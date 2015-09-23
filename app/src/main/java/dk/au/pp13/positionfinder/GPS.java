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

    protected LocationManager locationManager;

    public GPS(Context context) {
        this.context = context;
    }

    public GPSData requestLocationUpdates() {
        GPSData result = null;
        do {
            try {
                locationManager = (LocationManager) this.context
                        .getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = this.locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (isGPSEnabled) {
                    Log.d("GPS", "GPS is enabled");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    if (locationManager != null) {
                        Location location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d("GPS", "Location: " + location);
                        if (location != null) {
                            result = new GPSData(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude(),
                                    location.getTime()
                            );
                        }
                    }
                }
            } catch (Exception e) {
                result = null;
                Log.e("GPS ERROR", e.getMessage());
            }
        } while (result == null);
        return result;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("GPS", location.getLatitude() + "");
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


    public class GPSData {
        public final double latitude;
        public final double longitude;
        public final double altitude;
        public final long timestamp;

        private GPSData(double latitude, double longitude, double altitude, long timestamp) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
            this.timestamp = timestamp;
        }

        public String toString() {
            return this.longitude + "," + this.latitude + "," + this.altitude + "," + this.timestamp;
        }
    }
}
