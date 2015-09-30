package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class DistanceNaiveActivity extends ActionBarActivity {
    private TextView gpsCoordinates;
    private boolean isGPSOn;
    private LocationManager locationManager = null;
    private GPSListener locationListener = null;
    private EditText editFieldDistance;
    private TextView maxDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_naive);

        gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        maxDistance = (TextView) findViewById(R.id.maxDistance);
        editFieldDistance = (EditText) findViewById(R.id.inputFieldDistance);


        gpsCoordinates.setText("GPS not started");
        isGPSOn = false;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
    }


    public void toggleGPS(View view) {
        if(isGPSOn && locationListener != null) {
            locationManager.removeUpdates(locationListener);
            gpsCoordinates.setText("GPS not started");
        } else {
            gpsCoordinates.setText("Waiting for GPS signal...");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListener);
        }
        isGPSOn = !isGPSOn;
    }

    public void setDistance(View view) {
        if (editFieldDistance.getText().length() > 0) {
            long distance = Long.parseLong(String.valueOf(editFieldDistance.getText()));
            this.locationListener.setFilter(new HaversineFilter(distance));

            maxDistance.setText("Current distance threshold: " + distance + " meters");
            editFieldDistance.setText("");
            if (!isGPSOn) toggleGPS(null);

        }
    }
}
