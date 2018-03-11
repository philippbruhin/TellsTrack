package ch.hsr.tellstrack.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ch.hsr.tellstrack.model.ConnectionSection;
import ch.hsr.tellstrack.R;

public class ConnectionSectionAdapter extends ArrayAdapter<ConnectionSection> {

    public ConnectionSectionAdapter(Context context, ArrayList<ConnectionSection> connectionSections) {
        super(context, 0, connectionSections);
    }

    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    private DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ConnectionSection connectionSection = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_details, parent, false);
        }

        Calendar departure = Calendar.getInstance();
        Calendar arrival = Calendar.getInstance();
        Calendar prognosisDep = Calendar.getInstance();
        Calendar prognosisArr = Calendar.getInstance();

        try {
            assert connectionSection != null;
            departure.setTime(inputFormat.parse(connectionSection.DepartureTime));
            arrival.setTime(inputFormat.parse(connectionSection.ArrivalTime));

            if (connectionSection.DeparturePrognosis != null) {
                prognosisDep.setTime(inputFormat.parse(connectionSection.DeparturePrognosis));
            }
            if (connectionSection.ArrivalPrognosis != null) {
                prognosisArr.setTime(inputFormat.parse(connectionSection.ArrivalPrognosis));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView) convertView.findViewById(R.id.tvdetDepTime)).setText(sdfTime.format(departure.getTime()));
        ((TextView) convertView.findViewById(R.id.tvdetDeparture)).setText(connectionSection.Departure);
        ((TextView) convertView.findViewById(R.id.tvdetPatfDep)).setText(connectionSection.DeparturePlatform);

        if (connectionSection.DeparturePrognosis != null) {
            ((TextView) convertView.findViewById(R.id.tvdetProgDep)).setText(sdfTime.format(prognosisDep.getTime()));
        } else {
            ((TextView) convertView.findViewById(R.id.tvdetProgDep)).setText("");
        }

        ((TextView) convertView.findViewById(R.id.tvdetArrTime)).setText(sdfTime.format(arrival.getTime()));
        ((TextView) convertView.findViewById(R.id.tvdetArrival)).setText(connectionSection.Arrival);
        ((TextView) convertView.findViewById(R.id.tvdetPlatfArr)).setText(connectionSection.ArrivalPlatform);

        if (connectionSection.ArrivalPrognosis != null) {
            ((TextView) convertView.findViewById(R.id.tvdetProgArr)).setText(sdfTime.format(prognosisArr.getTime()));
        } else {
            ((TextView) convertView.findViewById(R.id.tvdetProgArr)).setText("");
        }

        ((TextView) convertView.findViewById(R.id.tvdetName)).setText(connectionSection.Name);
        ((TextView) convertView.findViewById(R.id.tvdetEndTo)).setText(connectionSection.To);

        return convertView;
    }
}