package com.example.eventdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;


public class DisplayEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        // Get info from the event
        Intent intent = getIntent();

        EditText allEventsET = (EditText) findViewById(R.id.allEvents);

        String allEventsText = "";

        for (Event e: MainActivity.allEvents)
            allEventsText += e.toString() + "\n";

        allEventsET.setText(allEventsText);

    }
}
