package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import ca.dylansheng.weatherapp.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //intent to getInfoActivity
        Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
        startActivity(intent);
    }
}