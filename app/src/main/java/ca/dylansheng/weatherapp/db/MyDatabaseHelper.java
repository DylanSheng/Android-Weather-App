package ca.dylansheng.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.security.DigestOutputStream;

import ca.dylansheng.weatherapp.cityInfo.cityInfo;

/**
 * Created by sheng on 2016/10/12.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{
/*    private String cityName;
    private Double lon;
    private Double lat;
    private Double temp;*/

    public static final String CREATE_INFO = "create table info ("
            + "id integer primary key autoincrement, "
            + "cityName text, "
            + "longitude double, "
            + "latitude double, "
            + "temperature integer, "
            + "cityImage BLOB);";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INFO);
        //Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void buildDatabaseValue(SQLiteDatabase db, cityInfo city){
        ContentValues values = new ContentValues();

        String Query = "Select cityName from " + "info" + " where " + "cityName" + " = " + "\"" + city.cityName + "\"";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() > 0){//match found
            values.put("temperature", city.temperature);
            db.update("info", values, "cityName = ?", new String[]{city.cityName});
        }else {
            cursor.close();
            values.put("cityName", city.cityName);
            values.put("longitude", city.longitude);
            values.put("latitude", city.latitude);
            values.put("temperature", city.temperature);
            db.insert("info", null, values);
        }
    }

    public cityInfo readDatabaseValue(SQLiteDatabase db, String cityName){
        String Query = "Select * from " + "info" + " where " + "cityName" + " = " + "\"" + cityName + "\"";
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();
        cityInfo city = new cityInfo();
        city.cityName = cityName;
        city.temperature = cursor.getInt(cursor.getColumnIndex("temperature"));
        city.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
        city.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));

        cursor.close();
        return city;
    }



}
