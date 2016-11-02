package ca.dylansheng.weatherapp.cityInfo;

import android.content.Context;
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
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.lang.*;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.activity.MainActivity;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;


/**
 * Created by sheng on 2016/11/1.
 */

public class cityAdapter extends ArrayAdapter<cityInfo> implements View.OnClickListener {
    private int resourceId;
    private Context context;
    private SwipeLayout swipeLayout;
    private TextView textViewMainActivity_cityName;
    private TextView textViewMainActivity_temperature;
    private TextView getTextViewMainActivity_time;
    private RelativeLayout relativeLayout;
    private LinearLayout bottom_wrapper;

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
        textViewMainActivity_cityName = (TextView) view.findViewById(R.id.textViewMainActivity_cityName);
        textViewMainActivity_temperature = (TextView) view.findViewById(R.id.textViewMainActivity_temperature);
        getTextViewMainActivity_time = (TextView) view.findViewById(R.id.textViewMainActivity_time);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(this);
        bottom_wrapper = (LinearLayout) view.findViewById(R.id.bottom_wrapper);
        bottom_wrapper.setTag(position);
        bottom_wrapper.setOnClickListener(this);

        dbHelper = new MyDatabaseHelper(context, "weatherDB.db", null, 1);
        db = dbHelper.getWritableDatabase();

        textViewMainActivity_cityName.setText(city.cityName);
        Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
        relativeLayout.setBackground(image);

        Calendar c = Calendar.getInstance();
        TimeZone timeZone = c.getTimeZone();
        int dst = timeZone.getDSTSavings() / 1000;
        Long offset = new Long(timeZone.getRawOffset() / 1000) + dst;
        textViewMainActivity_temperature.setText(city.temperature.toString() + "°");

        c.setTimeInMillis((c.getTimeInMillis() / 1000 + city.timezone - offset + city.daylight) * 1000);
        timeStamp = new SimpleDateFormat("HH:mm").format(c.getTime());
        getTextViewMainActivity_time.setText(timeStamp);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_wrapper:
                int position = (Integer)v.getTag();
                cityInfo city = getItem(position);
                dbHelper.removeCityInfoByName(db, city.cityName);
                remove(city);
                //Toast.makeText(context, city.cityName + "wrapper", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout:
                //Toast.makeText(context, city.cityName + "relative", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
