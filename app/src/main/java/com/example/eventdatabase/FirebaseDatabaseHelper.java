package com.example.eventdatabase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceEvents;
    private ArrayList<Event> events = new ArrayList<>();


    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        // this will reference the node called events and all its children.
        mReferenceEvents = mDatabase.getReference("events");
    }


    public void addEvent(Event event) {
        // This gets the unique key of where to push the element and then sets the value at
        // this key with the newEvent object we want to add to the database

        String key = mReferenceEvents.push().getKey();
        mReferenceEvents.child(key).setValue(event);
    }


    public void updateEvent(String key, Event event) {
        mReferenceEvents.child(key).setValue(event);
    }






    //  NOT EVEN SURE IF NEED THE BELOW METHOD - MAY BE DELETING *********




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
                events.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Event event = keyNode.getValue(Event.class);
                    events.add(event);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




