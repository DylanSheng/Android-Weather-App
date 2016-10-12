package ca.dylansheng.weatherapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.dylansheng.weatherapp.R;

public class changeCity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonChooseCity;
    private EditText editTextChooseCity;
    private Button buttonGetGPS;
    //private Button buttonParseLocation;
    private Double lon;
    private Double lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        buttonChooseCity = (Button) findViewById(R.id.buttonChooseCity);
        buttonChooseCity.setOnClickListener(this);
        editTextChooseCity = (EditText) findViewById(R.id.editTextChooseCity);
        buttonGetGPS = (Button) findViewById(R.id.buttonGetGPS);
        buttonGetGPS.setOnClickListener(this);

        //buttonParseLocation = (Button) findViewById(R.id.buttonParseLocation);
        //buttonParseLocation.setOnClickListener(this);
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

                String provider = LocationManager.NETWORK_PROVIDER;

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
                lat = location.getLatitude();
                lon = location.getLongitude();
                //editTextChooseCity.setText("lat: " + Double.toString(latI) + "....lon: " + Double.toString(lonI));

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.ENGLISH);

                try {
                    addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    editTextChooseCity.setText(city);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            default:
                break;
        }
    }
}
