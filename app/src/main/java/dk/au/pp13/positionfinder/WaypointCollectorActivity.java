package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import dk.au.pp13.positionfinder.filters.ImpenetrableFilter;

/**
 * Created by mys on 9/30/15.
 */
public class WaypointCollectorActivity extends ActionBarActivity {
    private TextView gpsCoordinates;
    private LocationManager locationManager;
    private GPSListener locationListener;
    private boolean isGPSOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waypoint_collector);

        gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);

        gpsCoordinates.setText("GPS not started");
        isGPSOn = false;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
        locationListener.setFilter(new ImpenetrableFilter());
    }

    public void collect(View view) {
        gpsCoordinates.setText("Getting waypoint...");
        this.locationListener.setFilter(new ImpenetrableFilter());
    }

    public void toggleGPS(View view) {
        if (isGPSOn && locationListener != null) {
            locationManager.removeUpdates(locationListener);
            gpsCoordinates.setText("GPS not started");
        } else {
            gpsCoordinates.setText("Waiting for GPS signal...");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListener);
        }
        isGPSOn = !isGPSOn;
    }
}
