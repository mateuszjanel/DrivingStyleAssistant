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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivingstyleassistant.R;
import com.example.drivingstyleassistant.data.AccelerationsGrade;
import com.example.drivingstyleassistant.data.CorneringGrade;
import com.example.drivingstyleassistant.data.SmoothnessFinalGrade;
import com.example.drivingstyleassistant.data.SmoothnessFragmentaryGrade;
import com.example.drivingstyleassistant.domain.helpers.EventHelper;
import com.example.drivingstyleassistant.domain.helpers.RouteHelper;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class RouteFragment extends Fragment implements SensorEventListener {

    LocationManager locationManager;
    LocationListener locationListener;

    long currentRouteId;

    SensorManager sensorManager;
    Sensor accelerationSensor;
    RouteHelper routeHelper;

    float currentSpeed;
    int accTransgression;
    int corneringTransgression;
    double maxAccelerationInEvent, maxCorneringAccInEvent;
    SensorEvent maxAccelerationSensorEvent, maxCorneringSensorEvent;
    float maxAccelerationSpeed, maxCorneringSpeed;
    boolean isAccelerationPositive, isCorneringPositive;

    boolean drivingStarted, smoothnessGradeSaved;
    SmoothnessFragmentaryGrade smoothnessFragmentaryGrade;
    SmoothnessFinalGrade smoothnessFinalGrade;
    long lastShotTakenTime;
    long lastEventTime;
    long startEventTimeAcc, startEventTimeCornering;


    TextView zPlus, zMinus, xPlus, xMinus, speed;
    ImageView zPlusBackground, zMinusBackground, xPlusBackground, xMinusBackground;
    Button finishRouteButton;


    private OnFragmentInteractionListener mListener;

    public RouteFragment() {
        // Required empty public constructor
    }

    public static RouteFragment newInstance(String param1, String param2) {
        RouteFragment fragment = new RouteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        lastEventTime = System.currentTimeMillis();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    @Override
    public void onPause() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sensorManager.unregisterListener(this);
        if(smoothnessGradeSaved == false) {
            if (smoothnessFinalGrade.fragmentaryGrades.size() > 0) {
                float finalGrade = smoothnessFinalGrade.grade();
                routeHelper.setSmoothnessGrade(finalGrade, currentRouteId);
            } else {
                routeHelper.setSmoothnessGrade(5.0f, currentRouteId);
            }
        }
        locationManager.removeUpdates(locationListener);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerationSensor,SensorManager.SENSOR_DELAY_GAME);
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
        }
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        routeHelper = new RouteHelper();
        currentRouteId = routeHelper.startRoute();
        drivingStarted = false;
        smoothnessGradeSaved = false;
        accTransgression = 0;
        corneringTransgression = 0;
        isAccelerationPositive = true;
        isCorneringPositive = true;
        smoothnessFinalGrade = new SmoothnessFinalGrade();

        zPlus = getActivity().findViewById(R.id.zPlusTextView);
        zMinus = getActivity().findViewById(R.id.zMinusTextView);
        xPlus = getActivity().findViewById(R.id.xPlusTextView);
        xMinus = getActivity().findViewById(R.id.xMinusTextView);
        speed = getActivity().findViewById(R.id.speedTextView);
        zPlusBackground = getActivity().findViewById(R.id.zPlusBackground);
        zMinusBackground = getActivity().findViewById(R.id.zMinusBackground);
        xPlusBackground = getActivity().findViewById(R.id.xPlusBackground);
        xMinusBackground = getActivity().findViewById(R.id.xMinusBackground);
        finishRouteButton = getActivity().findViewById(R.id.finishRouteButton);

        //accelerometer
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        locationManager = (LocationManager) getActivity().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);
        final String locationProvider = LocationManager.GPS_PROVIDER;

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.gps_check_message), Toast.LENGTH_SHORT).show();
        }

        finishRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smoothnessGradeSaved == false) {
                    if (smoothnessFinalGrade.fragmentaryGrades.size() > 0) {
                        float finalGrade = smoothnessFinalGrade.grade();
                        routeHelper.setSmoothnessGrade(finalGrade, currentRouteId);
                    } else {
                        routeHelper.setSmoothnessGrade(5.0f, currentRouteId);
                    }
                }
                smoothnessGradeSaved = true;
                Toast.makeText(getActivity(), getActivity().getString(R.string.all_saved),Toast.LENGTH_SHORT).show();

                Fragment fragment = new MainFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

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
        }
        else {

            locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    currentSpeed = location.getSpeed();
                    int speedKMPH =(int)(currentSpeed*3.6);
                    speed.setText(String.valueOf(speedKMPH));
                    if(currentSpeed > 2.8 && drivingStarted == false){
                        smoothnessFragmentaryGrade = new SmoothnessFragmentaryGrade();
                        drivingStarted = true;
                        smoothnessGradeSaved = false;
                        lastShotTakenTime = System.currentTimeMillis();
                    }
                    else if(currentSpeed > 0 && drivingStarted == true){
                        long deltaTime = System.currentTimeMillis() - lastShotTakenTime;
                        double deltaTimeInSec = deltaTime / 1000.0;
                        if((deltaTime / 1000.0) > 1.5){
                            smoothnessFragmentaryGrade.speeds.add((double)currentSpeed);
                            lastShotTakenTime = System.currentTimeMillis();
                            if(smoothnessFragmentaryGrade.xValsList.isEmpty()){
                                smoothnessFragmentaryGrade.xValsList.add((double)deltaTimeInSec);
                            }
                            else {
                                smoothnessFragmentaryGrade.xValsList
                                        .add(smoothnessFragmentaryGrade.xValsList
                                                .get(smoothnessFragmentaryGrade.xValsList.size()-1) + deltaTimeInSec);
                            }
                        }
                    }
                    else if(currentSpeed == 0 && drivingStarted == true){
                        smoothnessFinalGrade.fragmentaryGrades.add(smoothnessFragmentaryGrade.grade());
                        drivingStarted = false;
                    }

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    /*if(status == LocationProvider.TEMPORARILY_UNAVAILABLE)
                    {
                        locationManager.removeUpdates(this);
                    }
                    else {
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
                        }
                        else {
                        locationManager.requestLocationUpdates(locationProvider, 0, 0, this);}
                    } //removing updates when gps signal is poor in order to prevent from false speed*/
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
    }

    private void setAccelerationsTextViews (float accX, float accZ){

        double accXG = Math.round((Math.abs(accX) / 9.81)*100.0)/100.0;
        double accZG = Math.round((Math.abs(accZ) / 9.81)*100.0)/100.0;

        if (accXG <= 0.4){
            xPlusBackground.setImageResource(R.color.positiveGrade);
            xMinusBackground.setImageResource(R.color.positiveGrade);
        }
        else if(accXG > 0.4 && accXG <= 0.8){
            xPlusBackground.setImageResource(R.color.averageGrade);
            xMinusBackground.setImageResource(R.color.averageGrade);
        }
        else if(accXG > 0.8){
            xPlusBackground.setImageResource(R.color.negativeGrade);
            xMinusBackground.setImageResource(R.color.negativeGrade);
        }


        if (accZG <= 0.4){
            zPlusBackground.setImageResource(R.color.positiveGrade);
            zMinusBackground.setImageResource(R.color.positiveGrade);
        }
        else if(accZG > 0.4 && accZG <= 0.8){
            zPlusBackground.setImageResource(R.color.averageGrade);
            zMinusBackground.setImageResource(R.color.averageGrade);
        }
        else if(accZG > 0.8){
            zPlusBackground.setImageResource(R.color.negativeGrade);
            zMinusBackground.setImageResource(R.color.negativeGrade);
        }

        if(accX > 0){
            xPlus.setText(String.valueOf(accXG));
            xMinus.setText("0");
        }
        else {
            xPlus.setText("0");
            xMinus.setText(String.valueOf(accXG));
        }

        if(accZ > 0){
            zPlus.setText(String.valueOf(accZG));
            zMinus.setText("0");
        }
        else {
            zPlus.setText("0");
            zMinus.setText(String.valueOf(accZG * -1));
        }

        if(accX == 0 && accZ == 0){
            xPlus.setText("0");
            xMinus.setText("0");
            zPlus.setText("0");
            zMinus.setText("0");

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        EventHelper eventHelper = new EventHelper();

        float accelerometerX = event.values[0];
        float accelerometerY = event.values[1];
        float accelerometerZ = event.values[2];

        setAccelerationsTextViews(accelerometerX, accelerometerZ);

        if(eventHelper.checkIfTransgression(accelerometerZ)){
            if(accTransgression == 0){
                startEventTimeAcc = System.currentTimeMillis();
            }
            accTransgression = 1;
            double accelerationInG = Math.abs(accelerometerZ / 9.81);
            if(accelerometerZ < 0) {
                isAccelerationPositive = false;
            }
            if(Math.abs(accelerationInG) > maxAccelerationInEvent){
                maxAccelerationInEvent = accelerationInG;
                maxAccelerationSensorEvent = event;
                maxAccelerationSpeed = currentSpeed;
                maxAccelerationSpeed = (float) (Math.round(maxAccelerationSpeed*10.0)/10.0);
            }
        }
        else if(!eventHelper.checkIfTransgression(accelerometerZ) && accTransgression == 1){
            if(isAccelerationPositive == false){
                maxAccelerationInEvent = maxAccelerationInEvent * (-1);
            }
            double eventLenght = (System.currentTimeMillis() - startEventTimeAcc) / 1000.0;
            if(eventLenght > 1) {
                AccelerationsGrade accelerationsGrade = new AccelerationsGrade();
                accelerationsGrade.grade(maxAccelerationSensorEvent, currentRouteId, maxAccelerationSpeed, (float) maxAccelerationInEvent);
            }
            accTransgression = 0;
            maxAccelerationInEvent = 0;
            isAccelerationPositive = true;
            lastEventTime = System.currentTimeMillis();
        }

        if(eventHelper.checkIfTransgression(accelerometerX)){
            if(corneringTransgression == 0){
                startEventTimeCornering = System.currentTimeMillis();
            }
            corneringTransgression = 1;
            double accelerationInG = Math.abs(accelerometerX / 9.81);
            if(accelerometerX < 0){
                isCorneringPositive = false;
            }
            if(Math.abs(accelerationInG) > maxAccelerationInEvent){
                maxCorneringAccInEvent = accelerationInG;
                maxCorneringSensorEvent = event;
                maxCorneringSpeed = currentSpeed;
                maxCorneringSpeed = (float) (Math.round(maxCorneringSpeed*10.0)/10.0);
            }
        }

        else if(!eventHelper.checkIfTransgression(accelerometerX) && corneringTransgression == 1){
            if(isCorneringPositive == false){
                maxCorneringAccInEvent = maxAccelerationInEvent * (-1);
            }
            double eventLenght = (System.currentTimeMillis() - startEventTimeCornering) / 1000.0;
            if(eventLenght > 1) {
                CorneringGrade corneringGrade = new CorneringGrade();
                corneringGrade.grade(maxCorneringSensorEvent, currentRouteId, maxCorneringSpeed, (float) maxAccelerationInEvent);
            }
            corneringTransgression = 0;
            maxCorneringAccInEvent = 0;
            isCorneringPositive = true;
            lastEventTime = System.currentTimeMillis();
        }

        double elapsedTime = (System.currentTimeMillis() - lastEventTime) / 1000.0;

        if(drivingStarted == true && !eventHelper.checkIfTransgression(accelerometerZ) && !eventHelper.checkIfTransgression(accelerometerX) && elapsedTime > 180) {
            CorneringGrade corneringGrade = new CorneringGrade();
            AccelerationsGrade accelerationsGrade = new AccelerationsGrade();
            corneringGrade.gradeOnly(0.2f, currentRouteId);
            accelerationsGrade.gradeOnly(0.2f, currentRouteId);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
