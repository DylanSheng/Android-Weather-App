package ca.dylansheng.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sheng on 2016/11/20.
 */

public class worldCityListDB extends SQLiteOpenHelper{
    private Context mContext;
    public static final String CREATE_WORLDCITY = "create table worldcity ("
            + "worldCityId text, "
            + "cityName text, "
            + "countryCode text, "
            + "country text, "
            + "longitude double, "
            + "latitude double);";

    public worldCityListDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORLDCITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initCityListDB(SQLiteDatabase db) throws JSONException {
        File dbFile = mContext.getDatabasePath("worldcity.db");

        if(!dbFile.exists()){
            String fileName = "world-top-city-list.json";
            String str = readFromFile(fileName);
            initCityListDBParseJSONWorldCity(db, str);
        }


    }

    private String readFromFile(String fileName){
        BufferedReader reader = null;
        String str = new String();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(fileName)));

            // do reading, usually loop until end of file reading
            String mLine;

            while ((mLine = reader.readLine()) != null) {
                //process line
                str = str.concat(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return str;
    }

    private void initCityListDBParseJSONWorldCity(SQLiteDatabase db, String str) throws JSONException {
        JSONArray jsonArray = new JSONArray(str);
        ContentValues values = new ContentValues();
        db.beginTransaction();

        try {
            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                values.put("worldCityId", jsonObject.getString("id"));
                values.put("cityName", jsonObject.getString("cityEn"));
                values.put("countryCode", jsonObject.getString("countryCode"));
                values.put("country", jsonObject.getString("countryEn"));
                values.put("longitude", jsonObject.getDouble("lon"));
                values.put("latitude", jsonObject.getDouble("lat"));

                db.insert("worldcity", null, values);
                values.clear();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    /**
     * Check if the database exist and can be read.
     *
     * @return true if it exists and can be read, false if it doesn't
     */
//    private boolean checkDataBase() {
//        SQLiteDatabase checkDB = null;
//        try {
//            checkDB = SQLiteDatabase.openDatabase("worldcity.db", null, SQLiteDatabase.OPEN_READONLY);
//            checkDB.close();
//        } catch (SQLiteException e) {
//            // database doesn't exist yet.
//        }
//        return checkDB != null;
//    }
}
