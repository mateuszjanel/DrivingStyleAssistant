package com.example.drivingstyleassistant.presentation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivingstyleassistant.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class SensorPreviewFragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager;
    Sensor accelerationSensor;
    Sensor gyroSensor;
    float currentSpeed;
    double currentLatitude;
    double currentLongitude;
    float accelerometerX;
    float accelerometerY;
    float accelerometerZ;
    float currentBearing;

    TextView locSpeed;
    TextView locLongitude;
    TextView locLatitude;
    TextView accX;
    TextView accY;
    TextView accZ;
    TextView locBearing;

    private OnFragmentInteractionListener mListener;

    public SensorPreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor_preview, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerationSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        locSpeed = (TextView) getActivity().findViewById(R.id.locationSpeedValue);
        locLatitude = (TextView) getActivity().findViewById(R.id.locationLatitudeValue);
        locLongitude = (TextView) getActivity().findViewById(R.id.locationLongitudeValue);
        locBearing = (TextView) getActivity().findViewById(R.id.locationBearingValue);

        accX = getActivity().findViewById(R.id.accelerometerXValue);
        accY = getActivity().findViewById(R.id.accelerometerYValue);
        accZ = getActivity().findViewById(R.id.accelerometerZValue);

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);
        final String locationProvider = LocationManager.GPS_PROVIDER;

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.gps_check_message), Toast.LENGTH_SHORT).show();
        }

        locSpeed.setText("0");

        //accelerometer
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                //Request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }
        } else {

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    currentSpeed = location.getSpeed();
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    currentBearing = location.getBearing();



                    locSpeed.setText(String.valueOf(currentSpeed));
                    locLatitude.setText(String.valueOf(currentLatitude));
                    locLongitude.setText(String.valueOf(currentLongitude));
                    locBearing.setText(String.valueOf(currentBearing));

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    /*if(status == LocationProvider.TEMPORARILY_UNAVAILABLE)
                    {
                        locationManager.removeUpdates(this);
                    }
                    else {
                        locationManager.requestLocationUpdates(locationProvider, 0, 0, this);
                    } //removing updates when gps signal is poor in order to prevent from false speed*/
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        accelerometerX = event.values[0];
        accelerometerY = event.values[1];
        accelerometerZ = event.values[2];



        accX.setText(String.valueOf(accelerometerX));
        accY.setText(String.valueOf(accelerometerY));
        accZ.setText(String.valueOf(accelerometerZ));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
