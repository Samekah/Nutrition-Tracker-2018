package com.csed.csedsemester2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public abstract class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Report");
        setContentView(R.layout.activity_main);
    }

}
