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
import ca.dylansheng.weatherapp.cityInfo.cityInfoGoogleImage;
import ca.dylansheng.weatherapp.cityInfo.cityInfoOpenWeather;
import ca.dylansheng.weatherapp.cityInfo.cityInfoTimezone;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;
import ca.dylansheng.weatherapp.web.getInfoFromWeb;

/**
 * Created by sheng on 2016/10/10.
 */

public class getInfoActivity extends Activity implements View.OnClickListener{
    /* define interface parameters */
    private TextView textViewCityName;
    private TextView textViewTemp;
    private Button buttonChangeCity;
    private ImageView imageViewCityImage;
    private TextView textViewCondition;

    /* define variables and dbs */
    private cityInfo city = new cityInfo();
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

        dbHelper  = new MyDatabaseHelper(getInfoActivity.this,"weatherDB.db",null,1);

        /* check if there is any info passing by other activity, if so, get cityName*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city.cityName = extras.getString("cityNameKey");
        }

        /* AsyncTask for network connection branch */
        /* task1 for get city longitude, latitude, temperature by OpenWeather API*/
        AsyncTask task1 = new getWeather().execute(city);

        /* waiting for task1 finished */
//        try {
//            task1.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        /* task2 for get city image by Google Image API */
        //AsyncTask task2 = new getCityImage().execute(city);

        /* task3 for get timezone by Google API*/
        AsyncTask task3 = new getTimeZone().execute(city);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonBackMain:
                Intent intent = new Intent(getInfoActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class getWeather extends AsyncTask<cityInfo, Void, cityInfoOpenWeather> {
        private Exception exception;
        protected cityInfoOpenWeather doInBackground(cityInfo... cityInfos){
            try{
                getInfoFromWeb getinfofromweb = new getInfoFromWeb(cityInfos[0].cityName);
                cityInfoOpenWeather cityInfoOpenWeather = new cityInfoOpenWeather();
                cityInfoOpenWeather = getinfofromweb.getInfoFromOpenWeather();
                return cityInfoOpenWeather;
            }catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(cityInfoOpenWeather cityInfoOpenWeather) {
            super.onPostExecute(cityInfoOpenWeather);
            city.cityInfoOpenWeather = cityInfoOpenWeather;
            getInfoActivity.this.textViewCityName.setText(city.cityName);
            getInfoActivity.this.textViewTemp.setText(Integer.toString(city.cityInfoOpenWeather.temperature)+ "°");
            getInfoActivity.this.textViewCondition.setText(city.cityInfoOpenWeather.condition);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.buildDatabaseValue(db, city);
            new getCityImage().execute(city);
        }
    }

    class getCityImage extends AsyncTask<cityInfo, Void, Bitmap> {
        private Exception exception;
        protected Bitmap doInBackground(cityInfo... cityInfos){
            try{
                getInfoFromWeb getInfoFromWeb = new getInfoFromWeb(cityInfos[0].cityName);
                Bitmap bmp = getInfoFromWeb.getInfoFromGoogleImage(city);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                return bmp;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);

            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
            imageViewCityImage.setBackground(ob);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] cityImage = stream.toByteArray();
            cityInfoGoogleImage cityInfoGoogleImage = new cityInfoGoogleImage();
            cityInfoGoogleImage.cityImage = cityImage;
            city.cityInfoGoogleImage = cityInfoGoogleImage;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.insertCityImage(db, city.cityName, city.cityInfoGoogleImage.cityImage);

        }
    }

    class getTimeZone extends AsyncTask<cityInfo, Void, cityInfoTimezone>{
        private Exception exception;
        @Override
        protected cityInfoTimezone doInBackground(cityInfo... city) {
            try{
                cityInfoTimezone cityInfoTimezone = new cityInfoTimezone();
                getInfoFromWeb getInfoFromWeb = new getInfoFromWeb(city[0].cityName);
                cityInfoTimezone = getInfoFromWeb.getInfoTimezone(city[0]);
                return cityInfoTimezone;
            }catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(cityInfoTimezone cityInfoTimezone) {
            super.onPostExecute(cityInfoTimezone);
            city.cityInfoTimezone = cityInfoTimezone;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.insertTimezone(db, city.cityName, cityInfoTimezone.timezone, cityInfoTimezone.daylight);

        }
    }
}
