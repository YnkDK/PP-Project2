package dk.au.pp13.positionfinder.filters;

import android.location.Location;

/**
 * Either a distance of time filter
 */
public interface Filter {

    /**
     * Returns true if the change from l1 to l2 would make l2 pass the filter
     *
     * @param l1 First location
     * @param l2 Second location
     * @return True if l2 passes the filter, false otherwise
     */
    boolean passesFilter(Location l1, Location l2);
}
