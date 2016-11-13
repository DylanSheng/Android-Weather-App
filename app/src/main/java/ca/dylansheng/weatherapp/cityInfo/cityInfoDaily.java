package ca.dylansheng.weatherapp.cityInfo;

import java.util.ArrayList;

/**
 * Created by sheng on 2016/11/13.
 */

public class cityInfoDaily {
    ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastArrayList;
    public cityInfoDaily(ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastArrayList){
        this.cityInfoOpenWeatherForecastArrayList = cityInfoOpenWeatherForecastArrayList;
    }

    public ArrayList<cityInfoOpenWeatherForecast> getCityInfoDailyInfoArrayList(){
        ArrayList<cityInfoOpenWeatherForecast> cityInfoDailyInfoArrayList = new ArrayList<>();
        for(int day = 0; day < 5; ++day){
            Double temperatureMin = Double.parseDouble(cityInfoOpenWeatherForecastArrayList.get(day * 8).temperatureMin);
            Double temperatureMax = Double.parseDouble(cityInfoOpenWeatherForecastArrayList.get(day * 8).temperatureMax);
            for(int slot = 0; slot < 8; ++slot){
                int currPos = day * 8 + slot;
                if(Double.parseDouble(cityInfoOpenWeatherForecastArrayList.get(currPos).temperatureMin) < temperatureMin){
                    temperatureMin = Double.parseDouble(cityInfoOpenWeatherForecastArrayList.get(currPos).temperatureMin);
                }
                if(Double.parseDouble(cityInfoOpenWeatherForecastArrayList.get(currPos).temperatureMax) > temperatureMax){
                    temperatureMax = Double.parseDouble(cityInfoOpenWeatherForecastArrayList.get(currPos).temperatureMax);
                }
            }
            cityInfoOpenWeatherForecast cityInfoOpenWeatherForecast = cityInfoOpenWeatherForecastArrayList.get(day * 8);
            cityInfoOpenWeatherForecast.temperatureMin = temperatureMin.toString();
            cityInfoOpenWeatherForecast.temperatureMax = temperatureMax.toString();
            cityInfoDailyInfoArrayList.add(cityInfoOpenWeatherForecast);
        }
        return cityInfoDailyInfoArrayList;
    }
}
