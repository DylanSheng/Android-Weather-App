package ca.dylansheng.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;

/**
 * Created by sheng on 2016/10/12.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_INFO = "create table info ("
            + "cityName text, "
            + "longitude double, "
            + "latitude double, "
            + "condition text, "
            + "description text, "
            + "icon text, "
            + "temperature integer,"
            + "pressure long, "
            + "timezone long, "
            + "daylight long, "
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
        ContentValues values = new ContentValues();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void buildDatabaseValue(SQLiteDatabase db, cityInfo city){
        ContentValues values = new ContentValues();

        String Query = "Select cityName from " + "info" + " where " + "cityName" + " = " + "\"" + city.cityName + "\"";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() > 0){//match found
            values.put("temperature", city.cityInfoOpenWeather.temperature);
            db.update("info", values, "cityName = ?", new String[]{city.cityName});
        }else {
            cursor.close();
            values.put("cityName", city.cityName);

            values.put("longitude", city.cityInfoOpenWeather.longitude);
            values.put("latitude", city.cityInfoOpenWeather.latitude);

            values.put("condition", city.cityInfoOpenWeather.condition);
            values.put("description", city.cityInfoOpenWeather.description);
            values.put("icon", city.cityInfoOpenWeather.icon);

            values.put("temperature", city.cityInfoOpenWeather.temperature);
            values.put("pressure", city.cityInfoOpenWeather.pressure);
            if(city.cityName != null) {
                db.insert("info", null, values);
            }
            values.clear();
        }
    }

    public void insertCityImage(SQLiteDatabase db, String cityName, byte[] image) throws SQLiteException {
        ContentValues values = new ContentValues();
        values.put("cityImage", image);
        db.update("info", values, "cityName = ?", new String[]{cityName});
    }

    public cityInfo readCityInfoByIndex(SQLiteDatabase db, Integer index) throws SQLiteException{
        String Query = "Select * from " + "info" + " where " + "ROWID" + " = " + index.toString();
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0) {
            cityInfo city = new cityInfo();
            city.cityName = cursor.getString(cursor.getColumnIndex("cityName"));

            city.cityInfoOpenWeather.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            city.cityInfoOpenWeather.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));

            city.cityInfoOpenWeather.condition = cursor.getString(cursor.getColumnIndex("condition"));
            city.cityInfoOpenWeather.description = cursor.getString(cursor.getColumnIndex("description"));
            city.cityInfoOpenWeather.icon = cursor.getString(cursor.getColumnIndex("icon"));

            city.cityInfoOpenWeather.temperature = cursor.getInt(cursor.getColumnIndex("temperature"));
            city.cityInfoOpenWeather.pressure = cursor.getInt(cursor.getColumnIndex("pressure"));

            city.cityInfoGoogleImage.cityImage = cursor.getBlob(cursor.getColumnIndex("cityImage"));
            city.cityInfoTimezone.timezone = cursor.getLong(cursor.getColumnIndex("timezone"));
            city.cityInfoTimezone.daylight = cursor.getLong(cursor.getColumnIndex("daylight"));
            cursor.close();
            return city;
        }else{
            return null;
        }
    }

    public void removeCityInfoByName(SQLiteDatabase db, String cityName){
        String table = "info";
        String whereClause = "cityName =?";
        String[] whereArgs = new String[] { cityName };
        db.delete(table, whereClause, whereArgs);
    }

    public void insertTimezone(SQLiteDatabase db, String cityName, Long timezone, Long daylight){
        ContentValues values = new ContentValues();
        values.put("timezone", timezone);
        values.put("daylight", daylight);
        db.update("info", values, "cityName = ?", new String[]{cityName});
    }
}
