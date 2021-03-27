package com.csed.csedsemester2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class TypeActivity extends AppCompatActivity {
    // Allow user to input the type of meal
    public String type = null;
    // public static final String EXTRA_MESSAGE = "com.csed.csedsemester2.MESSAGE";
    //
    // Dairy = 7.5
    // Fats and sugars = 15
    // Protein = 7.5
    // Fruit = 3

    private static SeekBar carbsBar;
    private static TextView carbsText;
    private static SeekBar dairyBar;
    private static TextView dairyText;
    private static SeekBar fruitBar;
    private static TextView fruitText;
    private static SeekBar fatsBar;
    private static TextView fatsText;
    private static SeekBar proteinBar;
    private static TextView proteinText;
    private static TextView totalText;
    private static TextView errorText;
    private int totalValue;
    private int carb_value;
    private int dairy_value;
    private int fruit_value;
    private int fats_value;
    private int protein_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        totalValue = 0;
        SeekBar();
//        Intent intent = getIntent();
    }


    public void SeekBar() {
        carbsBar = (SeekBar) findViewById(R.id.carbsBar);
        carbsText = (TextView) findViewById(R.id.carbsText);
        carbsText.setText("Carbohydrates: " + carbsBar.getProgress() + "%");

        dairyBar = (SeekBar) findViewById(R.id.dairyBar);
        dairyText = (TextView) findViewById(R.id.dairyText);
        dairyText.setText("Dairy: " + dairyBar.getProgress() + "%");

        fruitBar = (SeekBar) findViewById(R.id.fruitBar);
        fruitText = (TextView) findViewById(R.id.fruitText);
        fruitText.setText("Fruit or Veg: " + fruitBar.getProgress() + "%");

        fatsBar = (SeekBar) findViewById(R.id.fatsBar);
        fatsText = (TextView) findViewById(R.id.fatsText);
        fatsText.setText("Fats or Sugars: " + fatsBar.getProgress() + "%");

        proteinBar = (SeekBar) findViewById(R.id.proteinBar);
        proteinText = (TextView) findViewById(R.id.proteinText);
        proteinText.setText("Protein: " + proteinBar.getProgress() + "%");


        carbsBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        carb_value = progress;
                        updateTotalValue();
                        carbsText.setText("Carbohydrates: " + carb_value + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        carbsText.setText("Carbohydrates: " + carb_value + "%");
                    }
                }
        );

        dairyBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        dairy_value = progress;
                        updateTotalValue();
                        dairyText.setText("Dairy: " + dairy_value + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        dairyText.setText("Dairy: " + dairy_value + "%");
                    }
                }
        );

        fruitBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        fruit_value = progress;
                        updateTotalValue();
                        fruitText.setText("Fruit or Veg: " + fruit_value + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        fruitText.setText("Fruit or Veg: " + fruit_value + "%");
                    }
                }
        );

        fatsBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        fats_value = progress;
                        updateTotalValue();
                        fatsText.setText("Fats or Sugars: " + fats_value + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        fatsText.setText("Fats or Sugars: " + fats_value + "%");
                    }
                }
        );

        proteinBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        protein_value = progress;
                        updateTotalValue();
                        proteinText.setText("Protein: " + protein_value + "%");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        proteinText.setText("Protein: " + protein_value + "%");
                    }
                }
        );

    }


    public void updateTotalValue() {
        totalText = (TextView) findViewById(R.id.totalText);
        totalValue = carb_value + dairy_value + fruit_value + fats_value + protein_value;
        totalText.setText("Total: " + totalValue + "%");

    }


    public void buttonProceedOnClick(View view) {

        Intent otherIntent = getIntent();

        String size = otherIntent.getStringExtra("size");

        // Start the size activity
        Intent intent = new Intent(this, ScoreActivity.class);
        // Need an intent to start a new activity
        //String str = null; // putExtra takes two arguments


        if(totalValue != 100){
            errorText = findViewById(R.id.TotalErrorText);
            errorText.setText("Make sure that total is equal to 100% before submitting");

        }else if(totalValue == 100){

            intent.putExtra("size", size);
            intent.putExtra("protein", protein_value);
            intent.putExtra("carbs", carb_value);
            intent.putExtra("dairy", dairy_value);
            intent.putExtra("fats", fats_value);
            intent.putExtra("fruit", fruit_value);

            startActivity(intent);

        }
    }
}