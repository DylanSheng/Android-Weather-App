package ca.dylansheng.weatherapp.cityInfo;

import java.io.Serializable;

/**
 * Created by sheng on 2016/10/19.
 */

public class cityInfo implements Serializable{
    public cityInfo(){
        cityInfoOpenWeather cityInfoOpenWeather = new cityInfoOpenWeather();
        cityInfoGoogleImage cityInfoGoogleImage = new cityInfoGoogleImage();
        cityInfoTimezone cityInfoTimezone = new cityInfoTimezone();
        this.cityInfoOpenWeather = cityInfoOpenWeather;
        this.cityInfoGoogleImage = cityInfoGoogleImage;
        this.cityInfoTimezone = cityInfoTimezone;
    }
    public String cityName;
    public cityInfoOpenWeather cityInfoOpenWeather;
    public cityInfoTimezone cityInfoTimezone;
    public cityInfoGoogleImage cityInfoGoogleImage;
}
