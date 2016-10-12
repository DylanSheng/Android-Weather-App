package ca.dylansheng.weatherapp.db;

import android.content.Context;
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

    public static final String CREATE_DB = "create table db ("
            + "id integer primary key autoincrement, "
            + "cityName text, "
            + "lon double, "
            + "lat double, "
            + "temp double)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
