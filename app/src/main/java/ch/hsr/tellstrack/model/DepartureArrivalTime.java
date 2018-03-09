package ch.hsr.tellstrack.model;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ch.hsr.tellstrack.R;

public class DepartureArrivalTime implements Serializable {
    public Calendar calendar;
    public Boolean isArrival;
    private Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public DepartureArrivalTime(Context c)
    {
        context = c;
        calendar = Calendar.getInstance();
        isArrival = false;
    }
    
    @Override
    public String toString()
    {
        String output =  isArrival ? context.getString(R.string.Arrival) : context.getString(R.string.Departure);
        return output + " " + simpleDateFormat.format(calendar.getTime());

    }
}
