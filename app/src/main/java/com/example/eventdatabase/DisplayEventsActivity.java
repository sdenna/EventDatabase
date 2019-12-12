package com.example.eventdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class DisplayEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        Intent intent = getIntent();

        // the arraylist allEventsFirebase was populated by the firebase database when the
        // Show Events button is clicked on.

        // We need to create a listAdapter that tells us how the Listview should look and where
        // it gets it data from

        ArrayAdapter<Event> listEventsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, MainActivity.allEventsFirebase);

        // Get a reference to the ListView element to display all Events in firebase
        ListView allEventsListView = (ListView) findViewById(R.id.eventList);

        // Connect the rules define in the ArrayAdapter called listEventsAdapter to the ListView
        allEventsListView.setAdapter(listEventsAdapter);
    }


    public void backToHome(View v) {
        Intent intent = new Intent(DisplayEventsActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
