package ca.dylansheng.weatherapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.backEnd.getWeather;

/**
 * Created by sheng on 2016/10/10.
 */

public class getInfoActivity extends AppCompatActivity{

    public TextView textViewCityName;
    public TextView textViewTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCityName = (TextView) findViewById(R.id.textViewCityName);
        textViewTemp = (TextView) findViewById(R.id.textViewTemp);

        new getWeather().execute(" ");
    }


    class getWeather extends AsyncTask<String, Double, Double> {
        private Exception exception;
        protected Double doInBackground(String... strings){
            try{
                String cityId = "2172729";
                String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
                String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&APPID=" + key;
                
                //InputStream is = new URL(url).openStream();
                URL openWeather = new URL(url);
                URLConnection yc = openWeather.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                String str = new String();
                while ((inputLine = in.readLine()) != null)
                {
                    str = str.concat(inputLine);
                    //System.out.println(inputLine);
                }
                Double s = parse(str);
                in.close();

                return s;
            }catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            getInfoActivity.this.textViewCityName.setText("2172729");
            getInfoActivity.this.textViewTemp.setText(Double.toString(aDouble));
        }
        public Double parse(String inputLine) throws JSONException {
            JSONObject obj = new JSONObject(inputLine);

            Double temp = obj.getJSONObject("main").getDouble("temp");
            return temp;
        }
    }
}
