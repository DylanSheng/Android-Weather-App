package ca.dylansheng.weatherapp;
/**
 * Created by sheng on 2016/10/9.
 */

import org.json.*;
import java.lang.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class getWeather{
    String cityId;
    getWeather(String cityId){
        this.cityId = "2172729";
    }

    public Double readJSON() throws Exception{
        String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
        String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&APPID=" + key;

        //URL oracle = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20%3D%202487889&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
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
    }

    public Double parse(String inputLine) throws Exception{
        JSONObject obj = new JSONObject(inputLine);

        Double temp = obj.getJSONObject("main").getDouble("temp");
        return temp;
    }



}


