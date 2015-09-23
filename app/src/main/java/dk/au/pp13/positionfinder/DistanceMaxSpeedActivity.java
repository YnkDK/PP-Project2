package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class DistanceMaxSpeedActivity extends ActionBarActivity {
    private TextView maxSpeed;
    private TextView gpsCoordinates;
    private EditText inputFieldEdittext;
    private LocationManager locationManager;
    private GPSListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_max_speed);

        this.maxSpeed = (TextView) findViewById(R.id.maxSpeed);
        this.inputFieldEdittext = (EditText) findViewById(R.id.inputFieldTimeInSec);
        this.gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);

        // Get the location service from the current context
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
    }

    public void stopGPS(View view) {
        if(locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }

    }

    /**
     * Extend the client program to implement distance-based reporting strategy that requests as few GPS
     * fixes as possible from the built-in GPS by assuming that the phone can only move with a configurable
     * maximum speed.
     *
     * @param view ignored
     */
    public void setMaxSpeed(View view) {
        // TODO: Should be configurable
        // 10 meters in km
        final double DISTANCE = 10.0 / 1000;

        stopGPS(null);
        gpsCoordinates.setText("");

        if (inputFieldEdittext.getText().length() > 0) {
            // Speed in km/h
            long speed = Long.parseLong(String.valueOf(inputFieldEdittext.getText()));

            double hours = DISTANCE / speed;
            long milliseconds = (long) (hours * 3600000);

            maxSpeed.setText("Current max speed: " + speed + " km / t");
            inputFieldEdittext.setText("");
            gpsCoordinates.setText("Waiting for GPS signal...");


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, milliseconds, 0,
                    locationListener);

        }
    }
}
