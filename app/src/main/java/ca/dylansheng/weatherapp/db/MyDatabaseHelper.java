package ca.dylansheng.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
            + "lon double, "
            + "lat double, "
            + "temp integer);";
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

    public void buildDatabaseValue(SQLiteDatabase db, Integer temperature, String cityName, Double longitude, Double latitude){
        ContentValues values = new ContentValues();
            /*values.put("cityName", "tianjin");
            values.put("lon", 117.2010);
            values.put("lat", 39.0842);
            values.put("temp", 22);s
            db.insert("info", null, values);
            values.clear();*/
        //String whereClause = "cityName = " + cityName;

        String Query = "Select cityName from " + "info" + " where " + "cityName" + " = " + "\"" + cityName + "\"";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() > 0){//match found
            values.put("temp", temperature);
            db.update("info", values, "cityName = ?", new String[]{cityName});
        }else {
            cursor.close();
            values.put("cityName", cityName);
            values.put("lon", longitude);
            values.put("lat", latitude);
            values.put("temp", temperature);
            db.insert("info", null, values);
        }
    }
}
