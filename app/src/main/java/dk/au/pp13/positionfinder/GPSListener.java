package dk.au.pp13.positionfinder;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

import dk.au.pp13.positionfinder.filters.Filter;

/**
 * Created by CasperC on 9/23/15.
 */
public class GPSListener implements LocationListener {
    private Filter filter;

    private TextView textView;
    private Location lastGoodFix;
    private HTTPFix logger;

    public GPSListener(TextView view) {
        this.textView = view;
        this.lastGoodFix = null;
    }

    public void setFilter(Filter filter) {
        // Filter changed, thus we do not have any good fixes
        this.lastGoodFix = null;
        this.filter = filter;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (filter == null || logger == null) {
            textView.setText("Found GPS - waiting on user");
        } else if (lastGoodFix == null || filter.passesFilter(lastGoodFix, location)) {
            textView.setText(location.toString());
            lastGoodFix = location;

            logger.log(location);
        }
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

    public void setLogger(HTTPFix logger) {
        this.logger = logger;
    }
}
