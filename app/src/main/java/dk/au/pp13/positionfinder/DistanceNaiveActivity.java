package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dk.au.pp13.positionfinder.filters.HaversineFilter;


public class DistanceNaiveActivity extends ActionBarActivity {
    private GPSListener locationListener = null;
    private EditText editFieldDistance;
    private TextView maxDistance;
    private LocationManager locationManager;
    private HTTPFix fixer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_naive);

        TextView gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        maxDistance = (TextView) findViewById(R.id.maxDistance);
        editFieldDistance = (EditText) findViewById(R.id.inputFieldDistance);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                locationListener);
        fixer = null;
        gpsCoordinates.setText("Waiting for GPS...");

    }


    public void setDistance(View view) {
        if (editFieldDistance.getText().length() > 0) {
            long distance = Long.parseLong(String.valueOf(editFieldDistance.getText()));
            fixer = new HTTPFix("DistanceNaive-distance" + distance);
            locationListener.setLogger(fixer);

            this.locationListener.setFilter(new HaversineFilter(distance));

            maxDistance.setText("Current distance threshold: " + distance + " meters");
            editFieldDistance.setText("");
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
