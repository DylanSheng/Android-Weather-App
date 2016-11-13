package ca.dylansheng.weatherapp.cityInfo;

import java.util.ArrayList;

/**
 * Created by sheng on 2016/11/13.
 */

public class cityInfoHourly {
    ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastArrayList;

    public cityInfoHourly(ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastArrayList) {
        this.cityInfoOpenWeatherForecastArrayList = cityInfoOpenWeatherForecastArrayList;
    }

    public ArrayList<cityInfoOpenWeatherForecast> getCityInfoHourlyInfoArrayList() {
        ArrayList<cityInfoOpenWeatherForecast> cityInfoHourlyInfoArrayList = new ArrayList<>();
            for (int slot = 0; slot < 8; ++slot) {
                cityInfoOpenWeatherForecast cityInfoOpenWeatherForecast = cityInfoOpenWeatherForecastArrayList.get(slot);
                cityInfoHourlyInfoArrayList.add(cityInfoOpenWeatherForecast);
            }
        return cityInfoHourlyInfoArrayList;
    }
}

