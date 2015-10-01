package dk.au.pp13.positionfinder.filters;

import android.location.Location;

/**
 * Given a distance and a maximum speed, locations can only pass the filter if it was possible
 * to move the given distance
 */
public class MaxSpeedFilter implements Filter {

    private final TimeFilter timeFilter;
    private final HaversineFilter distanceFilter;


    /**
     * @param distance          Distance in meters
     * @param speedInMeterPrSec Speed in m/s
     */
    public MaxSpeedFilter(double distance, double speedInMeterPrSec) {
        long seconds = (long) (distance / speedInMeterPrSec);
        timeFilter = new TimeFilter(seconds);
        distanceFilter = new HaversineFilter(distance);
    }

    @Override
    public boolean passesFilter(Location l1, Location l2) {
        return timeFilter.passesFilter(l1, l2) && distanceFilter.passesFilter(l1, l2);
    }
}
