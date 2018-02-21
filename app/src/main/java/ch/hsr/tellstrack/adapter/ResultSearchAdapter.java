package ch.hsr.tellstrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.hsr.tellstrack.R;
import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.ConnectionList;

/**
 * Created by Alexander on 21/02/2018.
 */

public class ResultSearchAdapter extends BaseAdapter {

    private Context context;
    private ConnectionList connectionList;

    public ResultSearchAdapter(Context context, ConnectionList connectionList) {
        this.context = context;
        this.connectionList = connectionList;
    }

    @Override
    public int getCount() {
        return connectionList.getConnections().size();
    }

    @Override
    public Object getItem(int position) {
        return connectionList.getConnections().get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_search_result, parent, false);
        }
        Connection connection = (Connection) getItem(position);

        String departureTime = connection.getFrom().getDeparture().toString();
        int div = departureTime.indexOf('T');
        departureTime = departureTime.substring(div + 1, div + 6);
        String departureDate = connection.getFrom().getDeparture().toString();
        departureDate = departureDate.substring(0, div);
        departureDate = "";
        TextView departureTextView = (TextView) convertView.findViewById(R.id.departure);
        departureTextView.setText(departureTime + " " + departureDate + " " + connection.getFrom().getStation().getName());

        String arrivalTime = connection.getTo().getArrival().toString();
        div = arrivalTime.indexOf('T');
        arrivalTime = arrivalTime.substring(div + 1, div + 6);
        String arrivalDate = connection.getTo().getArrival().toString();
        arrivalDate = arrivalDate.substring(0, div);
        arrivalDate = "";
        TextView arrivalTextView = (TextView) convertView.findViewById(R.id.arrival);
        arrivalTextView.setText(arrivalTime + " " + arrivalDate + " " + connection.getTo().getStation().getName());

        TextView idTextView = (TextView) convertView.findViewById(R.id.departureContdown);
        String s = departureCountdown(connection);
        idTextView.setText(s);

        return convertView;
    }

    private String departureCountdown(Connection connection) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date current = calendar.getTime();
        String cur = sdf.format(current);
        String departure = connection.getFrom().getDeparture();
        TimeUtil timeUtil = new TimeUtil();
        try {
            Date departureDate = sdf.parse(departure);

            long different = departureDate.getTime() - current.getTime();

            timeUtil.split(different);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeUtil.toString();
    }

    private class TimeUtil {
        private long elapsedDys;
        private long elapsedHours;
        private long elapsedMinutes;
        private long elapsedSeconds;

        public void split(long spliter) {
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDys = spliter / daysInMilli;
            spliter = spliter % daysInMilli;

            elapsedHours = spliter / hoursInMilli;
            spliter = spliter % hoursInMilli;

            elapsedMinutes = spliter / minutesInMilli;
            spliter = spliter % minutesInMilli;

            elapsedSeconds = spliter / secondsInMilli;
        }

        public String toString() {

            if (elapsedDys > 0) {
                return elapsedDys + "d";
            }
            if (elapsedHours > 0) {
                return elapsedHours + "h" + elapsedMinutes + "\"";
            } else {
                return elapsedMinutes + "\"" + elapsedSeconds + "'";
            }
        }
    }
}

