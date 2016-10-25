package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
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

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

public class MainActivity extends Activity implements View.OnClickListener{
    private SwipeLayout swipeLayout_1;
    private TextView textViewMainActivity_1_cityName;
    private TextView textViewMainActivity_1_temperature;
    private RelativeLayout relativeLayout_1_1;

    private SwipeLayout swipeLayout_2;
    private TextView textViewMainActivity_2_cityName;
    private TextView textViewMainActivity_2_temperature;
    private RelativeLayout relativeLayout_2;
    private RelativeLayout relativeLayout_2_1;

    private SwipeLayout swipeLayout_3;
    private TextView textViewMainActivity_3_cityName;
    private TextView textViewMainActivity_3_temperature;
    private RelativeLayout relativeLayout_3;
    private RelativeLayout relativeLayout_3_1;

    private SwipeLayout swipeLayout_4;
    private TextView textViewMainActivity_4_cityName;
    private TextView textViewMainActivity_4_temperature;
    private RelativeLayout relativeLayout_4;
    private RelativeLayout relativeLayout_4_1;

    private Button buttonAddCity;

    private GestureDetector gestureDetector;

    private String cityName_1;
    private String cityName_2;
    private String cityName_3;
    private String cityName_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        swipeLayout_1 = (SwipeLayout) findViewById(R.id.swipeLayout_1);
        swipeLayout_1.setOnClickListener(this);
        textViewMainActivity_1_cityName = (TextView) findViewById(R.id.textViewMainActivity_1_cityName);
        textViewMainActivity_1_temperature = (TextView) findViewById(R.id.textViewMainActivity_1_temperature);
        relativeLayout_1_1 = (RelativeLayout) findViewById(R.id.relativeLayout_1_1);

        swipeLayout_2 = (SwipeLayout) findViewById(R.id.swipeLayout_2);
        swipeLayout_2.setOnClickListener(this);
        textViewMainActivity_2_cityName = (TextView) findViewById(R.id.textViewMainActivity_2_cityName);
        textViewMainActivity_2_temperature = (TextView) findViewById(R.id.textViewMainActivity_2_temperature);
        relativeLayout_2 = (RelativeLayout) findViewById(R.id.relativeLayout_2);
        relativeLayout_2_1 = (RelativeLayout) findViewById(R.id.relativeLayout_2_1);

        swipeLayout_3 = (SwipeLayout) findViewById(R.id.swipeLayout_3);
        swipeLayout_3.setOnClickListener(this);
        textViewMainActivity_3_cityName = (TextView) findViewById(R.id.textViewMainActivity_3_cityName);
        textViewMainActivity_3_temperature = (TextView) findViewById(R.id.textViewMainActivity_3_temperature);
        relativeLayout_3 = (RelativeLayout) findViewById(R.id.relativeLayout_3);
        relativeLayout_3_1 = (RelativeLayout) findViewById(R.id.relativeLayout_3_1);

        swipeLayout_4 = (SwipeLayout) findViewById(R.id.swipeLayout_4);
        swipeLayout_4.setOnClickListener(this);
        textViewMainActivity_4_cityName = (TextView) findViewById(R.id.textViewMainActivity_4_cityName);
        textViewMainActivity_4_temperature = (TextView) findViewById(R.id.textViewMainActivity_4_temperature);
        relativeLayout_4 = (RelativeLayout) findViewById(R.id.relativeLayout_4);
        relativeLayout_4_1 = (RelativeLayout) findViewById(R.id.relativeLayout_4_1);







        buttonAddCity = (Button) findViewById(R.id.buttonAddCity);
        buttonAddCity.setOnClickListener(this);
        //intent to getInfoActivity
        readFromDatabaseInit();
        //Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
        //startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);
        switch (v.getId()){
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
            case R.id.buttonAddCity:
                Intent intent2 = new Intent(MainActivity.this, addCity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    private void readFromDatabaseInit() {
        cityInfo city;
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this, "weatherDB.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Integer i = 1; i <= 4; ++i) {
            city = dbHelper.readCityInfoByIndex(db, i);
            if (city == null) break;
            else {
                switch (i) {
                    case 1:
                        textViewMainActivity_1_cityName.setText(city.cityName);
                        cityName_1 = city.cityName;
                        textViewMainActivity_1_temperature.setText(city.temperature.toString());
                        Drawable image1 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_1_1.setBackground(image1);
                        break;
                    case 2:
                        textViewMainActivity_2_cityName.setText(city.cityName);
                        cityName_2 = city.cityName;
                        textViewMainActivity_2_temperature.setText(city.temperature.toString());
                        Drawable image2 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_2_1.setBackground(image2);
                        relativeLayout_2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        textViewMainActivity_3_cityName.setText(city.cityName);
                        cityName_3 = city.cityName;
                        textViewMainActivity_3_temperature.setText(city.temperature.toString());
                        Drawable image3 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_3_1.setBackground(image3);
                        relativeLayout_3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        textViewMainActivity_4_cityName.setText(city.cityName);
                        cityName_4 = city.cityName;
                        textViewMainActivity_4_temperature.setText(city.temperature.toString());
                        Drawable image4 = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        relativeLayout_4_1.setBackground(image4);
                        relativeLayout_4.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public void swipeLayoutFunction(){
        SwipeLayout swipeLayout =  (SwipeLayout)findViewById(R.id.swipeLayout_1);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper_1));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
    }
}