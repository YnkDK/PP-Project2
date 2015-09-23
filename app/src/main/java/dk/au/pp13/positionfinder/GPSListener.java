package dk.au.pp13.positionfinder;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by CasperC on 9/23/15.
 */
public class GPSListener implements LocationListener {
    private TextView textView;

    public GPSListener(TextView view) {
        this.textView = view;
    }

    @Override
    public void onLocationChanged(Location location) {
        textView.setText(location.toString());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
