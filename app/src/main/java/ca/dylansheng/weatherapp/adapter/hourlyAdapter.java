package ca.dylansheng.weatherapp.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfoOpenWeatherForecast;

/**
 * Created by sheng on 2016/11/13.
 */


public class hourlyAdapter extends RecyclerView.Adapter<hourlyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<cityInfoOpenWeatherForecast> cityInfoHourlyInfoArrayList;
    private String timeStamp;

    public hourlyAdapter(Context context, ArrayList<cityInfoOpenWeatherForecast> cityInfoHourlyInfoArrayList) {
        this.context = context;
        this.cityInfoHourlyInfoArrayList = cityInfoHourlyInfoArrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public hourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_info_activity_recycleview, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        Double temp = cityInfoHourlyInfoArrayList.get(position).temperature - 273.15;
        Integer temp2 = temp.intValue();
        viewHolder.getInfoActivityRecycleLayoutTextViewTemp.setText(temp2.toString() + "°");


        String uri = "drawable/" + "c" + cityInfoHourlyInfoArrayList.get(position).icon;
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        viewHolder.getInfoActivityRecycleLayoutImageViewIcon.setImageDrawable(context.getResources().getDrawable(imageResource));

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((c.getTimeInMillis() / 1000 + cityInfoHourlyInfoArrayList.get(position).dt) * 1000);
        timeStamp = new SimpleDateFormat("HH").format(c.getTime());
        viewHolder.getInfoActivityRecycleLayoutTextViewTimeSlot.setText(timeStamp + ":00");
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView getInfoActivityRecycleLayoutTextViewTimeSlot;
        public ImageView getInfoActivityRecycleLayoutImageViewIcon;
        public TextView getInfoActivityRecycleLayoutTextViewTemp;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            getInfoActivityRecycleLayoutTextViewTimeSlot = (TextView) itemLayoutView.findViewById(R.id.getInfoActivityRecycleLayoutTextViewTimeSlot);
            getInfoActivityRecycleLayoutImageViewIcon = (ImageView) itemLayoutView.findViewById(R.id.getInfoActivityRecycleLayoutImageViewIcon);
            getInfoActivityRecycleLayoutTextViewTemp = (TextView) itemLayoutView.findViewById(R.id.getInfoActivityRecycleLayoutTextViewTemp);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cityInfoHourlyInfoArrayList.size();
    }
}