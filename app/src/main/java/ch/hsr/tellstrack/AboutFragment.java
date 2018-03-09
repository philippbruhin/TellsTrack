package ch.hsr.tellstrack;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by philipp.bruhin on 04.03.2018.
 */

public class AboutFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.content_about, container, false);
    }
}
