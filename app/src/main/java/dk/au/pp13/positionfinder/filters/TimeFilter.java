package dk.au.pp13.positionfinder.filters;

import android.location.Location;


/**
 * A very simple time filter
 */
public class TimeFilter implements Filter {

    private final long threshold;

    /**
     * @param threshold The distance in seconds, in which two locations should have changed
     */
    public TimeFilter(long threshold) {
        this.threshold = threshold * 1000;
    }

    @Override
    public boolean passesFilter(Location l1, Location l2) {
        return (l2.getTime() - l1.getTime()) > threshold;
    }
}
