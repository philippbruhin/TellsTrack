package ch.hsr.tellstrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.tellstrack.R;
import ch.hsr.tellstrack.repository.OpenDataTransportException;
import ch.hsr.tellstrack.repository.OpenDataTransportRepository;
import ch.hsr.tellstrack.model.Station;

public class StationAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<Station> resultList = new ArrayList<>();

    public StationAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Station getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_station, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.stationname)).setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Station> stations = null;
                    try {
                        stations = findStations(constraint.toString());
                    } catch (OpenDataTransportException e) {
                        e.printStackTrace();
                    }

                    filterResults.values = stations;
                    filterResults.count = stations.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Station>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    private List<Station> findStations(String station) throws OpenDataTransportException {
        OpenDataTransportRepository repo = new OpenDataTransportRepository();
        return repo.findStations(station).getStations();
    }
}
