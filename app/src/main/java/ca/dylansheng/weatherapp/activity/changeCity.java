package ca.dylansheng.weatherapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import java.util.List;

import ca.dylansheng.weatherapp.R;

public class changeCity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonChooseCity;
    private EditText editTextChooseCity;
    private Button buttonGetGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        buttonChooseCity = (Button) findViewById(R.id.buttonChooseCity);
        buttonChooseCity.setOnClickListener(this);
        editTextChooseCity = (EditText) findViewById(R.id.editTextChooseCity);
        buttonGetGPS = (Button) findViewById(R.id.buttonGetGPS);
        buttonGetGPS.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonChooseCity:
                String cityName = editTextChooseCity.getText().toString();
                Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);

                intent.putExtra("cityNameKey", cityName);
                startActivity(intent);
                break;
            case R.id.buttonGetGPS:
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //List<String> providerList = locationManager.getProviders(true);

                String provider = LocationManager.PASSIVE_PROVIDER;

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                Double lat = location.getLatitude();
                Double lon = location.getLongitude();
                editTextChooseCity.setText("lat: " + Double.toString(lat) + "....lon: " + Double.toString(lon));
                break;
            default:
                break;
        }
    }
}
