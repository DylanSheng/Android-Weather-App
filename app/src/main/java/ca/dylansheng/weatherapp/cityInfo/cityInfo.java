package ca.dylansheng.weatherapp.cityInfo;

import java.io.Serializable;

/**
 * Created by sheng on 2016/10/19.
 */

public class cityInfo implements Serializable{
    public String cityName;
    public Double latitude;
    public Double longitude;
    public Integer temperature;
    public byte[] cityImage;
}
