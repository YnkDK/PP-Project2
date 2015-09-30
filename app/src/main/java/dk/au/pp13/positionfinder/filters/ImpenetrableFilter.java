package dk.au.pp13.positionfinder.filters;

import android.location.Location;

/**
 * A filter nothing can pass
 */
public class ImpenetrableFilter implements Filter {
    @Override
    public boolean passesFilter(Location l1, Location l2) {
        return false;
    }
}
