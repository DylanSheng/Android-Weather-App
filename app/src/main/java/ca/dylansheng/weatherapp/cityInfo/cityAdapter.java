package ca.dylansheng.weatherapp.cityInfo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.daimajia.swipe.SwipeLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.lang.*;
import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.activity.getInfoActivity;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;


/**
 * Created by sheng on 2016/11/1.
 */

public class cityAdapter extends ArrayAdapter<cityInfo> implements View.OnClickListener {
    private int resourceId;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView mainActivitySwiperLayoutTextViewCityName;
    private TextView mainActivitySwiperLayoutTextViewTemperature;
    private TextView mainActivitySwiperLayoutTextViewTime;
    private RelativeLayout mainActivitySwiperLayoutRelativeLayout;
    private LinearLayout mainActivitySwiperLayoutBottomWrapper;

    private String timeStamp;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public cityAdapter(Context context, int textViewResourceId, List<cityInfo> cityInfoList) {
        super(context, textViewResourceId, cityInfoList);
        this.context = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cityInfo city = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnClickListener(this);
        mainActivitySwiperLayoutTextViewCityName = (TextView) view.findViewById(R.id.mainActivitySwiperLayoutTextViewCityName);
        mainActivitySwiperLayoutTextViewTemperature = (TextView) view.findViewById(R.id.mainActivitySwiperLayoutTextViewTemperature);
        mainActivitySwiperLayoutTextViewTime = (TextView) view.findViewById(R.id.mainActivitySwiperLayoutTextViewTime);
        mainActivitySwiperLayoutRelativeLayout = (RelativeLayout) view.findViewById(R.id.mainActivitySwiperLayoutRelativeLayout);
        mainActivitySwiperLayoutRelativeLayout.setTag(position);
        mainActivitySwiperLayoutRelativeLayout.setOnClickListener(this);
        mainActivitySwiperLayoutBottomWrapper = (LinearLayout) view.findViewById(R.id.mainActivitySwiperLayoutBottomWrapper);
        mainActivitySwiperLayoutBottomWrapper.setTag(position);
        mainActivitySwiperLayoutBottomWrapper.setOnClickListener(this);

        dbHelper = new MyDatabaseHelper(context, "weatherDB.db", null, 1);
        db = dbHelper.getWritableDatabase();

        mainActivitySwiperLayoutTextViewCityName.setText(city.cityName);
        Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(city.cityInfoGoogleImage.cityImage, 0, city.cityInfoGoogleImage.cityImage.length));
        mainActivitySwiperLayoutRelativeLayout.setBackground(image);

        Calendar c = Calendar.getInstance();
        TimeZone timeZone = c.getTimeZone();
        int dst = timeZone.getDSTSavings() / 1000;
        Long offset = new Long(timeZone.getRawOffset() / 1000) + dst;
        mainActivitySwiperLayoutTextViewTemperature.setText(city.cityInfoOpenWeather.temperature.toString() + "°");

        c.setTimeInMillis((c.getTimeInMillis() / 1000 + city.cityInfoTimezone.timezone - offset + city.cityInfoTimezone.daylight) * 1000);
        timeStamp = new SimpleDateFormat("HH:mm").format(c.getTime());
        mainActivitySwiperLayoutTextViewTime.setText(timeStamp);
        return view;
    }

    @Override
    public void onClick(View v) {
        int position;
        cityInfo city;
        switch (v.getId()) {
            case R.id.mainActivitySwiperLayoutBottomWrapper:
                position = (Integer)v.getTag();
                city = getItem(position);
                dbHelper.removeCityInfoByName(db, city.cityName);
                remove(city);
                //Toast.makeText(context, city.cityName + "wrapper", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mainActivitySwiperLayoutRelativeLayout:
                position = (Integer)v.getTag();
                city = getItem(position);
                Intent intent = new Intent(context,  getInfoActivity.class);
                intent.putExtra("cityNameKey", city.cityName);
                context.startActivity(intent);
                //Toast.makeText(context, city.cityName + "relative", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
