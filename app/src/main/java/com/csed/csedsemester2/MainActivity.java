package com.csed.csedsemester2;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public String date;
    public BarChart barChart;
    public TextView graphName;

    public ArrayList<Integer> List;


    DataBaseHelper db;
    View view;
    //What is this line of code for?

    static final String EXTRA_MESSAGE = "com.csed.csedsemester2.MESSAGE";

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment fragment = null;
    private Class fragmentClass = HomeFragment.class;

    //declare database like this in each class
 //   DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        createGraph();
      //  cG();
        setTitle("Food Tracker");
        setContentView(R.layout.activity_main);

        if(doesDatabaseExist( this, "CSED.db") == false) {
            goalInsert();
        }

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Finds drawer layout
        mDrawerLayout = findViewById(R.id.drawer_layout);

        nvDrawer = findViewById(R.id.nav_view);

        setupDrawerContent(nvDrawer);

        //Creates a new instance of homepagae fragment
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Loads homepage fragment
        getSupportFragmentManager().beginTransaction().add(R.id.flContent, fragment).commit();


        scheduleAlarm();
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    //to test if data will be added to database, will delete soon
    public void goalInsert() {
        db = new DataBaseHelper(this);

        db.insertDataGoal(1, 1, "carbohydrates", 20, "F");
        db.insertDataGoal(2, 1, "protein", 20, "F");
        db.insertDataGoal(3, 1, "milkAndDairy", 20, "F");
        db.insertDataGoal(4, 1, "fruitAndVeg", 20, "F");
        db.insertDataGoal(5, 1, "fatsAndSugars", 20, "F");


        ArrayList<Integer> goals = new ArrayList<Integer>();
        goals = db.goalPercentageRetriever();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void cG(){

    }
    public void createGraph(){
        db = new DataBaseHelper(this);
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
        date = DATE_FORMAT.format(today);
        barChart = (BarChart) findViewById(R.id.graph);
        List = db.progressCarbsRetreiver(date);
        ArrayList<String> times = db.retrieveTodaysTimes(date);

//        graphName.setText("Graph showing carbohydrate score over time");

        ArrayList<BarEntry> barEntries = new ArrayList();
        int x = 0;
 //       while(x<times.size()){
            Log.i("times",times.get(x)+"");
   //         String time10=times.get(x).substring(7,9);
//            time10=time10+'.';
            //           time10=time10+times.get(x).charAt(10);
//            Log.i("times",time10);

            barEntries.add(new BarEntry(1f, 2f));
     //       x++;
      //  }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1600");
        theDates.add("1200");

        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.invalidate();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem){


        switch(menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_goal_fragment:
                fragmentClass = GoalsFragment.class;
                break;
            case R.id.nav_graphs_fragment:
                fragmentClass = GraphsFragment.class;
                break;
            case R.id.nav_days_fragment:
                fragmentClass = DaysFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }



        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }





    public void buttonOnClick(View view) {
        // Start the size activity
        Intent intent = new Intent(this, SizeActivity.class);
        // Need an intent to start a new activity
        String str = null; // putExtra takes two arguments
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }

    public void scheduleAlarm(){
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            long firstMillis = System.currentTimeMillis();
            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, AlarmManager.INTERVAL_DAY, pIntent);
        }



}