package com.csed.csedsemester2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;



import com.jjoe64.graphview.GraphView;



import com.jjoe64.graphview.series.DataPoint;



import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//public class GraphsFragment extends Fragment{
public class GraphsFragment extends Fragment  implements View.OnClickListener{
    public String date;
    public BarChart barChart;
    public  TextView graphName;
    EditText name;
    public ArrayList<Integer> List;
    DataBaseHelper db;
    String newDate;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_graphs, container, false);
        barChart = (BarChart) view.findViewById(R.id.graph);
        graphName= (TextView) view.findViewById(R.id.graphName);

        Button proteinGraph = (Button) view.findViewById(R.id.proteinGraph);
        proteinGraph.setOnClickListener(this);
        Button fruitGraph = (Button) view.findViewById(R.id.fruitGraph);
        fruitGraph.setOnClickListener(this);
        Button dairyGraph = (Button) view.findViewById(R.id.dairyGraph);
        dairyGraph.setOnClickListener(this);
        Button fatGraph = (Button) view.findViewById(R.id.fatsGraph);
        fatGraph.setOnClickListener(this);
        Button carbGraph = (Button) view.findViewById(R.id.carbGraph);
        carbGraph.setOnClickListener(this);
        db = new DataBaseHelper(getActivity());
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
        date = DATE_FORMAT.format(today);
        Button submit = (Button) view.findViewById(R.id.EnterDateButton);
        name = (EditText) view.findViewById(R.id.EnterDate);
        createCarbsGraph();

        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          date = (String) name.getText().toString();
                                          Log.i("abcdefg", date);
                                      }   //             if (name.getText().toString().isEmpty()){
                                      //                Toast.makeText(getActivity().getApplicationContext(), "Enter the Data", Toast.LENGTH_SHORT).show();
                                      //          }
                                      //           else {
                                      //            Toast.makeText(getActivity().getApplicationContext(), "Name -  " + name.getText().toString()}
                                      //       }
                                      //      date ="250418";


                                      //      createSugarGraph();
                                      //      createCarbsGraph(graphName);
                                  });
        Log.i("wombat", "wombat");
        return view;
    }
    @Override

    public void onClick(View v) {
            //do what you want to do when button is clicked
            switch (v.getId()) {
                case R.id.proteinGraph:
                    createProteinGraph();
                    break;

                case R.id.dairyGraph:
                    createDairyGraph();
                    break;

                case R.id.fruitGraph:
                    createFruitGraph();
                    break;
                case R.id.fatsGraph:
                    createSugarGraph();
                    break;
                case R.id.carbGraph:
                    createCarbsGraph();

                    break;
    }
        }

    public void createSugarGraph() {
        List = db.progressFaSRetreiver(date);
        ArrayList<String> times = db.retrieveTodaysTimes(date);

        graphName.setText("Fats and Sugar score / time");
        ArrayList<BarEntry> barEntries = new ArrayList();
        int x = 0;
        while(x<times.size()){
            Log.i("times",times.get(x)+"");
            String time10=times.get(x).substring(7,9);
//            time10=time10+'.';
            //           time10=time10+times.get(x).charAt(10);
            Log.i("times",time10);
            int xaxis = Integer.parseInt(time10);
            if (xaxis>24){
                xaxis=0;
            }
            Log.i("timespreditl",times.get(x));

            Log.i("timesfinal",xaxis+"");
            barEntries.add(new BarEntry(xaxis, List.get(x)));
            x++;
        }
/*
        barEntries.add(new

                BarEntry(timeGraph1, sugarScore1));
        barEntries.add(new

                BarEntry(timeGraph2, sugarScore2));
*/
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1600");
        theDates.add("1200");
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.invalidate();

    }
    public void createCarbsGraph() {
        List = db.progressCarbsRetreiver(date);
        ArrayList<String> times = db.retrieveTodaysTimes(date);

        graphName.setText("Carbohydrate score / time");
        ArrayList<BarEntry> barEntries = new ArrayList();
        int x = 0;
        while(x<times.size()){
            Log.i("times",times.get(x)+"");
            String time10=times.get(x).substring(7,9);
//            time10=time10+'.';
            //           time10=time10+times.get(x).charAt(10);
            Log.i("times",time10);
            int xaxis = Integer.parseInt(time10);
            if (xaxis>24){
                xaxis=0;
            }
            Log.i("timespreditl",times.get(x));

            Log.i("timesfinal",xaxis+"");
            barEntries.add(new BarEntry(xaxis, List.get(x)));
            x++;
        }
/*
        barEntries.add(new

                BarEntry(timeGraph1, sugarScore1));
        barEntries.add(new

                BarEntry(timeGraph2, sugarScore2));
*/
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1600");
        theDates.add("1200");

        BarData theData = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(theData);
        barChart.invalidate();

    }
    public void createProteinGraph() {
        //db.progressProteinRetreiver();
        graphName.setText("Protein score / time");
        List = db.progressProteinRetreiver(date);
        ArrayList<String> times = db.retrieveTodaysTimes(date);

        ArrayList<BarEntry> barEntries = new ArrayList();
        int x = 0;
        while(x<times.size()){
            Log.i("times",times.get(x)+"");
            String time10=times.get(x).substring(7,9);
//            time10=time10+'.';
            //           time10=time10+times.get(x).charAt(10);
            Log.i("times",time10);
            int xaxis = Integer.parseInt(time10);
            if (xaxis>24){
                xaxis=0;
            }
            Log.i("timespreditl",times.get(x));

            Log.i("timesfinal",xaxis+"");
            barEntries.add(new BarEntry(xaxis, List.get(x)));
            x++;
        }
/*
        barEntries.add(new

                BarEntry(timeGraph1, sugarScore1));
        barEntries.add(new

                BarEntry(timeGraph2, sugarScore2));
*/
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1600");
        theDates.add("1200");

        BarData theData = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(theData);
        barChart.invalidate();

    }
    public void createFruitGraph() {
        graphName.setText("Fruit & Veg score / time");
        List = db.progressFavRetreiver(date);
        ArrayList<String> times = db.retrieveTodaysTimes(date);
        ArrayList<BarEntry> barEntries = new ArrayList();
        int x = 0;
        while(x<times.size()){
            Log.i("times",times.get(x)+"");
            String time10=times.get(x).substring(7,9);


//            time10=time10+'.';
            //           time10=time10+times.get(x).charAt(10);
            Log.i("timesscore",List.get(x)+"");

            int xaxis = Integer.parseInt(time10);
            if (xaxis>24){
                xaxis=0;
            }
            Log.i("timespreditl",times.get(x));

            Log.i("timesfinal",xaxis+"");
            barEntries.add(new BarEntry(xaxis, List.get(x)));
            x++;
        }
/*
        barEntries.add(new

                BarEntry(timeGraph1, sugarScore1));
        barEntries.add(new

                BarEntry(timeGraph2, sugarScore2));
*/
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1600");
        theDates.add("1200");

        BarData theData = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(theData);
        barChart.invalidate();

    }

    public void createDairyGraph() {
        graphName.setText("Dairy score / time");
        List = db.progressMadRetreiver(date);
        ArrayList<String> times = db.retrieveTodaysTimes(date);
        ArrayList<BarEntry> barEntries = new ArrayList();
        int x = 0;
        while(x<times.size()){
            Log.i("times",times.get(x)+"");
            String time10=times.get(x).substring(7,9);
//            time10=time10+'.';
            //           time10=time10+times.get(x).charAt(10);
            Log.i("times",time10);
            int xaxis = Integer.parseInt(time10);
            if (xaxis>24){
                xaxis=0;
            }
            Log.i("timespreditl",times.get(x));

            Log.i("timesfinal",xaxis+"");
            barEntries.add(new BarEntry(xaxis, List.get(x)));
            x++;
        }
/*
        barEntries.add(new

                BarEntry(timeGraph1, sugarScore1));
        barEntries.add(new

                BarEntry(timeGraph2, sugarScore2));
*/
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1600");
        theDates.add("1200");

        BarData theData = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(theData);
        barChart.invalidate();
    }


    














    //ignorebelow
//        createGraph();
    //    public void createGraph(){
//        // BarChart chart = new BarChart(context);
//        //       setContentView(chart);
////        barChart = (BarChart) findViewById(R.id.graph);
//        BarChart barChart = (BarChart) view.findViewById(R.id.graph);
//
//        ArrayList<BarEntry> barEntries = new ArrayList();
//        barEntries.add(new BarEntry(44f,0));
//        barEntries.add(new BarEntry(22f,1));
//
//        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
//        ArrayList<String> theDates = new ArrayList<>();
//        theDates.add("a");
//        theDates.add("b");
//
//        BarData theData = new BarData(barDataSet);
//        barChart.setData(theData);
//        barChart.invalidate();
//    }
//

}