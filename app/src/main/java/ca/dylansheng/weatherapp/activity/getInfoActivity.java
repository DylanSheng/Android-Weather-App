package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.adapter.forecastAdapter;
import ca.dylansheng.weatherapp.adapter.hourlyAdapter;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.cityInfo.cityInfoDaily;
import ca.dylansheng.weatherapp.cityInfo.cityInfoHourly;
import ca.dylansheng.weatherapp.cityInfo.cityInfoOpenWeatherForecast;
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
    //private TextView getInfoActivityRelativeLayoutTextViewWindDeg;
    private TextView getInfoActivityRelativeLayoutTextViewCloudiness;
    private ListView getInfoActivityListView;
    private ImageView getInfoActivityListViewImageView;
    private RecyclerView getInfoActivityRecycleLayout;

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
        //getInfoActivityRelativeLayoutTextViewWindDeg = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewWindDeg);
        getInfoActivityRelativeLayoutTextViewCloudiness = (TextView) findViewById(R.id.getInfoActivityRelativeLayoutTextViewCloudiness);
        getInfoActivityListView = (ListView) findViewById(R.id.getInfoActivityListView);
        getInfoActivityListViewImageView = (ImageView) findViewById(R.id.getInfoActivityListViewImageView);
        getInfoActivityRecycleLayout = (RecyclerView) findViewById(R.id.getInfoActivityRecycleLayout);

        bar = (ProgressBar) findViewById(R.id.progressBar);

        dbHelper = new MyDatabaseHelper(getInfoActivity.this, "weatherDB.db", null, 1);



        String cityName = new String();
        /* check if there is any info passing by other activity, if so, get cityName*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityName = extras.getString("cityNameKey");
        }


        /* check if city existed in the db */
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select cityName from " + "info" + " where " + "cityName" + " = " + "\"" + cityName + "\"";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() > 0) {//match found
            cityInfo city = dbHelper.readCityInfoByCityName(db, cityName);
            getInfoActivity.this.updateUIInfo(city);
        } else {
            cursor.close();
            AsyncTask task1 = new getWeather().execute(cityName);
            try {
                task1.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* AsyncTask for network connection branch */
        /* task1 for get city longitude, latitude, temperature by OpenWeather API*/
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

    class getWeather extends AsyncTask<String, Integer, AsyncTaskResult<cityInfo>> {
        private Exception exception;

        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }
        protected  AsyncTaskResult<cityInfo> doInBackground(String... strings) {
            try {
                Log.d("getWeather", "getweather back");

                getInfoFromWeb getInfoFromWeb = new getInfoFromWeb(strings[0]);
                cityInfo city = getInfoFromWeb.generateCityInfo();

                return new AsyncTaskResult<cityInfo>(city);
            } catch (Exception e) {
                this.exception = e;

                return new AsyncTaskResult<cityInfo>(e);
            }
        }



        @Override
        protected void onPostExecute( AsyncTaskResult<cityInfo> result) {
            super.onPostExecute(result);
            Log.d("getWeather", "getweather post");

            if ( result.getError() != null ) {
                // error handling here

                Toast.makeText(getInfoActivity.this, "The city is currently unavailable. Please try agin.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getInfoActivity.this, addCity.class);
                startActivity(intent2);
            }  else if ( isCancelled()) {
                // cancel handling here
            } else {

                cityInfo city = result.getResult();
                // result handling here


                getInfoActivityTextViewCityName.setText(city.cityName);
                getInfoActivityTextViewTemp.setText(Integer.toString(city.cityInfoOpenWeather.temperature) + "°");
                getInfoActivityTextViewCondition.setText(city.cityInfoOpenWeather.condition + ": " + city.cityInfoOpenWeather.description);


                String weatherId = city.cityInfoOpenWeather.weatherId;
                Drawable backgroundImage = null;
                switch (weatherId.charAt(0)) {
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
                        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getInfoActivity.this);
                        backgroundImage = wallpaperManager.getDrawable();
                        break;
                }
                getInfoActivityImageViewCityImage.setBackground(backgroundImage);

            /* adapter */
                ArrayList<cityInfoOpenWeatherForecast> cityInfoDailyInfoArrayList = new ArrayList<>();
                cityInfoDaily cityInfoDaily = new cityInfoDaily(city.cityInfoOpenWeatherForecastArrayList);
                cityInfoDailyInfoArrayList = cityInfoDaily.getCityInfoDailyInfoArrayList();

                forecastAdapter adapter = new forecastAdapter(getInfoActivity.this, R.layout.get_info_activity_listview, cityInfoDailyInfoArrayList);
                getInfoActivityListView.setAdapter(adapter);
            /* adapter done */

                ArrayList<cityInfoOpenWeatherForecast> cityInfoHourlyInfoArrayList = new ArrayList<>();
                cityInfoHourly cityInfoHourly = new cityInfoHourly(city.cityInfoOpenWeatherForecastArrayList);
                cityInfoHourlyInfoArrayList = cityInfoHourly.getCityInfoHourlyInfoArrayList();
                // 2. set layoutManger
                getInfoActivityRecycleLayout.setLayoutManager(new LinearLayoutManager(getInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));
                hourlyAdapter adapter2 = new hourlyAdapter(getInfoActivity.this, cityInfoHourlyInfoArrayList);
                getInfoActivityRecycleLayout.setAdapter( adapter2);
                getInfoActivityRecycleLayout.setItemAnimator(new DefaultItemAnimator());


                getInfoActivityRelativeLayoutTextViewPressure.setText(Integer.toString(city.cityInfoOpenWeather.pressure) + " hPa");
                getInfoActivityRelativeLayoutTextViewHumidity.setText(Integer.toString(city.cityInfoOpenWeather.humidity) + " %");
                getInfoActivityRelativeLayoutTextViewTempMin.setText(city.cityInfoOpenWeather.temperatureMin + "°");
                getInfoActivityRelativeLayoutTextViewTempMax.setText(city.cityInfoOpenWeather.temperatureMax + "°");
                getInfoActivityRelativeLayoutTextViewWindspeed.setText(city.cityInfoOpenWeather.windSpeed + " m/s");
                //getInfoActivityRelativeLayoutTextViewWindDeg.setText(city.cityInfoOpenWeather.windDeg);
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

    public class AsyncTaskResult<T> {
        private T result;
        private Exception error;

        public T getResult() {
            return result;
        }

        public Exception getError() {
            return error;
        }

        public AsyncTaskResult(T result) {
            super();
            this.result = result;
        }

        public AsyncTaskResult(Exception error) {
            super();
            this.error = error;
        }
    }

    public void updateUIInfo(cityInfo city){
        getInfoActivityTextViewCityName.setText(city.cityName);
        getInfoActivityTextViewTemp.setText(Integer.toString(city.cityInfoOpenWeather.temperature) + "°");
        getInfoActivityTextViewCondition.setText(city.cityInfoOpenWeather.condition + ": " + city.cityInfoOpenWeather.description);


        String weatherId = city.cityInfoOpenWeather.weatherId;
        Drawable backgroundImage = null;
        switch (weatherId.charAt(0)) {
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
                final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getInfoActivity.this);
                backgroundImage = wallpaperManager.getDrawable();
                break;
        }
        getInfoActivityImageViewCityImage.setBackground(backgroundImage);

            /* adapter */
        ArrayList<cityInfoOpenWeatherForecast> cityInfoDailyInfoArrayList = new ArrayList<>();
        cityInfoDaily cityInfoDaily = new cityInfoDaily(city.cityInfoOpenWeatherForecastArrayList);
        cityInfoDailyInfoArrayList = cityInfoDaily.getCityInfoDailyInfoArrayList();

        forecastAdapter adapter = new forecastAdapter(getInfoActivity.this, R.layout.get_info_activity_listview, cityInfoDailyInfoArrayList);
        getInfoActivityListView.setAdapter(adapter);
            /* adapter done */

        ArrayList<cityInfoOpenWeatherForecast> cityInfoHourlyInfoArrayList = new ArrayList<>();
        cityInfoHourly cityInfoHourly = new cityInfoHourly(city.cityInfoOpenWeatherForecastArrayList);
        cityInfoHourlyInfoArrayList = cityInfoHourly.getCityInfoHourlyInfoArrayList();
        // 2. set layoutManger
        getInfoActivityRecycleLayout.setLayoutManager(new LinearLayoutManager(getInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));
        hourlyAdapter adapter2 = new hourlyAdapter(getInfoActivity.this, cityInfoHourlyInfoArrayList);
        getInfoActivityRecycleLayout.setAdapter( adapter2);
        getInfoActivityRecycleLayout.setItemAnimator(new DefaultItemAnimator());


        getInfoActivityRelativeLayoutTextViewPressure.setText(Integer.toString(city.cityInfoOpenWeather.pressure) + " hPa");
        getInfoActivityRelativeLayoutTextViewHumidity.setText(Integer.toString(city.cityInfoOpenWeather.humidity) + " %");
        getInfoActivityRelativeLayoutTextViewTempMin.setText(city.cityInfoOpenWeather.temperatureMin + "°");
        getInfoActivityRelativeLayoutTextViewTempMax.setText(city.cityInfoOpenWeather.temperatureMax + "°");
        getInfoActivityRelativeLayoutTextViewWindspeed.setText(city.cityInfoOpenWeather.windSpeed + " m/s");
        //getInfoActivityRelativeLayoutTextViewWindDeg.setText(city.cityInfoOpenWeather.windDeg);
        getInfoActivityRelativeLayoutTextViewCloudiness.setText(city.cityInfoOpenWeather.cloudiness + "%");
    }
}
