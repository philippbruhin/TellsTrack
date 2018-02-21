package ch.hsr.tellstrack.repository;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.ConnectionWrapper;
import ch.hsr.tellstrack.model.OpenDataTransportException;

/**
 * Created by Alexander on 20/02/2018.
 */

public class SearchRepository {

    final String baseUrl = "http://transport.opendata.ch/v1/connections";
    ArrayList<Connection> connections = new ArrayList<Connection>();

    public ConnectionWrapper findConnections(String from, String to, String date, String time, String via, boolean isDeparture) throws OpenDataTransportException {
        String fullUrl = createUrl(from, to, date, time, via, isDeparture);

        String jsonResult = GetResponseFromUrl(fullUrl);

        Gson gson = new Gson();
        return gson.fromJson(jsonResult, ConnectionWrapper.class);
    }

    private String GetResponseFromUrl(String url) throws OpenDataTransportException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new OpenDataTransportException("Could not connect to Open Transport API");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String createUrl(String from, String to, String date, String time, String via, boolean isArrivalTime){
        String url = baseUrl;

        try {

            url = baseUrl + "?from=" + URLEncoder.encode(from, "UTF-8") + "&to=" + URLEncoder.encode(to, "UTF-8");

            if (via != null && via != "") {
                url += "&via[]=" + via;
            }

            if (date != null && !date.isEmpty()) {
                url += "&date=" + date;
            }

            if (time != null && !time.isEmpty()) {
                url += "&time=" + time;
            }

            if (isArrivalTime) {
                url += "&isArrivalTime=1";
            }
        }
        catch(UnsupportedEncodingException ex){

        }

        return url;
    }
}

