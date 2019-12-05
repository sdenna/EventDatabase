package com.example.eventdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;


public class DisplayEventsActivity extends AppCompatActivity {

    public static ArrayList<Event> allEvents = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        // Get info from the event
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        int year = intent.getIntExtra("year", -1);
        int month = intent.getIntExtra("month", -1);
        int day = intent.getIntExtra("day", -1);

        allEvents.add(new Event(name, date, year, month, day));
        Log.i("DENNA", "" + allEvents.size());

        EditText allEventsET = (EditText) findViewById(R.id.allEvents);

        String allEventsText = "";

        for (Event e: allEvents)
            allEventsText += e.toString() + "\n";

        allEventsET.setText(allEventsText);

        //EditText eventNameET = (EditText) findViewById(R.id.eventName);
        //EditText eventDateET = (EditText) findViewById(R.id.eventDate);

        //eventNameET.setText(name);
        //eventDateET.setText(date);



    }
}
