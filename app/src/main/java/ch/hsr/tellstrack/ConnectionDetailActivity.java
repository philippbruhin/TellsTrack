package ch.hsr.tellstrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.Service;

public class ConnectionDetailActivity extends AppCompatActivity {

    private Connection connection;
    private Service service;

    private TextView textViewDeparture;
    private TextView textViewArrival;
    private TextView textViewDuration;
    private TextView textViewRegular;
    private TextView textViewIrregular;
    private TextView textViewProducts;
    private TextView textViewCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_detail);
        String json = getIntent().getStringExtra("connection");
        Gson gson = new Gson();
        connection = gson.fromJson(json, Connection.class);
        Log.d("detail", "onCreate: " + connection.getFrom().getStation().toString());
        updateView();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateView() {

        Service service = connection.getService();

        textViewDeparture = (TextView) findViewById(R.id.departure);
        textViewDeparture.setText("From " + connection.getFrom().getStation().getName().toString());

        textViewArrival = (TextView) findViewById(R.id.arrival);
        textViewArrival.setText("To " + connection.getTo().getStation().getName().toString());

        textViewDuration = (TextView) findViewById(R.id.duration);
        textViewDuration.setText("Duration " + connection.getDuration().toString());

        if(service != null && !service.getIrregular().isEmpty())
        {
            textViewIrregular = (TextView) findViewById(R.id.service_irregular);
            textViewIrregular.setText("Irregular " + service.getIrregular());
        }

        if(service != null && !service.getRegular().isEmpty())
        {
            textViewRegular = (TextView) findViewById(R.id.service_regular);
            textViewRegular.setText("Regular " + service.getRegular());
        }

        textViewProducts = (TextView) findViewById(R.id.products);
        textViewProducts.setText(connection.getProducts().toString());

        textViewCapacity = (TextView) findViewById(R.id.capacity);
        textViewCapacity.setText("Occupation 1st: " + connection.getCapacity1st() + " 2nd: " + connection.getCapacity2nd());
    }
}
