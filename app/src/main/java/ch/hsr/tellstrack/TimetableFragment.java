package ch.hsr.tellstrack;

import android.app.Fragment;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.ConnectionList;
import ch.hsr.tellstrack.model.DepartureArrivalTime;
import ch.hsr.tellstrack.repository.OpenDataTransportException;
import ch.hsr.tellstrack.adapter.ConnectionAdapter;


import ch.hsr.tellstrack.adapter.StationAutoCompleteAdapter;
import ch.hsr.tellstrack.controls.DelayAutoCompleteTextView;
import ch.hsr.tellstrack.dialog.DepartureArrivalTimePickerDialog;
import ch.hsr.tellstrack.model.ConnectionDetail;
import ch.hsr.tellstrack.model.ConnectionSearch;
import ch.hsr.tellstrack.model.Station;
import ch.hsr.tellstrack.repository.OpenDataTransportRepository;


/**
 * Created by philipp.bruhin on 04.03.2018.
 */

public class TimetableFragment extends Fragment {

    private static final int THRESHOLD = 2;
    View TimetableView = null;

    DepartureArrivalTime departureArrivalTime;

    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    DateFormat simpleDateFormatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    TextView tvDateTop;
    RelativeLayout rlDateTop;
    
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        
        TimetableView =  inflater.inflate(R.layout.content_timetable, container, false);
        tvDateTop = TimetableView.findViewById(R.id.tvDateTop);
        rlDateTop = TimetableView.findViewById(R.id.rlDateTop);
        rlDateTop.setVisibility(View.GONE);
        SetupListener();
        SetupWhenButton();
        return TimetableView;
    }

    private void SetupWhenButton() {
        departureArrivalTime = new DepartureArrivalTime(getActivity());
        Button btn = TimetableView.findViewById(R.id.btnSetWhen);
        UpdateWhenButton(btn);

    }
    
    int firstVisibleInList = 0;
    int ConnectionCounter = 4;
    
    private void UpdateWhenButton(Button btn) {
        btn.setText(departureArrivalTime.toString());
    }

    private void SetupListener() {
        Button btn = TimetableView.findViewById(R.id.btnSearchConnection);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionCounter = 4;
                LoadConnections();
            }
        });
        AddWhenButtonListener();
        ImageButton btnReverse= TimetableView.findViewById(R.id.btnReverseConnection);
        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {ReverseConnection(); }
        });

        SetListenerForStationAutocomplete(R.id.txtFrom, R.id.pb_loading_indicatorFrom);
        SetListenerForStationAutocomplete(R.id.txtTo, R.id.pb_loading_indicatorTo);

        final ListView listConnection= TimetableView.findViewById(R.id.listConnections);
        listConnection.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(totalItemCount > 0) {
                    if (firstVisibleInList != firstVisibleItem) {
                        firstVisibleInList = firstVisibleItem;
                        SetDateTop(firstVisibleItem);
                    }
                    if (++firstVisibleItem + visibleItemCount > totalItemCount && totalItemCount >= ConnectionCounter) {
                        ConnectionCounter = ConnectionCounter + 4;
                        LoadLaterConnections();
                    }
                }
            }
        });

        listConnection.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ConnectionAdapter adapter = (ConnectionAdapter)listConnection.getAdapter();
                Connection connection = adapter.getItemAtPosition(position);

                Context context = view.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);

                ConnectionDetail cd = new ConnectionDetail(connection);
                Bundle b = new Bundle();
                b.putSerializable("connections",cd.ConnectionSections);
                intent.putExtra("bundle",  b);
                context.startActivity(intent);

            }
        });

    }

    private void SetDateTop(int i){

        try {
            Connection c = listOfConnections.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(simpleDateFormatInput.parse(c.getTo().getArrival()));
            tvDateTop.setText(simpleDateFormat.format(cal.getTime()));
            rlDateTop.setVisibility(View.VISIBLE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void AddWhenButtonListener() {
        Button btn = TimetableView.findViewById(R.id.btnSetWhen);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDepartureArrivalTimePicker();
            }
        });
    }

    public void updateDepartureArrivalTime(DepartureArrivalTime departureArrivalTime)
    {
        this.departureArrivalTime = departureArrivalTime;
        Button btn = TimetableView.findViewById(R.id.btnSetWhen);
        UpdateWhenButton(btn);

    }

    private void LoadDepartureArrivalTimePicker() {
        DialogFragment newFragment = DepartureArrivalTimePickerDialog.newInstance(departureArrivalTime);
        FragmentManager fragmentManager = getFragmentManager();
        newFragment.setTargetFragment(this, 0);

        newFragment.show(fragmentManager, "departureArrivalTimePicker");
    }

    private void ReverseConnection() {
        TimetableView.clearFocus();
        final DelayAutoCompleteTextView txtFrom = TimetableView.findViewById(R.id.txtFrom);
        final DelayAutoCompleteTextView txtTo = TimetableView.findViewById(R.id.txtTo);
        txtFrom.SetIndicatorActive(false);
        txtTo.SetIndicatorActive(false);
        Editable from = txtFrom.getText();
        txtFrom.setText(txtTo.getText());
        txtTo.setText(from);
        txtFrom.SetIndicatorActive(true);
        txtTo.SetIndicatorActive(true);
    }

    private void SetListenerForStationAutocomplete(int autoCompleteTextView, int indicator) {
        final DelayAutoCompleteTextView stationName = TimetableView.findViewById(autoCompleteTextView);
        stationName.setThreshold(THRESHOLD);
        stationName.setAdapter(new StationAutoCompleteAdapter(TimetableView.getContext()));
        stationName.setLoadingIndicator(
                (android.widget.ProgressBar) TimetableView.findViewById(indicator));
        stationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Station station = (Station) adapterView.getItemAtPosition(position);
                stationName.setText(station.getName());
            }
        });
    }

    ConnectionSearch CS;

    private void LoadConnections() {
        listOfConnections = null;
        EditText txtFrom = TimetableView.findViewById(R.id.txtFrom);
        EditText txtTo = TimetableView.findViewById(R.id.txtTo);
        ProgressBar pb = TimetableView.findViewById(R.id.pb_loading_indicatorListConnections);
        pb.setVisibility(View.VISIBLE);
        CS = new ConnectionSearch(txtFrom.getText().toString(),txtTo.getText().toString(), simpleDateFormat.format(departureArrivalTime.calendar.getTime()), simpleDateFormatTime.format(departureArrivalTime.calendar.getTime()), departureArrivalTime.isArrival);
        new LoaderTask().execute(CS);
    }

    private void LoadLaterConnections() {
        if (CS != null && listOfConnections != null && listOfConnections.size() >= 4) {
            ProgressBar pb = TimetableView.findViewById(R.id.pb_loading_indicatorListConnections);
            pb.setVisibility(View.VISIBLE);
            try {
                Calendar calendar  =  Calendar.getInstance();
                if(CS.isArrival) {
                    calendar.setTime(simpleDateFormatInput.parse(listOfConnections.get(listOfConnections.size() - 1).getTo().getArrival()));
                    calendar.add(Calendar.SECOND, 1);
                }
                else
                {
                    calendar.setTime(simpleDateFormatInput.parse(listOfConnections.get(listOfConnections.size() - 1).getTo().getArrival()));
                    calendar.add(Calendar.SECOND, 1);
                }
                CS.Date = simpleDateFormat.format(calendar.getTime());
                CS.Time = simpleDateFormatTime.format(calendar.getTime());
                new LoaderTask().execute(CS);
            } catch (Exception e) {
                e.printStackTrace();
                pb.setVisibility(View.GONE);
            }
        }
    }


    List<Connection> listOfConnections;
    ListView listView;

    private class LoaderTask extends AsyncTask<ConnectionSearch, Void, ConnectionList> {
        @Override
        protected ConnectionList doInBackground(ConnectionSearch... params) {

            OpenDataTransportRepository repo = new OpenDataTransportRepository();

            ConnectionList connectionList = null;
            try {
                connectionList = repo.searchConnections(params[0].From, params[0].To, "", params[0].Date, params[0].Time,  params[0].isArrival);

            } catch (OpenDataTransportException e) {
                e.printStackTrace();
            }

            return connectionList;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(ConnectionList connectionList) {

            if(connectionList != null ) {
                List<Connection> lconnections = SortConnectionList(connectionList);
                if(lconnections != null) {
                    if (listOfConnections == null) {
                        listView = TimetableView.findViewById(R.id.listConnections);
                        listOfConnections = lconnections;
                        ConnectionAdapter adapter = new ConnectionAdapter(getContext(), listOfConnections);
                        listView.setAdapter(adapter);
                        if(lconnections.size()> 0)
                        {
                            SetDateTop(0);
                        }
                    } else {
                        listOfConnections.addAll(lconnections);
                        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                    }
                }
            }

            ProgressBar pb =  TimetableView.findViewById(R.id.pb_loading_indicatorListConnections);
            pb.setVisibility(View.GONE);

            Context mContext = getContext();
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(TimetableView.getWindowToken(), 0);
        }
    }

    @NonNull
    private List<Connection> SortConnectionList(ConnectionList connectionList) {
        List<Connection> lconnections= connectionList.getConnections();
        Collections.sort(lconnections, new Comparator<Connection>() {
            @Override
            public int compare(Connection o1, Connection o2) {
                try {
                    Date d1 = simpleDateFormatInput.parse(o1.getFrom().getDeparture());
                    Date d2 = simpleDateFormatInput.parse(o2.getFrom().getDeparture());
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;

            }
        });
        return lconnections;
    }

}
