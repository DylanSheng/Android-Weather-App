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
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

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
        AsyncTask task1 = new getWeather().execute(city.cityName);
        try {
            task1.get(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* task2 for get city image by Google Image API */
        AsyncTask task2 = new getCityImage().execute(city.cityName);

        /* task3 for get timezone by Google API*/
        new getTimeZone().execute(city);
        /* waiting for task1 finished */


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

    class getWeather extends AsyncTask<String, Void, Double> {
        private Exception exception;
        protected Double doInBackground(String... strings){
            try{
                String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
                String url_Name = "http://api.openweathermap.org/data/2.5/weather?q=" + strings[0] + "&APPID=" + key;
                //InputStream is = new URL(url).openStream();
                URL openWeather = new URL(url_Name);
                URLConnection yc = openWeather.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                String str = new String();
                while ((inputLine = in.readLine()) != null)
                {
                    str = str.concat(inputLine);
                }
                Double s =  parse(str);
                in.close();

                return s;
            }catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Double temp) {
            super.onPostExecute(temp);
            getInfoActivity.this.textViewCityName.setText(city.cityName);
            city.temperature = temp.intValue();
            getInfoActivity.this.textViewTemp.setText(Integer.toString(city.temperature)+ "°");
            getInfoActivity.this.textViewCondition.setText(city.condition);
            //dbHelper  = new MyDatabaseHelper(getInfoActivity.this,"weatherDB.db",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.buildDatabaseValue(db, city);
        }

        /* parse JSON to get temperature, longitude and latitude */
        private Double parse(String inputLine) throws JSONException {
            JSONObject obj = new JSONObject(inputLine);

            Double temp = obj.getJSONObject("main").getDouble("temp");
            city.latitude = Double.parseDouble(obj.getJSONObject("coord").getString("lat"));
            city.longitude = Double.parseDouble(obj.getJSONObject("coord").getString("lon"));
            city.condition = obj.getJSONArray("weather").getJSONObject(0).getString("main");

            return temp - 273.15;
        }

    }

    class getCityImage extends AsyncTask<String, Void, Bitmap> {
        private Exception exception;
        protected Bitmap doInBackground(String... strings){
            try{
                String googleKey = "AIzaSyBfG7eMBFRS8IfO3evj9DxTb3p35d9YYL8";
                String urlPlaceSearch = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + city.latitude + "," + city.longitude + "&key=" + googleKey + "&radius=500";
                URL openPlaceSearch = new URL(urlPlaceSearch);
                URLConnection yc = openPlaceSearch.openConnection();
                BufferedReader inPlaceSearch = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                String strPlaceSearch = new String();
                while ((inputLine = inPlaceSearch.readLine()) != null)
                {
                    strPlaceSearch = strPlaceSearch.concat(inputLine);
                }
                String photo_reference =  parse(strPlaceSearch);
                inPlaceSearch.close();

                String urlCityImage = "https://maps.googleapis.com/maps/api/place/photo?" + "maxwidth=400&" + "photoreference=" + photo_reference + "&key=" + googleKey;
                URL url = new URL(urlCityImage);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                URL openCityImage = new URL(urlCityImage);
                URLConnection ycCityImage = openCityImage.openConnection();
                BufferedReader inCityImage = new BufferedReader(new InputStreamReader(ycCityImage.getInputStream()));
                String inputLineCityImage;
                String strCityImage = new String();
                while ((inputLineCityImage = inCityImage.readLine()) != null)
                {
                    strCityImage = strCityImage.concat(inputLineCityImage);
                }
                inCityImage.close();

                return bmp;
            }catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);
            //getInfoActivity.this.textViewCityName.setText(city.cityName);

            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
            imageViewCityImage.setBackground(ob);

            Bitmap bitmap = ob.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] cityImage = stream.toByteArray();


            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.insertCityImage(db, city.cityName, cityImage);

        }
        public String parse(String inputLine) throws JSONException {
            JSONObject objPlaceSearch = new JSONObject(inputLine);
            JSONArray arrayPlaceSearch = objPlaceSearch.getJSONArray("results");
            JSONObject objArrayPlaceSearch = arrayPlaceSearch.getJSONObject(0);
            String photo_reference = objArrayPlaceSearch.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

            return photo_reference;
        }
    }

    class getTimeZone extends AsyncTask<cityInfo, Void, ArrayList<Long>>{
        private Exception exception;
        @Override
        protected ArrayList<Long> doInBackground(cityInfo... city) {
            try{
                String googleKey = "AIzaSyAFJSsk4C0gY1pNwYzfqfVcAnRyfpqTy4Q";
                Calendar c = Calendar.getInstance();
                Long timestamp = c.getTimeInMillis() / 1000;
                String urlPlaceSearch = "https://maps.googleapis.com/maps/api/timezone/json?" + "location=" + city[0].latitude + "," + city[0].longitude + "&key=" + googleKey + "&timestamp=" + timestamp.toString();
                URL openPlaceSearch = new URL(urlPlaceSearch);
                URLConnection yc = openPlaceSearch.openConnection();
                BufferedReader inPlaceSearch = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                String strPlaceSearch = new String();
                while ((inputLine = inPlaceSearch.readLine()) != null)
                {
                    strPlaceSearch = strPlaceSearch.concat(inputLine);
                }
                ArrayList<Long> offset =  parse(strPlaceSearch);
                inPlaceSearch.close();

                return offset;
            }catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Long> offset) {
            super.onPostExecute(offset);


            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.insertTimezone(db, city.cityName, offset.get(0), offset.get(1));

        }

        public ArrayList<Long> parse(String inputLine) throws JSONException {
            JSONObject obj = new JSONObject(inputLine);
            city.timezone = Long.parseLong(obj.getString("rawOffset"));
            city.daylight = Long.parseLong(obj.getString("dstOffset"));
            ArrayList<Long> v = new ArrayList<Long>();
            v.add(city.timezone);
            v.add(city.daylight);
            return v;
        }
    }
}
