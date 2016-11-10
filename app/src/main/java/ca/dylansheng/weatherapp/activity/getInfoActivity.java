package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;
import ca.dylansheng.weatherapp.web.getInfoFromWeb;

/**
 * Created by sheng on 2016/10/10.
 */

public class getInfoActivity extends Activity implements View.OnClickListener {
    /* define interface parameters */
    private TextView textViewCityName;
    private TextView textViewTemp;
    private Button buttonChangeCity;
    private ImageView imageViewCityImage;
    private TextView textViewCondition;

    /* define variables and dbs */
    //private cityInfo city = new cityInfo();
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info_activity);

        /* init layout and db */
        textViewCityName = (TextView) findViewById(R.id.textViewCityName);
        textViewTemp = (TextView) findViewById(R.id.textViewTemp);
        buttonChangeCity = (Button) findViewById(R.id.buttonBackMain);
        buttonChangeCity.setOnClickListener(this);
        imageViewCityImage = (ImageView) findViewById(R.id.imageViewCityImage);
        textViewCondition = (TextView) findViewById(R.id.textViewCondition);

        dbHelper = new MyDatabaseHelper(getInfoActivity.this, "weatherDB.db", null, 1);


        String cityName = new String();
        /* check if there is any info passing by other activity, if so, get cityName*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityName = extras.getString("cityNameKey");
        }

        /* AsyncTask for network connection branch */
        /* task1 for get city longitude, latitude, temperature by OpenWeather API*/
        AsyncTask task1 = new getWeather().execute(cityName);
        try {
            task1.get(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBackMain:
                Intent intent = new Intent(getInfoActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class getWeather extends AsyncTask<String, Void, cityInfo> {
        private Exception exception;

        protected cityInfo doInBackground(String... strings) {
            try {
                Log.d("getWeather", "getweather back");

                getInfoFromWeb getInfoFromWeb = new getInfoFromWeb(strings[0]);
                cityInfo city = getInfoFromWeb.generateCityInfo();

                return city;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(cityInfo city) {
            super.onPostExecute(city);
            Log.d("getWeather", "getweather post");

            getInfoActivity.this.textViewCityName.setText(city.cityName);
            getInfoActivity.this.textViewTemp.setText(Integer.toString(city.cityInfoOpenWeather.temperature) + "°");
            getInfoActivity.this.textViewCondition.setText(city.cityInfoOpenWeather.condition);


            Bitmap bitmap = BitmapFactory.decodeByteArray(city.cityInfoGoogleImage.cityImage, 0, city.cityInfoGoogleImage.cityImage.length);

            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
            imageViewCityImage.setBackground(ob);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.buildDatabaseValue(db, city);
            dbHelper.insertCityImage(db, city.cityName, city.cityInfoGoogleImage.cityImage);
            dbHelper.insertTimezone(db, city.cityName, city.cityInfoTimezone.timezone, city.cityInfoTimezone.daylight);
        }
    }
}
