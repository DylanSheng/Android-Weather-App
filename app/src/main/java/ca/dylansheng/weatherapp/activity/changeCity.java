package ca.dylansheng.weatherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import ca.dylansheng.weatherapp.R;

public class changeCity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonChooseCity;
    private EditText editTextChooseCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        buttonChooseCity = (Button) findViewById(R.id.buttonChooseCity);
        buttonChooseCity.setOnClickListener(this);
        editTextChooseCity = (EditText) findViewById(R.id.editTextChooseCity);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonChooseCity:
                String cityName = editTextChooseCity.getText().toString();
                Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);

                intent.putExtra("cityNameKey",cityName);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
