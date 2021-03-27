package com.csed.csedsemester2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;


public class DaysFragment extends Fragment {
    Spinner spinner;
    ViewGroup contain;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        contain = container;
        View view = inflater.inflate(R.layout.activity_days,container,false);

        String[] paths = {"carbohydrates", "protein", "milkAndDairy", "fruitAndVeg", "fatsAndSugars"};
        spinner = (Spinner)view.findViewById(R.id.spinner1);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       // spinner.setOnItemSelectedListener(this);
        Button btn = view.findViewById(R.id.refButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make your toast here
                Log.i("WOMBAT", "ZYGONS EVERYWHERE");
                String text = spinner.getSelectedItem().toString();
                displayTable(v.getRootView(), text);
            }
        });

      //  String st = new String(str);
        displayTable(view, spinner.getSelectedItem().toString());
        // Inflate the layout for this fragment

        return view;
    }
    private void displayTable(View view, String foodType){
        DataBaseHelper db = new DataBaseHelper(getActivity());
        ArrayList<String> dates = db.getAllDates();
        ArrayList<Integer> carbs = db.getAllOfType(foodType);
        ArrayList<String> both = new ArrayList<String>();
        for(int i = 0; i < dates.size(); i++){
            both.add("" + dates.get(i) + " " + carbs.get(i));
        }
        Collections.reverse(both);
        // carbs start at index 15
        //Collections.sort(carbs);
        String date = "";
        String carbsa = "";
        TextView tl = view.findViewById(R.id.typetitle);
        tl.setText("" + foodType);
        // ONLY ORDER IN DATES FROM MOST RECENT TO OLDEST - MORE CAN BE ADDED (PROBABLY)
        for(int i = 0; i < 3; i++){
            switch(i){
                case 0:
                    Log.i("wombat", "" + dates.get(i));
                    TextView td1 = view.findViewById(R.id.first_date);
                    TextView tc1 = view.findViewById(R.id.first_carb);
                    Log.i("wombat", "" + td1.getText());
                    date = both.get(i).substring(0, 14);
                    carbsa = both.get(i).substring(14);
                    td1.setText("" + date);
                    tc1.setText("" + carbsa);
                    break;
                case 1:
                    Log.i("wombat", "" + dates.get(i));
                    TextView td2 = (TextView)view.findViewById(R.id.second_date);
                    TextView tc2 = (TextView)view.findViewById(R.id.second_carb);
                    Log.i("wombat", "" + td2.getText());
                    date = both.get(i).substring(0, 14);
                    carbsa = both.get(i).substring(14);
                    td2.setText("" + date);
                    tc2.setText("" + carbsa);
                    break;
                case 2:
                    Log.i("wombat", "" + dates.get(i));
                    TextView td3 = (TextView)view.findViewById(R.id.third_date);
                    TextView tc3 = (TextView)view.findViewById(R.id.third_carb);
                    Log.i("wombat", "" + td3.getText());
                    date = both.get(i).substring(0, 14);
                    carbsa = both.get(i).substring(14);
                    td3.setText("" + date);
                    tc3.setText("" + carbsa);
                    break;
            }
        }
    }
}