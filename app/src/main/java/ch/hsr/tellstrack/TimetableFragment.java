package ch.hsr.tellstrack;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import ch.hsr.tellstrack.repository.ITransportRepository;
import ch.hsr.tellstrack.repository.OpenDataTransportException;
import ch.hsr.tellstrack.repository.TransportRepositoryFactory;
import ch.hsr.tellstrack.model.Connection;
import ch.hsr.tellstrack.model.ConnectionList;
import ch.hsr.tellstrack.model.Station;


/**
 * Created by philipp.bruhin on 04.03.2018.
 */

public class TimetableFragment extends Fragment {


}
