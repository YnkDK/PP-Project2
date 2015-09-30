package dk.au.pp13.positionfinder.filters;

import android.location.Location;

/**
 * Created by mys on 9/30/15.
 */
public class AccelerometerFilter implements Filter {
    private boolean movement = false;
    private final HaversineFilter haversineFilter;

    public AccelerometerFilter(long distance) {
        haversineFilter = new HaversineFilter(distance);
    }

    @Override
    public boolean passesFilter(Location l1, Location l2) {
        return movement && haversineFilter.passesFilter(l1, l2);
    }

    public void movement() {
        movement = true;
    }

    public void noMovement() {
        movement = false;
    }
}
