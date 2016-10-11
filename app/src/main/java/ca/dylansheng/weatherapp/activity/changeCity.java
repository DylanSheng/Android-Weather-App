package ca.dylansheng.weatherapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ca.dylansheng.weatherapp.R;

public class changeCity extends AppCompatActivity {
    private Button buttonChooseCity;
    private EditText editTextChooseCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        buttonChooseCity = (Button) findViewById(R.id.buttonChooseCity);
        editTextChooseCity = (EditText) findViewById(R.id.editTextChooseCity);
    }


}
