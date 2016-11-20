package ca.dylansheng.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORLDCITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
