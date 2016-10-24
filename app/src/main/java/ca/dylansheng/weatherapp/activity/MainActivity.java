package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.defaultHeight;
import static android.R.attr.onClick;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener{
    private ImageButton imageButtonMainActivity_1;
    private TextView textViewMainActivity_1_cityName;
    private TextView textViewMainActivity_1_temperature;

    private ImageButton imageButtonMainActivity_2;
    private TextView textViewMainActivity_2_cityName;
    private TextView textViewMainActivity_2_temperature;
    private RelativeLayout relativeLayout_2;

    private ImageButton imageButtonMainActivity_3;
    private TextView textViewMainActivity_3_cityName;
    private TextView textViewMainActivity_3_temperature;
    private RelativeLayout relativeLayout_3;

    private ImageButton imageButtonMainActivity_4;
    private TextView textViewMainActivity_4_cityName;
    private TextView textViewMainActivity_4_temperature;
    private RelativeLayout relativeLayout_4;

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

        imageButtonMainActivity_1 = (ImageButton) findViewById(R.id.imageButtonMainActivity_1) ;
        imageButtonMainActivity_1.setOnTouchListener(this);
        textViewMainActivity_1_cityName = (TextView) findViewById(R.id.textViewMainActivity_1_cityName);
        textViewMainActivity_1_temperature = (TextView) findViewById(R.id.textViewMainActivity_1_temperature);

        imageButtonMainActivity_2 = (ImageButton) findViewById(R.id.imageButtonMainActivity_2) ;
        imageButtonMainActivity_2.setOnTouchListener(this);
        textViewMainActivity_2_cityName = (TextView) findViewById(R.id.textViewMainActivity_2_cityName);
        textViewMainActivity_2_temperature = (TextView) findViewById(R.id.textViewMainActivity_2_temperature);
        relativeLayout_2 = (RelativeLayout) findViewById(R.id.relativeLayout_2);

        imageButtonMainActivity_3 = (ImageButton) findViewById(R.id.imageButtonMainActivity_3) ;
        imageButtonMainActivity_3.setOnTouchListener(this);
        textViewMainActivity_3_cityName = (TextView) findViewById(R.id.textViewMainActivity_3_cityName);
        textViewMainActivity_3_temperature = (TextView) findViewById(R.id.textViewMainActivity_3_temperature);
        relativeLayout_3 = (RelativeLayout) findViewById(R.id.relativeLayout_3);

        imageButtonMainActivity_4 = (ImageButton) findViewById(R.id.imageButtonMainActivity_4) ;
        imageButtonMainActivity_4.setOnTouchListener(this);
        textViewMainActivity_4_cityName = (TextView) findViewById(R.id.textViewMainActivity_4_cityName);
        textViewMainActivity_4_temperature = (TextView) findViewById(R.id.textViewMainActivity_4_temperature);
        relativeLayout_4 = (RelativeLayout) findViewById(R.id.relativeLayout_4);


        buttonAddCity = (Button) findViewById(R.id.buttonAddCity);
        buttonAddCity.setOnClickListener(this);
        //intent to getInfoActivity
        readFromDatabaseInit();
        //Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
        //startActivity(intent);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.imageButtonMainActivity_1:
                if (gestureDetector.onTouchEvent(event)) {
                    Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);
                    intent.putExtra("cityNameKey", cityName_1);
                    startActivity(intent);
                } else {
                    // your code for move and drag
                }
                break;
            case R.id.imageButtonMainActivity_2:
                if (gestureDetector.onTouchEvent(event)) {
                    Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);
                    intent.putExtra("cityNameKey", cityName_2);
                    startActivity(intent);
                } else {
                    // your code for move and drag
                }
                break;
            case R.id.imageButtonMainActivity_3:
                if (gestureDetector.onTouchEvent(event)) {
                    Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);
                    intent.putExtra("cityNameKey", cityName_3);
                    startActivity(intent);
                } else {
                    // your code for move and drag
                }
                break;
            case R.id.imageButtonMainActivity_4:
                if (gestureDetector.onTouchEvent(event)) {
                    Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);
                    intent.putExtra("cityNameKey", cityName_4);
                    startActivity(intent);
                } else {
                    // your code for move and drag
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddCity:
                Intent intent = new Intent(MainActivity.this, changeCity.class);
                startActivity(intent);
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

    private void readFromDatabaseInit(){
        cityInfo city;
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this,"weatherDB.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(Integer i = 1; i <= 4 ;++i) {
            city = dbHelper.readCityInfoByIndex(db, i);
            if(city == null) break;
            else{
                switch (i){
                    case 1:
                        textViewMainActivity_1_cityName.setText(city.cityName);
                        cityName_1 = city.cityName;
                        textViewMainActivity_1_temperature.setText(city.temperature.toString());
                        Drawable image1 = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        imageButtonMainActivity_1.setBackground(image1);
                        break;
                    case 2:
                        textViewMainActivity_2_cityName.setText(city.cityName);
                        cityName_2 = city.cityName;
                        textViewMainActivity_2_temperature.setText(city.temperature.toString());
                        Drawable image2 = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        imageButtonMainActivity_2.setBackground(image2);
                        relativeLayout_2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        textViewMainActivity_3_cityName.setText(city.cityName);
                        cityName_3 = city.cityName;
                        textViewMainActivity_3_temperature.setText(city.temperature.toString());
                        Drawable image3 = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        imageButtonMainActivity_3.setBackground(image3);
                        relativeLayout_3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        textViewMainActivity_4_cityName.setText(city.cityName);
                        cityName_4 = city.cityName;
                        textViewMainActivity_4_temperature.setText(city.temperature.toString());
                        Drawable image4 = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));
                        imageButtonMainActivity_4.setBackground(image4);
                        relativeLayout_4.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }







    }
}