package controlCenter.redistibutionStrategy;

import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import station.Station;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * interface that manages the different ways of redistributing vehicles to the stations
 */
public interface RedistributionStrategy {

    /**
     * method that performs a type of redistribution
     * @param stations - set of stations
     * @param station - station to be redistributed
     * @throws StationFullException - exception thrown when the station is full
     * @throws StationEmptyException - exception thrown when the station is empty
     * @throws NoVehicleOfThisTypeAvailableException - exception thrown when there are no vehicles of the type to be redistributed
     */
    void reallocation(Set<Station> stations, Station station) throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException;
}
