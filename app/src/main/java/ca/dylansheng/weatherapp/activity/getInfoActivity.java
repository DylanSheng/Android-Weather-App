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

public class getInfoActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView textViewCityName;
    public TextView textViewTemp;
    public Button buttonChangeCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info_activity);

        textViewCityName = (TextView) findViewById(R.id.textViewCityName);
        textViewTemp = (TextView) findViewById(R.id.textViewTemp);
        buttonChangeCity = (Button) findViewById(R.id.buttonChangeCity);

        new getWeather().execute(" ");

        buttonChangeCity.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonChangeCity:
                Intent intent = new Intent(getInfoActivity.this, changeCity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class getWeather extends AsyncTask<String, Double, Double> {
        private Exception exception;
        private String cityName = "Edmonton";
        protected Double doInBackground(String... strings){
            try{
                //String cityId = "2172729";
                String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
                //String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&APPID=" + key;

                String url_Name = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&APPID=" + key;
                //InputStream is = new URL(url).openStream();
                URL openWeather = new URL(url_Name);
                URLConnection yc = openWeather.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                String str = new String();
                while ((inputLine = in.readLine()) != null)
                {
                    str = str.concat(inputLine);
                    //System.out.println(inputLine);
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
            getInfoActivity.this.textViewCityName.setText(cityName);
            Integer intTemp = temp.intValue();
            getInfoActivity.this.textViewTemp.setText(Integer.toString(intTemp));
        }
        public Double parse(String inputLine) throws JSONException {
            JSONObject obj = new JSONObject(inputLine);

            Double temp = obj.getJSONObject("main").getDouble("temp");

            return temp - 273.15;
        }

    }
}
