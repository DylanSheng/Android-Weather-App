package ca.dylansheng.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public Button button;
    public EditText editText;
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.inputCityId);

        button = (Button) findViewById(R.id.buttonCityId);
        button.setOnClickListener((View.OnClickListener) this);

        textView = (TextView) findViewById(R.id.viewCityId);
    }

     @Override
     public void onClick(View v) {

         switch (v.getId()) {
             case R.id.buttonCityId:


                 AsyncTask as = new getWeather().execute(inputText);


                 //Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
                 break;
             default:
                 break;
         }
     }
}
