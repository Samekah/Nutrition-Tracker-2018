package com.csed.csedsemester2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context context) {
        super(context, "CSED.db", null, 1);

    }

    //creates database table
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tblGoal (goalID INTEGER, " +
                "userID INTEGER, " +
                "goal TEXT, " +
                "percentage INTEGER, " +
                "goalReached TEXT, " +
                "PRIMARY KEY (goalID), FOREIGN KEY(userID) REFERENCES tblAccount (userID))");

        db.execSQL("CREATE TABLE tblProgress (dateLog INTEGER, userID INTEGER, " +
                "time INTEGER, sizeOfMeal TEXT, carbohydrates INTEGER, protein INTEGER, milkAndDairy INTEGER,  fruitAndVeg INTEGER, " +
                " fatsAndSugars INTEGER, PRIMARY KEY (dateLog, userID), FOREIGN KEY(userID) REFERENCES tblAccount (userID))");


        db.execSQL("CREATE TABLE tblAccount (userID INTEGER, firstName TEXT, lastName TEXT, gender TEXT, PRIMARY KEY (userID ))");

       /* insertDataGoal(1,1,"carbohydrates",20,"F");
        insertDataGoal(2,1,"protein",20,"F");
        insertDataGoal(3,1,"milkAndDairy",20,"F");
        insertDataGoal(4,1,"fruitAndVeg",20,"F");
        insertDataGoal(5,1,"fatsAndSugars",20,"F");*/
    }

    //deletes and rebuilds updated database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  tblProgress");
        db.execSQL("DROP TABLE IF EXISTS  tblAccount");
        db.execSQL("DROP TABLE IF EXISTS  tblGoal");
        onCreate(db);
    }

    //inserts data into the db
    public boolean insertDataProgress(String dl, int uId, int ti, String som, int carb, int pro, int mad, int fav, int fas){
        String dandtime;
        //sets up data to be inputted
        Log.d("get1111",som);
        Log.d("get1111",dl+"");
        Log.d("get1111",carb+"");
        if(String.valueOf(ti).length()==4){
           dandtime =dl +" 00"+ti;
       }
        if(String.valueOf(ti).length()==5){
            dandtime =dl +" 0"+ti;
        }
        else {
            dandtime = dl +" "+ti;;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dateLog", dandtime);
        cv.put("userID", uId);
        cv.put("sizeOfMeal", som);
        cv.put("carbohydrates", carb);
        cv.put("protein", pro);
        cv.put("milkAndDairy", mad);
        cv.put("fruitAndVeg", fav);
        cv.put("fatsAndSugars", fas);


        //inserts data and checks that there wasn't an error

        Long result = db.insert("tblProgress", null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertDataGoal(int gId, int uId, String g, int p, String gr){

        //sets up data to be inputted

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("goalID", gId);
        cv.put("userID", uId);
        cv.put("goal", g);
        cv.put("percentage", p);
        cv.put("goalReached", gr);

        //inserts data and checks that there wasn't an error
        Log.d("GOAL ID INSERT", Integer.toString(gId));
        Log.d("PERCENTAGE INSERT", Integer.toString(p));

        Long result = db.insert("tblGoal", null, cv);
        if(result == -1) {
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertDataAccount(int uId, String fn, String ln, String ge){

        //sets up data to be inputted

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userID", uId);
        cv.put("firstName", fn);
        cv.put("lastName", ln);
        cv.put("gender", ge);

        //inserts data and checks that there wasn't an error

        Long result = db.insert("tblAccount", null, cv);
        if(result == -1) {
            return false;
        }
        else{
            return true;
        }
    }

    public int todaysDate(){
        Date dt = new Date();
        String dlbc;
        int dl = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        dlbc =(sdf.format(dt));
        Log.i("TIME",dlbc);
        if(dlbc.length()<8){

        }
       //
        dl = Integer.parseInt(dlbc);
        return dl;
    }

    public int currentTime(){
        Date tm = new Date();
        String tmbc;
        int tme = 0;

        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
        tmbc =(sdf2.format(tm));

        Log.d("time is", tmbc );

        tme = Integer.valueOf(tmbc);

        return tme;
    }

    public ArrayList<Integer> progressCarbsRetreiver(String date){
//        Log.i("test12","D");
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> carbs = new ArrayList<Integer>();
        String SQL="SELECT carbohydrates FROM tblProgress WHERE dateLog LIKE '" + date +"%'";
        Cursor cCr = db.rawQuery(SQL,null);

        for(cCr.moveToFirst(); !cCr.isAfterLast(); cCr.moveToNext()) {

            Log.d("test 1", Integer.toString(cCr.getInt(cCr.getColumnIndex("carbohydrates"))));

            carbs.add(cCr.getInt(cCr.getColumnIndex("carbohydrates")));
        }
        Log.i("test12",carbs.size()+"");

        cCr.close();
        return carbs;
    }
    public ArrayList<String> retrieveTodaysTimes(String date){
        Log.i("test1234",date);
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> carbs = new ArrayList<String>();
        String SQL="SELECT dateLog FROM tblProgress WHERE dateLog LIKE '" + date +"%'";
        Cursor cCr = db.rawQuery(SQL,null);

        for(cCr.moveToFirst(); !cCr.isAfterLast(); cCr.moveToNext()) {

            Log.d("test 1", Integer.toString(cCr.getInt(cCr.getColumnIndex("dateLog"))));

            carbs.add(cCr.getString(cCr.getColumnIndex("dateLog")));
        }

        cCr.close();
        return carbs;
    }
    public ArrayList<Integer> getAllOfType(String foodType){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> carbs = new ArrayList<Integer>();
        String SQL="SELECT " + foodType + " FROM tblProgress";
        Cursor cCr = db.rawQuery(SQL,null);

        for(cCr.moveToFirst(); !cCr.isAfterLast(); cCr.moveToNext()) {

            Log.d("test 1", Integer.toString(cCr.getInt(cCr.getColumnIndex(foodType))));

            carbs.add(cCr.getInt(cCr.getColumnIndex(foodType)));
        }
        Log.i("test12",carbs.size()+"");

        cCr.close();
        return carbs;
    }
    public ArrayList<String> getAllDates(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> times = new ArrayList<String>();
        String SQL="SELECT dateLog FROM tblProgress";
        Cursor cCr = db.rawQuery(SQL,null);

        for(cCr.moveToFirst(); !cCr.isAfterLast(); cCr.moveToNext()) {

            Log.d("test 1", Integer.toString(cCr.getInt(cCr.getColumnIndex("dateLog"))));

            times.add(cCr.getString(cCr.getColumnIndex("dateLog")));
        }
        Log.i("test12",times.size()+"");

        cCr.close();
        return times;
    }
    public ArrayList<Integer> progressProteinRetreiver(String date){
        Log.d("test 2","QQ");
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> pro = new ArrayList<Integer>();

        String SQL="SELECT protein FROM tblProgress WHERE dateLog LIKE '" + date +"%'";
        Cursor cpr = db.rawQuery(SQL,null);

        for(cpr.moveToFirst(); !cpr.isAfterLast(); cpr.moveToNext()) {

            Log.d("test 2", Integer.toString(cpr.getInt(cpr.getColumnIndex("protein"))));

            pro.add(cpr.getInt(cpr.getColumnIndex("protein")));
            Log.i("get1111",""+pro.get(0));
        }

        cpr.close();
        return pro;
    }

    public ArrayList<Integer> progressMadRetreiver(String date){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> mad = new ArrayList<Integer>();

        String SQL="SELECT milkAndDairy FROM tblProgress WHERE dateLog LIKE '" + date +"%'";
        Cursor cmd = db.rawQuery(SQL,null);


        for(cmd.moveToFirst(); !cmd.isAfterLast(); cmd.moveToNext()) {

            Log.d("test 3", Integer.toString(cmd.getInt(cmd.getColumnIndex("milkAndDairy"))));

            mad.add(cmd.getInt(cmd.getColumnIndex("milkAndDairy")));
        }

        cmd.close();
        return mad;
    }

    public ArrayList<Integer> progressFavRetreiver(String date){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> fav = new ArrayList<Integer>();

        String SQL="SELECT fruitAndVeg FROM tblProgress WHERE dateLog LIKE '" + date +"%'";
        Cursor cfv = db.rawQuery(SQL,null);

        for(cfv.moveToFirst(); !cfv.isAfterLast(); cfv.moveToNext()) {

            Log.d("test 4", Integer.toString(cfv.getInt(cfv.getColumnIndex("fruitAndVeg"))));

            fav.add(cfv.getInt(cfv.getColumnIndex("fruitAndVeg")));
        }

        cfv.close();
        return fav;
    }

    public ArrayList<Integer> progressFaSRetreiver(String date){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> fas = new ArrayList<Integer>();
        String SQL="SELECT fatsAndSugars FROM tblProgress WHERE dateLog LIKE '" + date +"%'";
        Cursor cfs = db.rawQuery(SQL,null);

        for(cfs.moveToFirst(); !cfs.isAfterLast(); cfs.moveToNext()) {

            Log.d("test 5", Integer.toString(cfs.getInt(cfs.getColumnIndex("fatsAndSugars"))));

            fas.add(cfs.getInt(cfs.getColumnIndex("fatsAndSugars")));
        }

        cfs.close();
        return fas;
    }

    public ArrayList<Integer> goalPercentageRetriever(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> goals = new ArrayList<Integer>();

        Cursor cGoal = db.rawQuery("SELECT percentage FROM  tblGoal", null);


        for(cGoal.moveToFirst(); !cGoal.isAfterLast(); cGoal.moveToNext()) {

            Log.d("goal retrieved", Integer.toString(cGoal.getInt(cGoal.getColumnIndex("percentage"))));

            goals.add(cGoal.getInt(cGoal.getColumnIndex("percentage")));

        }

        cGoal.close();
        return goals;
    }

    public void booleangoalUpdater(int g1, int g2, int g3, int g4, int g5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ArrayList<Integer> gl = new ArrayList<Integer>();
        int[] gd = {1,2,3,4,5};

        gl.add(g1);
        gl.add(g2);
        gl.add(g3);
        gl.add(g4);
        gl.add(g5);


        for(int i = 0; i < 5; i++) {

            cv.put("percentage", gl.get(i));
            db.update("tblGoal",cv,"goalID = ?", new String[]{String.valueOf(gd[i])});
            Log.d("GOAL UPDATED", "YES");
        }

    }

    public void goalReachedUpdater(String goal, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //status being T or F
        //string goal being the food group

        cv.put("goalReached", status);
        db.update("tblGoal",cv,"goal = ?", new String[]{goal});


    }

    public int dailyScore(ArrayList<Integer> x){

        int add = 0;

        for (int i = 0; i<x.size(); i++){
            add = add + x.get(i);
        }

        return add;
    }

    public ArrayList<Integer> dailyTime(String tableName, int date){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> time = new ArrayList<Integer>();
        Cursor cctime = db.rawQuery("SELECT time FROM " + tableName + " WHERE dateLog = " + date, null);

        for(cctime.moveToFirst(); !cctime.isAfterLast(); cctime.moveToNext()) {

            Log.d("test 6", Integer.toString(cctime.getInt(cctime.getColumnIndex("time"))));

            time.add(cctime.getInt(cctime.getColumnIndex("time")));
        }

        cctime.close();
        return time;
    }

}
