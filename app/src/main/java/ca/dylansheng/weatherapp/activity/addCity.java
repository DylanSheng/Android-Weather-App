package ca.dylansheng.weatherapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.adapter.addCityAdapter;
import ca.dylansheng.weatherapp.adapter.cityAdapter;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;
import ca.dylansheng.weatherapp.db.worldCityListDB;

public class addCity extends Activity implements View.OnClickListener {
    private Button changeCityActivityButtonChooseCity;
    private EditText changeCityActivityEditTextChooseCity;
    private Button changeCityActivityButtonGetGPS;
    private ListView addCityActivityListView;
    //private Button buttonParseLocation;
    private String cityName;
    private Double longitude;
    private Double latitude;

    private worldCityListDB worldCityListDB;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        changeCityActivityButtonChooseCity = (Button) findViewById(R.id.changeCityActivityButtonChooseCity);
        changeCityActivityButtonChooseCity.setOnClickListener(this);
        changeCityActivityEditTextChooseCity = (EditText) findViewById(R.id.changeCityActivityEditTextChooseCity);
        changeCityActivityButtonGetGPS = (Button) findViewById(R.id.changeCityActivityButtonGetGPS);
        changeCityActivityButtonGetGPS.setOnClickListener(this);

        addCityActivityListView = (ListView) findViewById(R.id.addCityActivityListView);
        //buttonParseLocation = (Button) findViewById(R.id.buttonParseLocation);
        //buttonParseLocation.setOnClickListener(this);
        worldCityListDB = new worldCityListDB(addCity.this, "weather.db", null, 1);
        db = worldCityListDB.getWritableDatabase();


        ArrayList<String> countryList = worldCityListDB.getCountryList(db);
        addCityAdapter adapter = new addCityAdapter(addCity.this, R.layout.add_city_activity_listview, countryList);
        ListView addCityActivityListView = (ListView) findViewById(R.id.mainActivityListView);
        addCityActivityListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeCityActivityButtonChooseCity:
                cityName = changeCityActivityEditTextChooseCity.getText().toString();
                Intent intent = new Intent(getApplicationContext(), getInfoActivity.class);

                intent.putExtra("cityNameKey", cityName);
                startActivity(intent);
                break;
            case R.id.changeCityActivityButtonGetGPS:
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
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                //editTextChooseCity.setText("lat: " + Double.toString(latI) + "....lon: " + Double.toString(lonI));

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.ENGLISH);

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    //String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    cityName = addresses.get(0).getLocality();
                    //String state = addresses.get(0).getAdminArea();
                    //String country = addresses.get(0).getCountryName();
                    //String postalCode = addresses.get(0).getPostalCode();
                    //String knownName = addresses.get(0).getFeatureName();
                    changeCityActivityEditTextChooseCity.setText(cityName);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            default:
                break;
        }
    }
}
