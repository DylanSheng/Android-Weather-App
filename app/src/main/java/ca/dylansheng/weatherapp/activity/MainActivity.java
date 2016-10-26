package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

public class MainActivity extends Activity implements View.OnClickListener {
    /* four swipe layouts in main activity */
    private SwipeLayout swipeLayout_1;
    private TextView textViewMainActivity_1_cityName;
    private TextView textViewMainActivity_1_temperature;
    private RelativeLayout relativeLayout_1_1;
    private LinearLayout bottom_wrapper_1;

    private SwipeLayout swipeLayout_2;
    private TextView textViewMainActivity_2_cityName;
    private TextView textViewMainActivity_2_temperature;
    private RelativeLayout relativeLayout_2;
    private RelativeLayout relativeLayout_2_1;
    private LinearLayout bottom_wrapper_2;

    private SwipeLayout swipeLayout_3;
    private TextView textViewMainActivity_3_cityName;
    private TextView textViewMainActivity_3_temperature;
    private RelativeLayout relativeLayout_3;
    private RelativeLayout relativeLayout_3_1;
    private LinearLayout bottom_wrapper_3;

    private SwipeLayout swipeLayout_4;
    private TextView textViewMainActivity_4_cityName;
    private TextView textViewMainActivity_4_temperature;
    private RelativeLayout relativeLayout_4;
    private RelativeLayout relativeLayout_4_1;
    private LinearLayout bottom_wrapper_4;

    /* an add city button in main activity */
    private Button buttonAddCity;

    //private GestureDetector gestureDetector;

    private String cityName_1;
    private String cityName_2;
    private String cityName_3;
    private String cityName_4;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        /* init swipe layout on create */
        swipeLayout_1 = (SwipeLayout) findViewById(R.id.swipeLayout_1);
        swipeLayout_1.setOnClickListener(this);
        textViewMainActivity_1_cityName = (TextView) findViewById(R.id.textViewMainActivity_1_cityName);
        textViewMainActivity_1_temperature = (TextView) findViewById(R.id.textViewMainActivity_1_temperature);
        relativeLayout_1_1 = (RelativeLayout) findViewById(R.id.relativeLayout_1_1);
        bottom_wrapper_1 = (LinearLayout) findViewById(R.id.bottom_wrapper_1);
        bottom_wrapper_1.setOnClickListener(this);

        swipeLayout_2 = (SwipeLayout) findViewById(R.id.swipeLayout_2);
        swipeLayout_2.setOnClickListener(this);
        textViewMainActivity_2_cityName = (TextView) findViewById(R.id.textViewMainActivity_2_cityName);
        textViewMainActivity_2_temperature = (TextView) findViewById(R.id.textViewMainActivity_2_temperature);
        relativeLayout_2 = (RelativeLayout) findViewById(R.id.relativeLayout_2);
        relativeLayout_2_1 = (RelativeLayout) findViewById(R.id.relativeLayout_2_1);
        bottom_wrapper_2 = (LinearLayout) findViewById(R.id.bottom_wrapper_2);
        bottom_wrapper_2.setOnClickListener(this);

        swipeLayout_3 = (SwipeLayout) findViewById(R.id.swipeLayout_3);
        swipeLayout_3.setOnClickListener(this);
        textViewMainActivity_3_cityName = (TextView) findViewById(R.id.textViewMainActivity_3_cityName);
        textViewMainActivity_3_temperature = (TextView) findViewById(R.id.textViewMainActivity_3_temperature);
        relativeLayout_3 = (RelativeLayout) findViewById(R.id.relativeLayout_3);
        relativeLayout_3_1 = (RelativeLayout) findViewById(R.id.relativeLayout_3_1);
        bottom_wrapper_3 = (LinearLayout) findViewById(R.id.bottom_wrapper_3);
        bottom_wrapper_3.setOnClickListener(this);

        swipeLayout_4 = (SwipeLayout) findViewById(R.id.swipeLayout_4);
        swipeLayout_4.setOnClickListener(this);
        textViewMainActivity_4_cityName = (TextView) findViewById(R.id.textViewMainActivity_4_cityName);
        textViewMainActivity_4_temperature = (TextView) findViewById(R.id.textViewMainActivity_4_temperature);
        relativeLayout_4 = (RelativeLayout) findViewById(R.id.relativeLayout_4);
        relativeLayout_4_1 = (RelativeLayout) findViewById(R.id.relativeLayout_4_1);
        bottom_wrapper_4 = (LinearLayout) findViewById(R.id.bottom_wrapper_4);
        bottom_wrapper_4.setOnClickListener(this);

