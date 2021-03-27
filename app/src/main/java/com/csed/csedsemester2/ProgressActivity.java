package com.csed.csedsemester2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by User on 16/04/2018.
 */

public class ProgressActivity extends AppCompatActivity {
    BarChart barChart;
    //What is this line of code for?

    static final String EXTRA_MESSAGE = "com.csed.csedsemester2.MESSAGE";





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Food Tracker");
        setContentView(R.layout.activity_main);


        createGraph();

    }



//    public void buttonOnClick(View view) {
 //       // Start the size activity
  //      Intent intent = new Intent(this, SizeActivity.class);
   //     // Need an intent to start a new activity
    //    String str = null; // putExtra takes two arguments
     //   intent.putExtra(EXTRA_MESSAGE, str);
      //  startActivity(intent);
   // }

    public void createGraph(){
        // BarChart chart = new BarChart(context);
        //       setContentView(chart);
        barChart = (BarChart) findViewById(R.id.graph);
        ArrayList<BarEntry> barEntries = new ArrayList();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(22f,1));

        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("a");
        theDates.add("b");

        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.invalidate();
    }

}