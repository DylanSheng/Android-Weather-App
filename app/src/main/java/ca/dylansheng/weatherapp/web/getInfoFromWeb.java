package ca.dylansheng.weatherapp.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.cityInfo.cityInfoGoogleImage;
import ca.dylansheng.weatherapp.cityInfo.cityInfoOpenWeather;

/**
 * Created by sheng on 2016/11/4.
 */

public class getInfoFromWeb {

    private String cityName;
    public getInfoFromWeb(String cityName){
        this.cityName = cityName;
    }

    public cityInfoOpenWeather getInfoFromOpenWeather() throws IOException, JSONException{
        cityInfoOpenWeather cityInfoOpenWeather = new cityInfoOpenWeather();
        String key = "3c8b1e15683ae662889c1ed4a06ab1e6";
        String url_Name = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&APPID=" + key;
        URL openWeather = new URL(url_Name);
        URLConnection yc = openWeather.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        String str = new String();
        while ((inputLine = in.readLine()) != null)
        {
            str = str.concat(inputLine);
        }
        JSONObject obj = new JSONObject(str);

        Double temp = obj.getJSONObject("main").getDouble("temp");
        cityInfoOpenWeather.latitude = Double.parseDouble(obj.getJSONObject("coord").getString("lat"));
        cityInfoOpenWeather.longitude = Double.parseDouble(obj.getJSONObject("coord").getString("lon"));
        cityInfoOpenWeather.condition = obj.getJSONArray("weather").getJSONObject(0).getString("main");
        cityInfoOpenWeather.temperature = temp.intValue() - 273;
        in.close();
        return cityInfoOpenWeather;
    }

    public cityInfoGoogleImage getInfoFromGoogleImage(cityInfo city) throws IOException, JSONException{
        cityInfoGoogleImage cityInfoGoogleImage = new cityInfoGoogleImage();
        String googleKey = "AIzaSyBfG7eMBFRS8IfO3evj9DxTb3p35d9YYL8";
        String urlPlaceSearch = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + city.cityInfoOpenWeather.latitude + "," + city.cityInfoOpenWeather.longitude + "&key=" + googleKey + "&radius=500";
        URL openPlaceSearch = new URL(urlPlaceSearch);
        URLConnection yc = openPlaceSearch.openConnection();
        BufferedReader inPlaceSearch = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        String strPlaceSearch = new String();
        while ((inputLine = inPlaceSearch.readLine()) != null)
        {
            strPlaceSearch = strPlaceSearch.concat(inputLine);
        }

        JSONObject objPlaceSearch = new JSONObject(strPlaceSearch);
        JSONArray arrayPlaceSearch = objPlaceSearch.getJSONArray("results");
        JSONObject objArrayPlaceSearch = arrayPlaceSearch.getJSONObject(0);
        String photo_reference = objArrayPlaceSearch.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

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

        cityInfoGoogleImage.cityImage = bmp;
        return cityInfoGoogleImage;
    }

}
