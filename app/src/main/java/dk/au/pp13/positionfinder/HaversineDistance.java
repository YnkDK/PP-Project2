package dk.au.pp13.positionfinder;

import android.location.Location;


public class HaversineDistance implements Distance {

    /**
     * Use the Haversine formula to estimate the distance
     * between the a and b
     * Source: http://en.wikipedia.org/wiki/Haversine_formula
     *
     * @param l1 First location
     * @param l2 Second location
     * @return The Haversine distance
     */
    @Override
    public double distance(Location l1, Location l2) {
        // Mean radius of earth (http://en.wikipedia.org/wiki/Earth_radius)
        final double r = 6371;
        // Convert to radians
        final double a1 = Math.toRadians(l1.getLongitude()), a2 = Math.toRadians(l1.getLatitude());
        final double b1 = Math.toRadians(l2.getLongitude()), b2 = Math.toRadians(l2.getLatitude());
        // haversine(phi2 - phi1)
        final double h1 = (1. - Math.cos(b2 - a2)) / 2.;
        // haversin(lambda2 - lambda1)
        final double h2 = (1. - Math.cos(b1 - a1)) / 2.;

        // THE HAVERSINE FORMULA
        final double h = h1 + Math.cos(a2) * Math.cos(b2) * h2;
        // Solve for d using arcsine
        return 2. * r * Math.asin(Math.sqrt(h));
    }

    @Override
    public String name() {
        return "Haversine Distance";
    }
}
