package ch.hsr.tellstrack;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private Calendar calendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.Dialog), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Button timeButton = (Button) getActivity().findViewById(R.id.searchTime);
        timeButton.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
    }
}