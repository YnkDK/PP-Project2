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
    private GPSListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waypoint_collector);

        gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
        locationListener.setFilter(new ImpenetrableFilter());

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                locationListener);

        gpsCoordinates.setText("Getting first waypoint...");

    }

    public void collect(View view) {
        gpsCoordinates.setText("Getting waypoint...");
        this.locationListener.setFilter(new ImpenetrableFilter());
    }
}
