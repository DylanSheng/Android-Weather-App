package ca.dylansheng.weatherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.dylansheng.weatherapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intent to getInfoActivity
        Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
        startActivity(intent);

    }
}