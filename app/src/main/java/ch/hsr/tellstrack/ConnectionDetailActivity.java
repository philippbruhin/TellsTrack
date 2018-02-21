package ch.hsr.tellstrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import ch.hsr.tellstrack.model.Connection;

public class ConnectionDetailActivity extends AppCompatActivity {

    private Connection connection;

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
        TextView textViewDeparture = (TextView) findViewById(R.id.departure);
        textViewDeparture.setText(getString(R.string.From) + " " + connection.getFrom().getStation().getName().toString());

        TextView textViewArrival = (TextView) findViewById(R.id.arrival);
        textViewArrival.setText(getString(R.string.To) + " " + connection.getTo().getStation().getName().toString());

        TextView textViewDuration = (TextView) findViewById(R.id.duration);
        textViewDuration.setText(getString(R.string.Duration) + " " + connection.getDuration().toString());

        TextView textViewService = (TextView) findViewById(R.id.service_irregular);
        String irregular = connection.getService().getIrregular();
        if (irregular == null) {
            textViewService.setText("");
        } else {
            textViewService.setText(getString(R.string.Irregular) + " " + irregular);
        }

        TextView textViewServiceRegular = (TextView) findViewById(R.id.service_regular);
        String regular = connection.getService().getRegular();
        if (regular == null) {
            textViewServiceRegular.setText("");
        } else {
            textViewServiceRegular.setText(getString(R.string.Regular) + " " + regular);
        }

        TextView textViewProducts = (TextView) findViewById(R.id.products);
        textViewProducts.setText(connection.getProducts().toString());

        TextView textViewCapacity = (TextView) findViewById(R.id.capacity);
        textViewCapacity.setText(getString(R.string.estimated_occupation) + getString(R.string.firstclass) + connection.getCapacity1st() + getString(R.string.secondclass) + connection.getCapacity2nd());
    }
}
