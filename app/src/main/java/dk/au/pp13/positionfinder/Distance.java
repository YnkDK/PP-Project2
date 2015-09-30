package dk.au.pp13.positionfinder;

import android.location.Location;

/**
 * The distance function
 */
public interface Distance {

    /**
     * Calculates the distance between l1 and l2 using latitude and longitudee
     *
     * @param l1 First location
     * @param l2 Second location
     * @return The distance between the two locations
     */
    double distance(Location l1, Location l2);

    /**
     * @return The name of the distance measure
     */
    String name();
}
