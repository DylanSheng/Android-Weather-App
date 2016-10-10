package ca.dylansheng.weatherapp;
/**
 * Created by sheng on 2016/10/9.
 */

import org.json.*;
import java.lang.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class getWeather{
    String cityId;
    getWeather(String cityId){
        this.cityId = "2172729";
    }

    public Double readJSON() throws Exception{
        String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
        String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&APPID=" + key;
        JSONObject jsonObject = new JSONObject();
        jsonObject = readJsonFromUrl(url);


        Double s = parse(jsonObject);

        return s;
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public Double parse(JSONObject obj) throws Exception{
        Double temp = obj.getJSONObject("main").getDouble("temp");
        return temp;
    }



}


