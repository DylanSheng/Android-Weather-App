package ca.dylansheng.weatherapp;
/**
 * Created by sheng on 2016/10/9.
 */

import android.os.AsyncTask;

import org.json.*;
import java.lang.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class getWeather extends AsyncTask<String, Void, Double>{

    private Exception exception;

    protected Double doInBackground(String... cityId) {
        try {
                String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
                String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&APPID=" + key;

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
                onProgressUpdate(s);



                return s;
        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        textView.setText(Double.toString(values));
        super.onProgressUpdate(values);
    }

    public Double parse(String inputLine){
        JSONObject obj = new JSONObject(inputLine);

        Double temp = obj.getJSONObject("main").getDouble("temp");
        return temp;
    }


}


