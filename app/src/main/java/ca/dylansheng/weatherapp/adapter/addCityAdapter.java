package ca.dylansheng.weatherapp.adapter;

import android.content.Context;
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

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

/**
 * Created by sheng on 2016/11/21.
 */

public class addCityAdapter extends ArrayAdapter<String> implements View.OnClickListener {
    private int resourceId;
    private Context context;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private TextView addCityActivityListViewTextView;

    public addCityAdapter(Context context, int textViewResourceId, List<String> countryList) {
        super(context, textViewResourceId, countryList);
        this.context = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        dbHelper = new MyDatabaseHelper(context, ".db", null, 1);
        db = dbHelper.getWritableDatabase();

        addCityActivityListViewTextView = (TextView) view.findViewById(R.id.addCityActivityListViewTextView);
        addCityActivityListViewTextView.setTag(position);
        addCityActivityListViewTextView.setOnClickListener(this);
        addCityActivityListViewTextView.setText(info);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
