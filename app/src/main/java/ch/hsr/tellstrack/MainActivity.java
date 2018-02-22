package ch.hsr.tellstrack;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import ch.hsr.tellstrack.adapter.ResultSearchAdapter;
import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.ConnectionList;
import ch.hsr.tellstrack.model.OpenDataTransportException;
import ch.hsr.tellstrack.model.SearchData;
import ch.hsr.tellstrack.repository.SearchRepository;

public class MainActivity extends AppCompatActivity {

    SearchRepository searchRepository = new SearchRepository();

    View textViewFrom;
    View textViewTo;
    Button searchButton;

    Button dateButton;
    Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewFrom = findViewById(R.id.searchFromInput);
        textViewTo = findViewById(R.id.searchToInput);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchTask().execute(searchDataCollect());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchButton.getWindowToken(), 0);
            }
        });


        dateButton = (Button) findViewById(R.id.searchDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "DatePicker");
            }
        });

        timeButton = (Button)findViewById(R.id.searchTime);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "TimePicker");
            }
        });

        setCurrentTimeOn(timeButton);
        setCurrentDateOn(dateButton);
    }

    public void setCurrentTimeOn(Button button){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(cal.getTime());
        button.setText(time);
    }

    public void setCurrentDateOn(Button button) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        button.setText(new StringBuilder()
                .append(year).append("-").append(month).append("-")
                .append(day));
    }

    private SearchData searchDataCollect() {
        SearchData searchData = new SearchData();
        EditText editTextFrom = (EditText) findViewById(R.id.searchFromInput);
        EditText editTextTo = (EditText) findViewById(R.id.searchToInput);
        EditText editTextVia = (EditText) findViewById(R.id.searchViaInput);
        Button timeButton = (Button) findViewById(R.id.searchTime);
        Button dateButton = (Button) findViewById(R.id.searchDate);
        CheckBox checkBoxArrival = (CheckBox) findViewById(R.id.isArrivalTime);
        searchData.setFrom(editTextFrom.getText().toString());
        searchData.setTo(editTextTo.getText().toString());
        searchData.setVia(editTextVia.getText().toString());
        searchData.setTime(timeButton.getText().toString());
        searchData.setDate(dateButton.getText().toString());
        searchData.setIsArrival(checkBoxArrival.isChecked());
        return searchData;
    }

    private class SearchTask extends AsyncTask<SearchData, Void, ConnectionList> {

        @Override
        protected void onPreExecute() {
            Resources res = getResources();
        }

        protected ConnectionList doInBackground(SearchData... sda) {
            SearchData sd = sda[0];

            try {
                return searchRepository.findConnections(sd.getFrom(), sd.getTo(), sd.getDate(), sd.getTime(), sd.getVia(), sd.getIsArrival());
            } catch (Exception ex) {
                return  null;
            }
        }

        protected void onPostExecute(ConnectionList result) {
            Log.d("con", result.getConnections().get(0).toString());
            final ListView listView = (ListView) findViewById(R.id.searchResultListView);
            ResultSearchAdapter resultSearchAdapter = new ResultSearchAdapter(getApplicationContext(), result);
            listView.setAdapter(resultSearchAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Connection connection = (Connection) parent.getItemAtPosition(position);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = new Gson();
                    Intent intent = new Intent(getApplicationContext(), ConnectionDetailActivity.class);
                    String json = gson.toJson(connection);
                    intent.putExtra("connection", json);
                    startActivity(intent);
                }
            });
        }
    }
}
