package com.example.eventdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private String dateSelected = "No date chosen";
    private int dateMonth;
    private int dateDay;
    private int dateYear;

    private DatabaseReference database;

    // this ArrayList is made public static so that it is accessible in the DisplayEventsActivity onCreate

    public static ArrayList<Event> allEventsFirebase = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();


        //  Video to learn basic access to CalendarView Data
        //  https://www.youtube.com/watch?v=WNBE_3ZizaA

        CalendarView calendarView = findViewById(R.id.eventCalendarDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
                                                 @Override
                                                 public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                                                     String date =  (month + 1) + "/" + day + "/" + year;
                                                     Log.i("DENNA", date);
                                                     dateYear = year;
                                                     dateMonth = month + 1;
                                                     dateDay = day;

                                                     dateSelected = date;
                                                     closeKeyboard();
                                                 }
                                             }
        );
    }

    public void addEventButtonPressed(View v) {

        EditText eventNameET = (EditText) findViewById(R.id.eventName);
        String eventName = eventNameET.getText().toString();

        // verify there is a name and date
        if (eventName.length() == 0 ) {
            Toast.makeText(MainActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
        }
        else if (dateSelected.equals("No date chosen")) {
            Toast.makeText(MainActivity.this, "Please select Date", Toast.LENGTH_SHORT).show();
        }
        else {
            Event newEvent = new Event(eventName, dateSelected, dateYear, dateMonth, dateDay);
            eventNameET.setText("");    // clears out text

            /*
             Before we can add the newEvent object to the database, we need to get an instance
             of the db.  From here you can either get a reference database as a whole and all
             elements are added at the root level or you can set a folder and have the reference
             point to a folder within the database. It all depends on how you want to organize your data


            */

            // This will put the elements directly into database, not in a folder
            // DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            // I wanted my elements to be pushed into a folder called events
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("/events");

            // This gets the unique key of where to push the element and then sets the value at
            // this key with the newEvent object we want to add to the database
            String key = dbRef.push().getKey();
            dbRef.child(key).setValue(newEvent);

            // this works on my phone, not on the emulator
            // check if the emulator has play store on it?  Or need wifi?
        }

    }

    /**
     * This method will be closed to minimize the entry keyboard of the Activity
     * When we get the current view, it is the view that has focus, which is the keyboard
     *
     * Source:  https://www.youtube.com/watch?v=CW5Xekqfx3I
     */
    private void closeKeyboard() {
        View view = this.getCurrentFocus();     // view will refer to the keyboard
        if (view != null ){                     // if there is a view that has focus
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * This method is called to retrieve the data from firebase.  Currently it is simply
     * pulling the data, filing an array list, and then the array list will be used to
     * display the info when the next page loads.
     *
     * modified from source found at link below
     * https://www.programcreek.com/java-api-examples/?class=com.google.firebase.database.DataSnapshot&method=getChildren
     *
     * @param v
     */


    public void onRetrieve(View v){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allEventsFirebase.clear();      // emptys arraylist to start fresh on each display

                // This gets another snapshot for the events folder - essentially going
                // one level into the database
                DataSnapshot events = dataSnapshot.child("events");

                for (DataSnapshot item: events.getChildren()) {

                   allEventsFirebase.add(new Event(
                           item.child("eventName").getValue().toString(),
                           item.child("eventDate").getValue().toString(),
                           Integer.valueOf(item.child("year").getValue().toString()),
                           Integer.valueOf(item.child("month").getValue().toString()),
                           Integer.valueOf(item.child("day").getValue().toString())
                   ));
                }
                // starts intent that will display this new data that has been saved into the arraylist
                // since we used a single value event the data will not continually update

                Intent intent = new Intent(MainActivity.this, DisplayEventsActivity.class);
                startActivity(intent);
            }


            // This method is required for it the listener to work.
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("callError", "There has been an Error with database retrieval");
            }
        });
    }







}
