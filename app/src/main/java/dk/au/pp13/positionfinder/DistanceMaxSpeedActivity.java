package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dk.au.pp13.positionfinder.filters.MaxSpeedFilter;


public class DistanceMaxSpeedActivity extends ActionBarActivity {
    private TextView maxSpeed;
    private TextView gpsCoordinates;
    private EditText inputFieldEdittext;
    private GPSListener locationListener;
    private TextView maxDistance;
    private EditText editFieldDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_max_speed);

        this.maxSpeed = (TextView) findViewById(R.id.maxSpeed);
        this.inputFieldEdittext = (EditText) findViewById(R.id.inputFieldTimeInSec);
        this.gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);

        maxDistance = (TextView) findViewById(R.id.maxDistance);
        editFieldDistance = (EditText) findViewById(R.id.inputFieldDistance);

        // Get the location service from the current context
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    /**
     * Extend the client program to implement distance-based reporting strategy that requests as few GPS
     * fixes as possible from the built-in GPS by assuming that the phone can only move with a configurable
     * maximum speed.
     *
     * @param view ignored
     */
    public void setMaxSpeed(View view) {

        if (inputFieldEdittext.getText().length() > 0 && editFieldDistance.getText().length() > 0) {
            // Speed in km/h
            long speed = Long.parseLong(String.valueOf(inputFieldEdittext.getText()));
            long distance = Long.parseLong(String.valueOf(editFieldDistance.getText()));


            inputFieldEdittext.setText("");
            editFieldDistance.setText("");

            maxSpeed.setText("Current max speed: " + speed + " km / t");
            maxDistance.setText("Current distance threshold: " + distance + " meters");

            this.locationListener.setFilter(new MaxSpeedFilter(distance, speed));
            gpsCoordinates.setText("Waiting for GPS signal...");


        }
    }
}
