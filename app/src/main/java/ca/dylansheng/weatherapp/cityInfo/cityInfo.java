package ca.dylansheng.weatherapp.cityInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sheng on 2016/10/19.
 */

public class cityInfo implements Serializable{
    public cityInfo(){
        cityInfoOpenWeather cityInfoOpenWeather = new cityInfoOpenWeather();
        cityInfoGoogleImage cityInfoGoogleImage = new cityInfoGoogleImage();
        cityInfoTimezone cityInfoTimezone = new cityInfoTimezone();
        ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastArrayList = new ArrayList<>();

        this.cityInfoOpenWeather = cityInfoOpenWeather;
        this.cityInfoGoogleImage = cityInfoGoogleImage;
        this.cityInfoTimezone = cityInfoTimezone;
        this.cityInfoOpenWeatherForecastArrayList = cityInfoOpenWeatherForecastArrayList;
    }
    public String cityName;
    public cityInfoOpenWeather cityInfoOpenWeather;
    public cityInfoTimezone cityInfoTimezone;
    public cityInfoGoogleImage cityInfoGoogleImage;
    public ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastArrayList;
}
