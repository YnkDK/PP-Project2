package dk.au.pp13.positionfinder.filters;

import android.location.Location;

/**
 * Given a distance and a maximum speed, locations can only pass the filter if it was possible
 * to move the given distance
 */
public class MaxSpeedFilter implements Filter {

    private final TimeFilter timeFilter;


    /**
     * @param distance   Distance in meters
     * @param speedInKmH Speed in km/h
     */
    public MaxSpeedFilter(double distance, double speedInKmH) {
        // TODO: Check that this is actually correct
        double hours = (distance / 1000.) / speedInKmH;
        long milliseconds = (long) (hours * 3600);
        timeFilter = new TimeFilter(milliseconds);
    }

    @Override
    public boolean passesFilter(Location l1, Location l2) {
        return timeFilter.passesFilter(l1, l2);
    }
}
