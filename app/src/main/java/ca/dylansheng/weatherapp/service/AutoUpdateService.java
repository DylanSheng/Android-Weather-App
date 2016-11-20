package ca.dylansheng.weatherapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;
import ca.dylansheng.weatherapp.web.getInfoFromWeb;

/**
 * Created by sheng on 2016/11/14.
 */

public class AutoUpdateService extends Service{
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final ArrayList<String> cityNameList = intent.getStringArrayListExtra("cityNamekey");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    updateWeather(cityNameList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000; // 这是8小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(ArrayList<String> cityNameList) throws Exception {
        for(String cityName : cityNameList) {
            getInfoFromWeb getInfoFromWeb = new getInfoFromWeb(cityName);
            getInfoFromWeb.getOpenWeather();
            getInfoFromWeb.getOpenWeatherForecast();
            cityInfo city = getInfoFromWeb.city;

            dbHelper = new MyDatabaseHelper(this, "weatherDB.db", null, 1);
            db = dbHelper.getWritableDatabase();
            dbHelper.updateDatebaseValue(db, city);
        }
    }


}