        /* init add city button */
        buttonAddCity = (Button) findViewById(R.id.buttonAddCity);
        buttonAddCity.setOnClickListener(this);

        /* init database "weatherDB.db", version 1 */
        dbHelper = new MyDatabaseHelper(MainActivity.this, "weatherDB.db", null, 1);
        db = dbHelper.getWritableDatabase();

        /* read data from db, init four swipe layout parameters */
        readFromDatabaseInit();
        //Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
        //startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);
        switch (v.getId()) {
            /* click the layout then jump to the getInfoActivity */
            case R.id.relativeLayout_1_1:
                intent.putExtra("cityNameKey", cityName_1);
                startActivity(intent);
                break;
            case R.id.relativeLayout_2_1:
                intent.putExtra("cityNameKey", cityName_2);
                startActivity(intent);
                break;
            case R.id.relativeLayout_3_1:
                intent.putExtra("cityNameKey", cityName_3);
                startActivity(intent);
                break;
            case R.id.relativeLayout_4_1:
                intent.putExtra("cityNameKey", cityName_4);
                startActivity(intent);
                break;
            /* click to delete the swipe layout and the data from the db */
            case R.id.bottom_wrapper_1:
                dbHelper.removeCityInfoByName(db, cityName_1);
                finish();
                startActivity(getIntent());
                break;
            case R.id.bottom_wrapper_2:
                dbHelper.removeCityInfoByName(db, cityName_2);
                finish();
                startActivity(getIntent());
                break;
            case R.id.bottom_wrapper_3:
                dbHelper.removeCityInfoByName(db, cityName_1);
                finish();
                startActivity(getIntent());
                break;
            case R.id.bottom_wrapper_4:
                dbHelper.removeCityInfoByName(db, cityName_1);
                finish();
                startActivity(getIntent());
                break;
            /* click to add city info */
            case R.id.buttonAddCity:
                Intent intent2 = new Intent(MainActivity.this, addCity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

//    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onSingleTapUp(MotionEvent event) {
//            return true;
//        }
//    }

    private void readFromDatabaseInit() {
        cityInfo city;
        /* query the rowid list from db */
        String Query = "Select ROWID from " + "info";
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();
        /* store the rowid in arraylist */
        ArrayList<Integer> rowIDList = new ArrayList<>();

        if(cursor != null && cursor .getCount() > 0) {
            do {
                rowIDList.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        /* iterator from 1 to maximum 4, continuously update swipe layout */
        for (Integer i = 1; i <= 4 && i <= rowIDList.size(); ++i) {
            /* update city class, notice that rowIDList index ranges in(0, 3) */
            city = dbHelper.readCityInfoByIndex(db, rowIDList.get(i - 1));
            /* continue when no city info */
            if (city == null) continue;
            else {
                /* switch for 4 cases, keep updating the swipe layout */
                switch (i) {
                    case 1:
                        textViewMainActivity_1_cityName.setText(city.cityName);
                        cityName_1 = city.cityName;
                        textViewMainActivity_1_temperature.setText(city.temperature.toString()+ "°");
                        /* update background bitmap figure */
                        Drawable image1 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_1_1.setBackground(image1);
                        /* keep addCity button visible until i = 4 */
                        buttonAddCity.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        textViewMainActivity_2_cityName.setText(city.cityName);
                        cityName_2 = city.cityName;
                        textViewMainActivity_2_temperature.setText(city.temperature.toString()+ "°");
                        Drawable image2 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_2_1.setBackground(image2);
                        relativeLayout_2.setVisibility(View.VISIBLE);
                        buttonAddCity.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        textViewMainActivity_3_cityName.setText(city.cityName);
                        cityName_3 = city.cityName;
                        textViewMainActivity_3_temperature.setText(city.temperature.toString()+ "°");
                        Drawable image3 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_3_1.setBackground(image3);
                        relativeLayout_3.setVisibility(View.VISIBLE);
                        buttonAddCity.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        textViewMainActivity_4_cityName.setText(city.cityName);
                        cityName_4 = city.cityName;
                        textViewMainActivity_4_temperature.setText(city.temperature.toString()+ "°");
                        Drawable image4 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_4_1.setBackground(image4);
                        relativeLayout_4.setVisibility(View.VISIBLE);
                        buttonAddCity.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }

//    public void swipeLayoutFunction() {
//        SwipeLayout swipeLayout = (SwipeLayout) findViewById(R.id.swipeLayout_1);
//
////set show mode.
//        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//
////add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper_1));
//
//        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onClose(SwipeLayout layout) {
//                //when the SurfaceView totally cover the BottomView.
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//                //you are swiping.
//            }
//
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//                //when the BottomView totally show.
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//                //when user's hand released.
//            }
//        });
//    }

}