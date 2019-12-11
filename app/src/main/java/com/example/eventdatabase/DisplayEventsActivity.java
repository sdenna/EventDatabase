package com.example.eventdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DisplayEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        Intent intent = getIntent();
        EditText allEventsET = (EditText) findViewById(R.id.allEvents);
        String allEventsText = "";

        // this arraylist was populated by the firebase database.

        for (Event e: MainActivity.allEventsFirebase)
            allEventsText += e.toString() + "\n";

        allEventsET.setText(allEventsText);
    }



}
