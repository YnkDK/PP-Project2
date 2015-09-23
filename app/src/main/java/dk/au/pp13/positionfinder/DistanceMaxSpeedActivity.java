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

    public void setMaxSpeed(View view) {
        stopGPS(null);
        gpsCoordinates.setText("");

        if (inputFieldEdittext.getText().length() > 0) {
            // TODO: Do something
        }
    }
}
