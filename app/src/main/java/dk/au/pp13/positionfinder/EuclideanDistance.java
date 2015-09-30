package dk.au.pp13.positionfinder;

import android.location.Location;

/**
 * Created by mys on 9/30/15.
 */
public class EuclideanDistance implements Distance {
    @Override
    public double distance(Location l1, Location l2) {
        final double latDiff = l2.getLatitude() - l1.getLatitude();
        final double lonDiff = l2.getLongitude() - l1.getLongitude();

        return Math.sqrt((latDiff * latDiff) + (lonDiff * lonDiff));
    }

    @Override
    public String name() {
        return "Euclidean Distance";
    }
}
