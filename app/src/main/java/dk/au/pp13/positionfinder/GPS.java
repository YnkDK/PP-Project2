package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by mys on 9/23/15.
 */
public class GPS implements LocationListener {
    private final Context context;
    private Location lastKnownLocation;

    protected LocationManager locationManager;

    public GPS(Context context) {
        this.context = context;
        this.lastKnownLocation = requestLocationUpdates();
    }

    private Location requestLocationUpdates() {
        try {
            // Get the location service from the current context
            locationManager = (LocationManager) this.context
                    .getSystemService(Context.LOCATION_SERVICE);
            // Ensure that GPS is enabled
            boolean isGPSEnabled = this.locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                // Request the location
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    // Get last known location
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        return location;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("GPS ERROR", e.getMessage());
        }
        return null;
    }

    public Location getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        lastKnownLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
