package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityAdapter;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.cityInfo.cityInfoOpenWeatherForecast;
import ca.dylansheng.weatherapp.cityInfo.forecastAdapter;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;
import ca.dylansheng.weatherapp.web.getInfoFromWeb;

/**
 * Created by sheng on 2016/10/10.
 */

public class getInfoActivity extends Activity implements View.OnClickListener {
    Context context;
    /* define interface parameters */
    private TextView getInfoActivityTextViewCityName;
    private TextView getInfoActivityTextViewTemp;
    private Button getInfoActivityButtonBackMain;
    private ImageView getInfoActivityImageViewCityImage;
    private TextView getInfoActivityTextViewCondition;

    private TextView getInfoActivityRelativeLayoutTextViewPressure;
    private TextView getInfoActivityRelativeLayoutTextViewHumidity;
    private TextView getInfoActivityRelativeLayoutTextViewTempMin;
    private TextView getInfoActivityRelativeLayoutTextViewTempMax;
    private TextView getInfoActivityRelativeLayoutTextViewWindspeed;
    private TextView getInfoActivityRelativeLayoutTextViewWindDeg;
    private TextView getInfoActivityRelativeLayoutTextViewCloudiness;
    private ListView getInfoActivityListView;
    private ImageView getInfoActivityListViewImageView;

    private ProgressBar bar;
    /* define variables and dbs */
    //private cityInfo city = new cityInfo();
    private MyDatabaseHelper dbHelper;
    private List<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecast = new ArrayList<cityInfoOpenWeatherForecast>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info_activity);

        /* init layout and db */
        getInfoActivityTextViewCityName = (TextView) findViewById(R.id.getInfoActivityTextViewCityName);
        getInfoActivityTextViewTemp = (TextView) findViewById(R.id.getInfoActivityTextViewTemp);
        getInfoActivityButtonBackMain = (Button) findViewById(R.id.getInfoActivityButtonBackMain);
        getInfoActivityButtonBackMain.setOnClickListener(this);
        getInfoActivityImageViewCityImage = (ImageView) findViewById(R.id.getInfoActivityImageViewCityImage);
        getInfoActivityTextViewCondition = (TextView) findViewById(R.id.getInfoActivityTextViewCondition);

        getInfoActivityRelativeLayoutTextViewPressure = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewPressure);
        getInfoActivityRelativeLayoutTextViewHumidity = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewHumidity);
        getInfoActivityRelativeLayoutTextViewTempMin = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewTempMin);
        getInfoActivityRelativeLayoutTextViewTempMax = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewTempMax);
        getInfoActivityRelativeLayoutTextViewWindspeed  =(TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewWindspeed);
        getInfoActivityRelativeLayoutTextViewWindDeg = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewWindDeg);
        getInfoActivityRelativeLayoutTextViewCloudiness = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewCloudiness);
        getInfoActivityListView = (ListView) findViewById(R.id.getInfoActivityListView);
        getInfoActivityListViewImageView = (ImageView) findViewById(R.id.getInfoActivityListViewImageView);

        bar = (ProgressBar) findViewById(R.id.progressBar);

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
            task1.get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getInfoActivityButtonBackMain:
                Intent intent = new Intent(getInfoActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class getWeather extends AsyncTask<String, Integer, cityInfo> {
        private Exception exception;

        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }
        protected cityInfo doInBackground(String... strings) {
            try {
                Log.d("getWeather", "getweather back");

                getInfoFromWeb getInfoFromWeb = new getInfoFromWeb(strings[0]);
                cityInfo city = getInfoFromWeb.generateCityInfo();
                int a;
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

            getInfoActivityTextViewCityName.setText(city.cityName);
            getInfoActivityTextViewTemp.setText(Integer.toString(city.cityInfoOpenWeather.temperature) + "°");
            getInfoActivityTextViewCondition.setText(city.cityInfoOpenWeather.condition + ": " + city.cityInfoOpenWeather.description);


            String weatherId = city.cityInfoOpenWeather.weatherId;
            Drawable backgroundImage = null;
            switch(weatherId.charAt(0)){
                case '2':   //ThunderStorm
                    backgroundImage = ResourcesCompat.getDrawable(getResources(), R.drawable.thunderstorm, null);
                    break;
                case '3':
                case '5':
                    backgroundImage = ResourcesCompat.getDrawable(getResources(), R.drawable.rainy, null);
                    break;
                case '6':
                    backgroundImage = ResourcesCompat.getDrawable(getResources(), R.drawable.snow, null);
                    break;
                case '8':
                    backgroundImage = ResourcesCompat.getDrawable(getResources(), R.drawable.clouds, null);
                    Log.d("getinfo", "clouds");
                    break;
                default:
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                    backgroundImage = wallpaperManager.getDrawable();
                    break;
            }
            getInfoActivityImageViewCityImage.setBackground(backgroundImage);

            /* adapter */
            forecastAdapter adapter = new forecastAdapter(getInfoActivity.this, R.layout.get_info_activity_listview, city.cityInfoOpenWeatherForecastArrayList);
            getInfoActivityListView.setAdapter(adapter);
            /* adapter done */

            getInfoActivityRelativeLayoutTextViewPressure.setText(Integer.toString(city.cityInfoOpenWeather.pressure) + " hPa");
            getInfoActivityRelativeLayoutTextViewHumidity.setText(Integer.toString(city.cityInfoOpenWeather.humidity) + " %");
            getInfoActivityRelativeLayoutTextViewTempMin.setText(city.cityInfoOpenWeather.temperatureMin + "°");
            getInfoActivityRelativeLayoutTextViewTempMax.setText(city.cityInfoOpenWeather.temperatureMax + "°");
            getInfoActivityRelativeLayoutTextViewWindspeed.setText(city.cityInfoOpenWeather.windSpeed + " m/s");
            getInfoActivityRelativeLayoutTextViewWindDeg.setText(city.cityInfoOpenWeather.windDeg);
            getInfoActivityRelativeLayoutTextViewCloudiness.setText(city.cityInfoOpenWeather.cloudiness + "%");



            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.buildDatabaseValue(db, city);
            dbHelper.insertCityImage(db, city.cityName, city.cityInfoGoogleImage.cityImage);
            dbHelper.insertTimezone(db, city.cityName, city.cityInfoTimezone.timezone, city.cityInfoTimezone.daylight);
            dbHelper.buildDatabaseValueForecast(db, city);

            bar.setVisibility(View.GONE);
        }
    }
}
