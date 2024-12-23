package station.stationVisitor;

import station.Station;
import vehicle.Vehicle;

/**
 * Station visitor interface
 */
public interface StationVisitor {
    /**
     * visit a vehicle
     * @param station the station to visit
     * @throws Exception if an error occurs during the visit
     */
    void visit(Station station) throws Exception;

    /**
     * each implémentation need to create a predicate to filter a list of station the way they want
     * @param s the station which need to verificate the predicate
     * @return a boolean
     */
    boolean predicatTestFilter(Station s);


}
