package com.csed.csedsemester2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT;


public class ScoreActivity extends AppCompatActivity {
    // Allow user to input the type of meal
  //  public static final String EXTRA_MESSAGE = "com.csed.csedsemester2.MESSAGE";
    DataBaseHelper myDb;
    int proteinScore;
    int carbScore ;
    int dairyScore;
    int fruitScore;
    int fatsScore;
    int score;
    String size;
    PieChart pieChart;
    private ArrayList<Integer> scores  = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score);
            Intent intent = getIntent();
            size = intent.getStringExtra("size");

            int protein = intent.getIntExtra("protein", 0);
            int carbs = intent.getIntExtra("carbs", 0);
            int fruit = intent.getIntExtra("fruit", 0);
            int fats = intent.getIntExtra("fats", 0);
            int dairy = intent.getIntExtra("dairy", 0);
        Log.i("qwe",""+protein);

            scores.clear();

         //   err = (TextView)findViewById(R.id.texto);
            /*TextView txProtein = (TextView) findViewById(R.id.textProtein);
            TextView txDairy = (TextView) findViewById(R.id.textDairy);
            TextView txFruit = (TextView) findViewById(R.id.textFruit);
            TextView txFats = (TextView) findViewById(R.id.textFats);
            TextView txCarbs = (TextView) findViewById(R.id.textCarbs);*/

            //tx2.setText(type);
            int sizeScore = 0;

            switch(size) {
                case "Snack":
                    sizeScore = 1;
                    break;
                case "Small":
                    sizeScore = 2;
                    break;
                case "Medium":
                    sizeScore = 3;
                    break;
                case "Large":
                    sizeScore = 4;
                    break;
            }

        Log.i("qwe",""+protein);
        Log.i("qwe",""+sizeScore);

        proteinScore = sizeScore * protein;
        carbScore = sizeScore * carbs;
        dairyScore = sizeScore * dairy;
        fruitScore = sizeScore * fruit;
        fatsScore = sizeScore * fats;

        // Dairy = 7.5
        // Fats and sugars = 15
        // Protein = 7.5
        // Fruit = 3
        // carbs = 3

            /*txProtein.setText("Protein Score = " + proteinScore);
            txDairy.setText("Dairy Score = " + dairyScore);
            txCarbs.setText("Carbohydrates Score = " + carbScore);
            txFruit.setText("Fruit and Veg Score = " + fruitScore );
            txFats.setText("Fat Score = " + fatsScore);*/


            if(proteinScore > 0){
                scores.add(proteinScore);
                categories.add("Protein");
            }

            if(carbScore > 0){
                scores.add(carbScore);
                categories.add("Carbs");
            }

            if(dairyScore > 0){
                scores.add(dairyScore);
                categories.add("Dairy");
            }

            if(fruitScore > 0){
                scores.add(fruitScore);
                categories.add("Fruit & Veg");
            }

            if(fatsScore > 0){
                scores.add(fatsScore);
                categories.add("Fats");
            }


            pieChart = findViewById(R.id.pieChart);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleAlpha(61);
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawEntryLabels(false);
            addDataSet();
        myDb = new DataBaseHelper(this);
        addData();//tests database
    }



    private void addDataSet(){
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

    public void homeOnClick(View view){
        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void addData(){
        Log.i("qwe",""+size);
       Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
        String date = DATE_FORMAT.format(today);
   //     String date="250418";
//        if(date.length()==6){
//            date="00"+date;
//        }
 //       else if(date.length()==5){//////
  //          date="0"+date;

//        }
        myDb.insertDataProgress(date,5,myDb.currentTime(),size, carbScore,proteinScore, dairyScore,fruitScore,fatsScore);
        Log.i("qwe",""+date);
        Log.i("proteinscore",""+proteinScore);

        Log.i("qwe",""+size);
    }

}