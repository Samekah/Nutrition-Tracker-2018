package com.csed.csedsemester2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.*;
import java.util.ArrayList;


public class GoalsFragment extends Fragment {

   private static EditText carbGoal;
    private static EditText dairyGoal;
    private static EditText proteinGoal;
    private static EditText fruitGoal;
    private static EditText fatsGoal;
    private static TextView errorText;
    private static Button saveButton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_goals,container,false);



        carbGoal = (EditText)view.findViewById(R.id.carbGoal);
        dairyGoal = (EditText)view.findViewById(R.id.dairyGoal);
        proteinGoal = (EditText)view.findViewById(R.id.proteinGoal);
        fruitGoal = (EditText)view.findViewById(R.id.fruitGoal);
        fatsGoal = (EditText)view.findViewById(R.id.fatsGoal);
        errorText = (TextView)view.findViewById(R.id.GoalsErrorText);
        saveButton = (Button) view.findViewById(R.id.SaveButton);


    //GOALS RETRIEVED HERE - need to convert the integer to string to display
        /////////////////////////////////////////////////////////
        DataBaseHelper db = new DataBaseHelper(getActivity());
        ArrayList<Integer> goals = db.goalPercentageRetriever();

        carbGoal.setText(String.valueOf(goals.get(0)));
        proteinGoal.setText(String.valueOf(goals.get(1)));
        dairyGoal.setText(String.valueOf(goals.get(2)));
        fruitGoal.setText(String.valueOf(goals.get(3)));
        fatsGoal.setText(String.valueOf(goals.get(4)));

    //////////////////////////////////////////////////////////////////////////



        //Check if goals exist in database currently, if so, load them

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int carbValue = Integer.parseInt(carbGoal.getText().toString());
                int dairyValue = Integer.parseInt(dairyGoal.getText().toString());
                int proteinValue = Integer.parseInt(proteinGoal.getText().toString());
                int fruitValue = Integer.parseInt(fruitGoal.getText().toString());
                int fatsValue = Integer.parseInt(fatsGoal.getText().toString());

                if(carbValue + dairyValue + proteinValue + fruitValue + fatsValue == 100){
                    errorText.setText("Goal Set");


                    //GOALS UPDATED HERE//////////////////////////////////////////////////
                    DataBaseHelper db = new DataBaseHelper(getActivity());
                    db.booleangoalUpdater(carbValue,proteinValue,dairyValue,fruitValue,fatsValue);
                    Log.d("GOALS FRAGMENT", "GOAL UPDATED");
                    ////////////////////////////////////////////////////////////////
                }else{
                    errorText.setText("Make sure that the goals add to 100% before submitting");
                }
            }
        });


        return view;
    }





}
