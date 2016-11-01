package ca.dylansheng.weatherapp.cityInfo;

import android.content.Context;
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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.lang.*;
import ca.dylansheng.weatherapp.R;


/**
 * Created by sheng on 2016/11/1.
 */

public class cityAdapter extends ArrayAdapter<cityInfo> implements View.OnClickListener {
    private int resourceId;

    private SwipeLayout swipeLayout;
    private TextView textViewMainActivity_cityName;
    private TextView textViewMainActivity_temperature;
    private TextView getTextViewMainActivity_time;
    private RelativeLayout relativeLayout;
    private LinearLayout bottom_wrapper;

    public cityAdapter(Context context, int textViewResourceId, List<cityInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cityInfo city = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnClickListener(this);
        textViewMainActivity_cityName = (TextView) view.findViewById(R.id.textViewMainActivity_cityName);
        textViewMainActivity_temperature = (TextView) view.findViewById(R.id.textViewMainActivity_temperature);
        getTextViewMainActivity_time = (TextView) view.findViewById(R.id.textViewMainActivity_time);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        bottom_wrapper = (LinearLayout) view.findViewById(R.id.bottom_wrapper);
        bottom_wrapper.setOnClickListener(this);

        textViewMainActivity_cityName.setText(city.cityName);
        Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
        relativeLayout.setBackground(image);

//        Calendar c = Calendar.getInstance();
//        TimeZone timeZone = c.getTimeZone();
//        int dst = timeZone.getDSTSavings() / 1000;
//        Long offset = new Long(timeZone.getRawOffset() / 1000) + dst;
//        textViewMainActivity_1_cityName.setText(city.cityName);
//        cityName_1 = city.cityName;
//        textViewMainActivity_1_temperature.setText(city.temperature.toString()+ "°");
//                        /* update background bitmap figure */
//        Drawable image1 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
//        relativeLayout_1_1.setBackground(image1);
//        relativeLayout_1.setVisibility(View.VISIBLE);
//                        /* keep addCity button visible until i = 4 */
//        buttonAddCity.setVisibility(View.VISIBLE);
//        //Long t = c.getTimeInMillis();
//
//        c.setTimeInMillis((c.getTimeInMillis() / 1000 + city.timezone - offset + city.daylight) * 1000);
//        timeStamp = new SimpleDateFormat("HH:mm").format(c.getTime());
//        getTextViewMainActivity_1_time.setText(timeStamp);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
