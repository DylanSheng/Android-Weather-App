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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.activity.getInfoActivity;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

/**
 * Created by sheng on 2016/11/12.
 */

public class forecastAdapter extends ArrayAdapter<cityInfoOpenWeatherForecast>{
    private int resourceId;
    private Context context;

    private RelativeLayout relativeLayout;
    private TextView getInfoActivityListViewTimeSlot;
    private TextView getInfoActivityListViewTempMin;
    private TextView getInfoActivityListViewTempMax;
    private ImageView getInfoActivityListViewImageView;
    private String timeStamp;

    public forecastAdapter(Context context, int textViewResourceId, ArrayList<cityInfoOpenWeatherForecast> cityInfoOpenWeatherForecastList) {
        super(context, textViewResourceId, cityInfoOpenWeatherForecastList);
        this.context = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cityInfoOpenWeatherForecast cityInfoOpenWeatherForecast = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.getInfoActivityListViewAdapter);

        getInfoActivityListViewTimeSlot = (TextView) view.findViewById(R.id.getInfoActivityListViewTimeSlot);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((c.getTimeInMillis() / 1000 + cityInfoOpenWeatherForecast.dt) * 1000);
        timeStamp = new SimpleDateFormat("EEEE  HH:mm").format(c.getTime());
        getInfoActivityListViewTimeSlot.setText(timeStamp);

        getInfoActivityListViewImageView = (ImageView) view.findViewById(R.id.getInfoActivityListViewImageView);

        String uri = "drawable/" + "c" + cityInfoOpenWeatherForecast.icon;
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        getInfoActivityListViewImageView.setImageDrawable(context.getResources().getDrawable(imageResource));

        getInfoActivityListViewTempMin = (TextView) view.findViewById(R.id.getInfoActivityListViewTempMin);
        getInfoActivityListViewTempMin.setText(cityInfoOpenWeatherForecast.temperatureMin + "°");
        getInfoActivityListViewTempMax = (TextView) view.findViewById(R.id.getInfoActivityListViewTempMax);
        getInfoActivityListViewTempMax.setText(cityInfoOpenWeatherForecast.temperatureMax + "°");

        return view;
    }
}
