package ch.hsr.tellstrack;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ch.hsr.tellstrack.adapter.AutoCompleteAdapter;
import ch.hsr.tellstrack.adapter.ResultSearchAdapter;
import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.ConnectionList;
import ch.hsr.tellstrack.model.SearchData;
import ch.hsr.tellstrack.repository.SearchRepository;

public class MainActivity extends AppCompatActivity {

    private SearchRepository searchRepository = new SearchRepository();

    private Button searchButton;
    private Button dateButton;
    private Button timeButton;
    private Button changeDirectionButton;
    private SearchData searchData;
    private EditText editTextFrom;
    private EditText editTextTo;
    private EditText editTextVia;
    private CheckBox checkBoxArrival;

    private AutoCompleteTextView textViewVia;
    private AutoCompleteTextView textViewTo;
    private AutoCompleteTextView textViewFrom;

    private Calendar calendar;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewFrom            = findViewById(R.id.searchFromInput);
        textViewTo              = findViewById(R.id.searchToInput);
        textViewVia             = findViewById(R.id.searchViaInput);
        searchButton            = findViewById(R.id.searchButton);
        dateButton              = findViewById(R.id.searchDate);
        timeButton              = findViewById(R.id.searchTime);
        changeDirectionButton   = findViewById(R.id.searchChangeDirection);


        textViewFrom.setAdapter(new AutoCompleteAdapter(this));
        textViewTo.setAdapter(new AutoCompleteAdapter(this));
        textViewVia.setAdapter(new AutoCompleteAdapter(this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchTask().execute(searchDataCollect());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchButton.getWindowToken(), 0);
            }
        });

       dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "DatePicker");
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "TimePicker");
            }
        });

        changeDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextFrom = findViewById(R.id.searchFromInput);
                EditText editTextTo = findViewById(R.id.searchToInput);
                String from = editTextFrom.getText().toString();
                String to = editTextTo.getText().toString();
                editTextFrom.setText(to);
                editTextTo.setText(from);
            }
        });

        setCurrentTimeOn(timeButton);
        setCurrentDateOn(dateButton);
    }

    public void setCurrentTimeOn(Button button){
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(calendar.getTime());
        button.setText(time);
    }

    public void setCurrentDateOn(Button button) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        button.setText(new StringBuilder()
                .append(year).append("-").append(month).append("-")
                .append(day));
    }

    private SearchData searchDataCollect() {

        searchData = new SearchData();
        editTextFrom = findViewById(R.id.searchFromInput);
        editTextTo = findViewById(R.id.searchToInput);
        editTextVia = findViewById(R.id.searchViaInput);
        timeButton = findViewById(R.id.searchTime);
        dateButton = findViewById(R.id.searchDate);
        checkBoxArrival = findViewById(R.id.isArrivalTime);

        searchData.setFrom(editTextFrom.getText().toString());
        searchData.setTo(editTextTo.getText().toString());
        searchData.setVia(editTextVia.getText().toString());
        searchData.setTime(timeButton.getText().toString());
        searchData.setDate(dateButton.getText().toString());
        searchData.setIsArrival(checkBoxArrival.isChecked());

        return searchData;
    }

    private class SearchTask extends AsyncTask<SearchData, Void, ConnectionList> {

        protected ConnectionList doInBackground(SearchData... sda) {
            SearchData sd = sda[0];

            try {
                return searchRepository.findConnections(sd.getFrom(), sd.getTo(), sd.getDate(), sd.getTime(), sd.getVia(), sd.getIsArrival());
            } catch (Exception ex) {
                return  null;
            }
        }

        protected void onPostExecute(ConnectionList result) {

            listView = findViewById(R.id.searchResultListView);
            ResultSearchAdapter resultSearchAdapter = new ResultSearchAdapter(getApplicationContext(), result);
            listView.setAdapter(resultSearchAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Connection connection = (Connection) parent.getItemAtPosition(position);
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
