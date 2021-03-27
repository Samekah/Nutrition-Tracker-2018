package com.csed.csedsemester2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER;
import static com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT;


public class HomeFragment extends Fragment {
    BarChart barChart;
    PieChart percentPieChart;
    PieChart goalsPieChart;
    DataBaseHelper db;
    private  ArrayList<Integer> carbs;
    private  ArrayList<Integer> dairy;
    private  ArrayList<Integer> protein;
    private  ArrayList<Integer> fruit;
    private  ArrayList<Integer> fats;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home,container,false);

        barChart = (BarChart) view.findViewById(R.id.graph);

        db = new DataBaseHelper(getActivity());
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
        String date = DATE_FORMAT.format(today);


        carbs = db.progressCarbsRetreiver(date);
        fats = db.progressFaSRetreiver(date);
        dairy = db.progressMadRetreiver(date);
        fruit = db.progressFavRetreiver(date);
        protein = db.progressProteinRetreiver(date);

        ArrayList<String> times = db.retrieveTodaysTimes(date);

        int total, carbsTotal,fatsTotal,dairyTotal,fruitTotal,proteinTotal;
        total = carbsTotal = fatsTotal = dairyTotal = fruitTotal = proteinTotal  = 0;

         carbsTotal = sumTotal(carbs);
         dairyTotal = sumTotal(dairy);
         proteinTotal = sumTotal(protein);
         fruitTotal = sumTotal(fruit);
         fatsTotal = sumTotal(fats);


        ArrayList<Integer> percentages = new ArrayList<>();
        ArrayList<Integer> goals = db.goalPercentageRetriever();
        ArrayList<Integer> goals2 = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<String> categories2 = new ArrayList<>();


        if(carbsTotal > 0){
            percentages.add(carbsTotal);
            categories.add("Carbs");
        }

        if(dairyTotal > 0){
            percentages.add(dairyTotal);
            categories.add("Dairy");
        }

        if(proteinTotal > 0){
            percentages.add(proteinTotal);
            categories.add("Protein");
        }

        if(fruitTotal > 0){
            percentages.add(fruitTotal);
            categories.add("Fruit & Veg");
        }

        if(fatsTotal > 0){
            percentages.add(fatsTotal);
            categories.add("Fats");
        }

        int carbGoal, proteinGoal, dairyGoal, fruitGoal,fatsGoal;

        carbGoal =  goals.get(0);
        proteinGoal = goals.get(1);
        dairyGoal = goals.get(2);
        fruitGoal = goals.get(3);
        fatsGoal = goals.get(4);

        if(carbGoal > 0){
            goals2.add(carbGoal);
            categories2.add("Carbs");
        }

        if(dairyGoal > 0){
            goals2.add(dairyGoal);
            categories2.add("Dairy");
        }

        if(proteinGoal > 0){
            goals2.add(proteinGoal);
            categories2.add("Protein");
        }

        if(fruitGoal > 0){
            goals2.add(fruitGoal);
            categories2.add("Fruit & Veg");
        }

        if(fatsGoal > 0){
            goals2.add(fatsGoal);
            categories2.add("Fats");
        }





        percentPieChart = (PieChart) view.findViewById(R.id.percentagesPieChart);


        goalsPieChart = (PieChart) view.findViewById(R.id.goalsPieChart);
        addDataSet(percentPieChart,percentages,categories);
        addDataSet(goalsPieChart,goals,categories2);

        return view;
    }

    private void addDataSet(PieChart pieChart, ArrayList<Integer> scores, ArrayList<String> categories){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleAlpha(61);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setDrawInside(false);


        ArrayList<PieEntry> pieEntries = new ArrayList<>();


        for(int i = 0; i<scores.size(); i++){
            pieEntries.add(new PieEntry(scores.get(i),categories.get(i)));
        }



        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setSliceSpace(5);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueTextColor(Color.WHITE);




        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);



        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private int sumTotal(ArrayList<Integer> arrList) {
        int total = 0;
        for (int i = 0; i < arrList.size(); i++) {
            total += arrList.get(i);
        }
        return total;
    }


}
