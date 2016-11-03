package ca.dylansheng.weatherapp.cityInfo;

import java.io.Serializable;

/**
 * Created by sheng on 2016/10/19.
 */

/**
 * Parameters:
     coord
        coord.lon City geo location, longitude
        coord.lat City geo location, latitude
     weather (more info Weather condition codes)
        weather.id Weather condition id
        weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
        weather.description Weather condition within the group
        weather.icon Weather icon id
     base Internal parameter
     main
        main.temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         main.pressure Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
         main.humidity Humidity, %
         main.temp_min Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         main.temp_max Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         main.sea_level Atmospheric pressure on the sea level, hPa
         main.grnd_level Atmospheric pressure on the ground level, hPa
     wind
         wind.speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
         wind.deg Wind direction, degrees (meteorological)
     clouds
         clouds.all Cloudiness, %
     rain
         rain.3h Rain volume for the last 3 hours
     snow
         snow.3h Snow volume for the last 3 hours
     dt Time of data calculation, unix, UTC
     sys
         sys.type Internal parameter
         sys.id Internal parameter
         sys.message Internal parameter
         sys.country Country code (GB, JP etc.)
         sys.sunrise Sunrise time, unix, UTC
         sys.sunset Sunset time, unix, UTC
     id City ID
     name City name
     cod Internal parameter
 *
 */

public class cityInfo implements Serializable{
    public String cityName;

    public Double latitude;
    public Double longitude;

    public String condition;
    public String description;
    public String iconId;

    public Integer temperature;
    public Integer pressure;
    public Integer humidity;
    public Long temperatureMin;
    public Long temperatureMax;

    public Long windSpeed;
    public Long windDeg;

    public Integer cloudiness;

    public Long timezone;
    public Long daylight;

    public byte[] cityImage;
}
