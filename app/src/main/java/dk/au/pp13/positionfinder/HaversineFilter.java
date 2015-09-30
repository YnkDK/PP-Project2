package dk.au.pp13.positionfinder;

import android.location.Location;


public class HaversineFilter implements Filter {

    private final double threshold;

    /**
     * @param threshold The distance in meters, in which two locations should have moved
     */
    HaversineFilter(double threshold) {
        this.threshold = threshold;
    }

    /**
     * Use the Haversine formula to estimate the distance
     * between the l1 and l2
     * Source: http://andrew.hedges.name/experiments/haversine/
     *
     * @param l1 First location
     * @param l2 Second location
     * @return The Haversine distance
     */
    private double distance(Location l1, Location l2) {
        // Mean radius of earth (http://en.wikipedia.org/wiki/Earth_radius)
        final double r = 6373000;
        // Convert to radians
        final double lon1 = Math.toRadians(l1.getLongitude()), lat1 = Math.toRadians(l1.getLatitude());
        final double lon2 = Math.toRadians(l2.getLongitude()), lat2 = Math.toRadians(l2.getLatitude());
        final double dlat = lat2 - lat1;
        final double dlon = lon2 - lon1;

        final double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // great circle distance in radians

        return c * r;
    }

    @Override
    public boolean passesFilter(Location l1, Location l2) {
        return distance(l1, l2) > threshold;
    }
}
