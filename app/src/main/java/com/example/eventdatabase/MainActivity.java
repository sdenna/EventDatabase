package com.example.eventdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String dateSelected = "No date chosen";
    int dateMonth;
    int dateDay;
    int dateYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            Intent intent = new Intent(MainActivity.this, DisplayEventsActivity.class);
            intent.putExtra("name", eventName);
            intent.putExtra("date", dateSelected);
            intent.putExtra("month", dateMonth);
            intent.putExtra("year", dateYear);
            intent.putExtra("day", dateDay);
            startActivity(intent);
        }

    }
}
