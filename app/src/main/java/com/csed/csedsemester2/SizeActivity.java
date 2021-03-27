package com.csed.csedsemester2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SizeActivity extends AppCompatActivity {
    // Allow user to input the size of their meal
   // public static final String EXTRA_MESSAGE = "com.csed.csedsemester2.MESSAGE";
    private String size=null;
    private Button snack;
    private Button small;
    private Button medium;
    private Button large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        snack = (Button)findViewById(R.id.snackButton);
        small = (Button)findViewById(R.id.smallButton);
        medium = (Button)findViewById(R.id.mediumButton);
        large = (Button)findViewById(R.id.largeButton);
//        Intent intent = getIntent();

    }
    public void buttonProceedOnClick(View view){
        // Start the size activity
        Intent intent = new Intent(this, TypeActivity.class);
        // Need an intent to start a new activity
        //String str = null; // putExtra takes two arguments
        intent.putExtra("size", size);
        startActivity(intent);
    }

    public void buttonSnackOnClick(View view){
        size = "Snack";
        snack.setBackgroundResource(R.drawable.circularbutton2);
        small.setBackgroundResource(R.drawable.circularbutton);
        medium.setBackgroundResource(R.drawable.circularbutton);
        large.setBackgroundResource(R.drawable.circularbutton);

    }


    public void buttonSmallOnClick(View view){
        size = "Small";
        snack.setBackgroundResource(R.drawable.circularbutton);
        small.setBackgroundResource(R.drawable.circularbutton2);
        medium.setBackgroundResource(R.drawable.circularbutton);
        large.setBackgroundResource(R.drawable.circularbutton);
    }


    public void buttomMediumOnClick(View view){
        size = "Medium";
        snack.setBackgroundResource(R.drawable.circularbutton);
        small.setBackgroundResource(R.drawable.circularbutton);
        medium.setBackgroundResource(R.drawable.circularbutton2);
        large.setBackgroundResource(R.drawable.circularbutton);

    }
    public void buttonLargeOnClick(View view){
        size = "Large";
        snack.setBackgroundResource(R.drawable.circularbutton);
        small.setBackgroundResource(R.drawable.circularbutton);
        medium.setBackgroundResource(R.drawable.circularbutton);
        large.setBackgroundResource(R.drawable.circularbutton2);
    }
}
