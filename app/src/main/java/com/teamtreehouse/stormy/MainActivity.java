package com.teamtreehouse.stormy;


        import android.Manifest;
        import android.content.Context;
        import android.content.IntentSender;
        import android.content.pm.PackageManager;
        import android.location.LocationManager;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Looper;
        import android.text.method.LinkMovementMethod;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.databinding.DataBindingUtil;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.android.gms.common.api.ApiException;
        import com.google.android.gms.common.api.ResolvableApiException;
        import com.google.android.gms.location.LocationCallback;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationResult;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.location.LocationSettingsRequest;
        import com.google.android.gms.location.LocationSettingsResponse;
        import com.google.android.gms.location.LocationSettingsStatusCodes;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.teamtreehouse.stormy.databinding.ActivityMainBinding;
        import org.jetbrains.annotations.NotNull;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // define postcode search box, button, location request and attributionLabel attribution message
    EditText enterUkPostcode;
    Button locateMeButton;
    LocationRequest locationRequest;
    TextView attributionLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binds the main activity to the UI
        //activity_main is the name of our UI layout file
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,
                R.layout.activity_main);

        // links postcode and button variables to UI elements in xml file (UI)
        enterUkPostcode = findViewById(R.id.enterUkPostcode);
        locateMeButton = findViewById(R.id.locateMeButton);

        //links attributionLabel variable to attribution_message in xml file (UI)
        attributionLabel = findViewById(R.id.attribution_message);
        // makes the attributionLabel links clickable
        attributionLabel.setMovementMethod(LinkMovementMethod.getInstance());


//creating request for phone accessing location - would like to work on further
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        // Create click listener for 'Locate Me' button
        locateMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                   if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                       if (isGPSEnabled()) {

                           LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                   .requestLocationUpdates(locationRequest, new LocationCallback() {
                                       @Override
                                       public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                                           super.onLocationResult(locationResult);

                                           LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                                   .removeLocationUpdates(this);

                                           if(locationResult.getLocations().size() >0){
                                               int index = locationResult.getLocations().size() -1;
                                               double latitude = locationResult.getLocations().get(index).getLatitude();
                                               double longitude = locationResult.getLocations().get(index).getLongitude();

                                               LatLongModel latLongModel = new LatLongModel();
                                               latLongModel.setLongitude(longitude);
                                               latLongModel.setLatitude(latitude);
                                               Log.i(TAG, String.valueOf(latitude));
                                               Log.i(TAG, String.valueOf(latLongModel.getLongitude()));

                                           }
                                       }
                                   }, Looper.getMainLooper());


                       } else {
                           turnOnGPS();

                       }
                       Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();

                   }else {
                       requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                   }
                }


                String ApiKey = BuildConfig.ApiKey;

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                String oSAPIkey = "";

                String locationURL = "https://api.os.uk/search/names/v1/find?query=" + enterUkPostcode.getText().toString() + "&key=" + oSAPIkey;


                //get the JSON object

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, locationURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //get the selected items from the JSON data using the WeatherReportModel

                        try {

                            //create a new instance of Weather Report Model

                            LocationModel locationModel = new LocationModel();

                            // response = the retrieved JSON data
                            // define a JSON object within response, and save it to a variable (the object here is called "main")

                            JSONArray results = response.getJSONArray("results");
                            JSONObject resultsObject = results.getJSONObject(0);
                            JSONObject gazetteerEntry = resultsObject.getJSONObject("GAZETTEER_ENTRY");
                            locationModel.setPopulatedPlace(gazetteerEntry.getString("POPULATED_PLACE"));
                            Log.i(TAG, String.valueOf(locationModel.getPopulatedPlace()));
                            locationModel.setCountyUnitary(gazetteerEntry.getString("COUNTY_UNITARY"));
                            Log.i(TAG, String.valueOf(locationModel.getCountyUnitary()));
                            locationModel.setRegion(gazetteerEntry.getString("REGION"));
                            Log.i(TAG, String.valueOf(locationModel.getRegion()));
                            locationModel.setCountry(gazetteerEntry.getString("COUNTRY"));
                            Log.i(TAG, String.valueOf(locationModel.getCountry()));

                            // binds the weather data to the UI
                            binding.setLocation(locationModel);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });


// Add the request to the RequestQueue.
                queue.add(request);

            }
        });
    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        }
    }
}


