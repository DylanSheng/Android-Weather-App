package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import java.util.*;
import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;
import ca.dylansheng.weatherapp.cityInfo.cityAdapter;

public class MainActivity extends Activity implements View.OnClickListener{
    /* declare city info list*/
    private List<cityInfo> cityInfoList = new ArrayList<cityInfo>();

    /* declare addCity button in main activity */
    private Button buttonAddCity;

    /* declare db helper*/
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        /* init add city button */
        buttonAddCity = (Button) findViewById(R.id.buttonAddCity);
        buttonAddCity.setOnClickListener(this);

        /* init database "weatherDB.db", version 1 */
        dbHelper = new MyDatabaseHelper(MainActivity.this, "weatherDB.db", null, 1);
        db = dbHelper.getWritableDatabase();

        /* init city info */
        initCityInfo();

        /* init listView */
        cityAdapter adapter = new cityAdapter(MainActivity.this, R.layout.swipelayout, cityInfoList);
        ListView listView = (ListView) findViewById(R.id.listviewMainActivity);
        listView.setAdapter(adapter);
    }

   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddCity:
                Intent intent2 = new Intent(MainActivity.this, addCity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void initCityInfo() {
        /* query from db */
        String countQuery = "select count(*) from info";
        Cursor countCursor = db.rawQuery(countQuery, null);
        countCursor.moveToFirst();

        int count = countCursor.getInt(0);
        int i = 1;
        while (i <= count) {
            String Query = "Select ROWID from " + "info";
            Cursor cursor = db.rawQuery(Query, null);
            cursor.moveToFirst();

            ArrayList<Integer> rowIDList = new ArrayList<>();

            if (cursor != null && cursor.getCount() > 0) {
                do {
                    rowIDList.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cityInfo city = dbHelper.readCityInfoByIndex(db, rowIDList.get(i - 1));
            cityInfoList.add(city);
            ++i;
        }
    }
}