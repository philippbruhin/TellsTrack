package ch.hsr.tellstrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.ConnectionDetails);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, TimetableFragment.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable public Toolbar getToolbarView(@NonNull Context context) {
        Activity activity = ((Activity) context);

        int resId = context.getResources().getIdentifier("action_bar", "id", "android");

        Toolbar toolbar = activity.findViewById(resId);
        if (toolbar == null) {
            toolbar = findToolbar((ViewGroup) activity.findViewById(android.R.id.content));
        }
        return toolbar;
    }

    private Toolbar findToolbar(@NonNull ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getClass().getName().equals("android.support.v7.widget.Toolbar")
                    || view.getClass().getName().equals("android.widget.Toolbar")) {
                return (Toolbar) view;
            } else if (view instanceof ViewGroup) {
                return findToolbar((ViewGroup) view);
            }
        }
        return null;
    }
}
