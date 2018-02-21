package ch.hsr.tellstrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.Instant;
import java.util.Date;

import ch.hsr.tellstrack.model.OpenDataTransportException;
import ch.hsr.tellstrack.repository.SearchRepository;

public class MainActivity extends AppCompatActivity {

    SearchRepository repository = new SearchRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    repository.findConnections("Zürich", "Winterthur", "2018-03-25", "18:00",null,true);
                }
                catch(OpenDataTransportException ex){
                    Number t = 5;
                }


            }
        });
    }
}
