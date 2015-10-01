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
    private LocationManager locationManager;
    private HTTPFix fixer;

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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        fixer = null;

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
            // Speed in m/s
            long speed = Long.parseLong(String.valueOf(inputFieldEdittext.getText()));
            long distance = Long.parseLong(String.valueOf(editFieldDistance.getText()));
            double KM_PR_HOUR_TO_METER_PR_SECOND = 3.6;

            inputFieldEdittext.setText("");
            editFieldDistance.setText("");

            maxSpeed.setText("Current max speed: " + speed + " m/s");
            maxDistance.setText("Current distance threshold: " + distance + " meters");
            fixer = new HTTPFix("MaxSpeed-speed" + speed + "-distance" + distance);
            locationListener.setLogger(fixer);

            this.locationListener.setFilter(new MaxSpeedFilter(distance, speed*KM_PR_HOUR_TO_METER_PR_SECOND));
            gpsCoordinates.setText("Waiting for GPS signal...");
        }
    }

    public void sendWaypoint(View view) {
        if (fixer != null) {
            fixer.waypoint();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        locationManager.removeUpdates(locationListener);
    }
}
