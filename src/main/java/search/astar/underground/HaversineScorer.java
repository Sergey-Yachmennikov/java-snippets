package search.astar.underground;

import search.astar.Scorer;


public class HaversineScorer implements Scorer<Station> {

    @Override
    public double computeCost(Station from, Station to) {
        double earthRadius = 6372.8; // In kilometers

        double dLat = Math.toRadians(to.latitude() - from.latitude());
        double dLon = Math.toRadians(to.longitude() - from.longitude());
        double lat1 = Math.toRadians(from.latitude());
        double lat2 = Math.toRadians(to.latitude());

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return earthRadius * c;
    }
}
