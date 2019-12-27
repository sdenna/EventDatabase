package com.example.eventdatabase;

import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class FirebaseDatabaseHelper {
    private DatabaseReference mReferenceEvents;
    private ArrayList<Event> eventsArrayList = new ArrayList<>();
    public static ArrayList<String> keys = new ArrayList<>();


    public FirebaseDatabaseHelper() {
        // this will reference the node called events and all its children.
        mReferenceEvents = FirebaseDatabase.getInstance().getReference("events");

        mReferenceEvents.addValueEventListener(new ValueEventListener() {

            /**
             * In this method we will clear the arraylist of all events each time the data is changed
             * We will then create an array list to get all the unique keys of the data
             * Once we have the key, we can then reference that particular event and we can get the
             * Event object from firebase so that we can add it to our arraylist of events
             *
             * This method onDataChange is an asynchonos method.  It isn't called each time the readEvents
             * method is called.  It is called when the data is changed.  So in order to link these two processes
             * together, we use an interface
             *
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventsArrayList.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    keys.add(item.getKey());
                   // Event event = item.getValue(Event.class);

                    Event e = new Event(
                            item.child("eventName").getValue().toString(),
                            item.child("eventDate").getValue().toString(),
                            Integer.valueOf(item.child("year").getValue().toString()),
                            Integer.valueOf(item.child("month").getValue().toString()),
                            Integer.valueOf(item.child("day").getValue().toString()),
                            item.child("key").getValue().toString());

                    eventsArrayList.add(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public DatabaseReference getDatabaseReference() {
        return mReferenceEvents;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }


    public void addEvent(Event event) {
        // This gets the unique key of where to push the element and then sets the value at
        // this key with the newEvent object we want to add to the database

        String key = mReferenceEvents.push().getKey();
        event.setKey(key);
        Log.i("Denna", "Inside FBDBHelper, key is: " + event.getKey());
        mReferenceEvents.child(key).setValue(event);

    }


    /**
     * Every time you update, delete, or insert data, this value event listener will execute the
     * onDataChange method.
     */
    public void addEvent() {
        mReferenceEvents.addValueEventListener(new ValueEventListener() {

            /**
             * In this method we will clear the arraylist of all events each time the data is changed
             * We will then create an array list to get all the unique keys of the data
             * Once we have the key, we can then reference that particular event and we can get the
             * Event object from firebase so that we can add it to our arraylist of events
             *
             * This method onDataChange is an asynchonos method.  It isn't called each time the readEvents
             * method is called.  It is called when the data is changed.  So in order to link these two processes
             * together, we use an interface
             *
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventsArrayList.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Event event = keyNode.getValue(Event.class);
                    eventsArrayList.add(event);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Event> populateEventArrayList(DataSnapshot dataSnapshot) {
        eventsArrayList.clear();      // empties arraylist to start fresh on each display
        keys.clear();

        // This gets another snapshot from the events node - essentially going
        // one level into the database
        DataSnapshot events = dataSnapshot.child("events");

        // this gets all elements in the events node so we can cycle through them in a loop
        for (DataSnapshot item: events.getChildren()) {

            eventsArrayList.add(new Event(
                    item.child("eventName").getValue().toString(),
                    item.child("eventDate").getValue().toString(),
                    Integer.valueOf(item.child("year").getValue().toString()),
                    Integer.valueOf(item.child("month").getValue().toString()),
                    Integer.valueOf(item.child("day").getValue().toString()),
                    item.child("key").getValue().toString()
            ));
            keys.add(item.getKey());
            Log.i("Denna", "Inside populate, key is: " + keys.get(keys.size()-1));

        }
        Log.i("Denna", "In FDBH, arraylist size is: " + eventsArrayList.size());
        return eventsArrayList;
    }

    public void updateEvent(String key, String eventName, String eventDate, int month, int day, int year) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events").child(key);
        ref.child("eventName").setValue(eventName);
        ref.child("eventDate").setValue(eventDate);
        ref.child("month").setValue(month);
        ref.child("day").setValue(day);
        ref.child("year").setValue(year);

        // Still need to error check the date is a valid date and then update the month, day, year variables
    }


    public void deleteEvent(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events").child(key);
        ref.removeValue();
    }


    /**
     * Every time you update, delete, or insert data, this value event listener will execute the
     * onDataChange method.
     */
    public ArrayList<Event> getAllEvents() {
        mReferenceEvents.addValueEventListener(new ValueEventListener() {

            /**
             * In this method we will clear the arraylist of all events each time the data is changed
             * We will then create an array list to get all the unique keys of the data
             * Once we have the key, we can then reference that particular event and we can get the
             * Event object from firebase so that we can add it to our arraylist of events
             *
             * This method onDataChange is an asynchonos method.  It isn't called each time the readEvents
             * method is called.  It is called when the data is changed.
             *
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventsArrayList.clear();
                int count = 0;
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    count++;
                    Event event = keyNode.getValue(Event.class);
                    Log.i("Denna", "" + count);
                    Log.i("Denna", event.toString());
                    eventsArrayList.add(event);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return eventsArrayList;
    }
}




