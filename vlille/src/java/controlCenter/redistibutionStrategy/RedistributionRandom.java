package controlCenter.redistibutionStrategy;

import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import station.Station;
import vehicle.Vehicle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements the `RedistributionStrategy` interface using a random redistribution algorithm.
 */
public class RedistributionRandom implements RedistributionStrategy {

    /**
     * A `Random` instance used to generate random values for redistribution.
     */
    Random random = new Random();

    /**
     * Redistributes vehicles between stations based on the state of the target station.
     *
     * @param stations a set of all available stations for redistribution.
     * @param station the station where redistribution is initiated.
     * @throws StationFullException if the target station cannot accommodate more vehicles.
     * @throws StationEmptyException if the source station does not have vehicles to redistribute.
     * @throws NoVehicleOfThisTypeAvailableException if no vehicles of the required type are available for redistribution.
     */
    @Override
    public void reallocation(Set<Station> stations, Station station) throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        if (!station.canBeDropOff())
            reallocationFullEmpty(stations, station, true);
        else if (!station.canBeRent())
            reallocationFullEmpty(stations, station, false);
    }

    /**
     * Handles redistribution when the target station is either full or empty.
     *
     * @param stations a set of all available stations.
     * @param station the target station for redistribution.
     * @param isFull indicates whether the target station is full (`true`) or empty (`false`).
     * @throws StationFullException if a station cannot accommodate more vehicles.
     * @throws StationEmptyException if a station has no vehicles to redistribute.
     * @throws NoVehicleOfThisTypeAvailableException if no vehicles of the required type are available for redistribution.
     */
    private void reallocationFullEmpty(Set<Station> stations, Station station, boolean isFull) throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        int nbRedistribution = random.nextInt(1, station.getCapacityMax());
        for (int i = 0; i < nbRedistribution; i++)
            reallocationAlea(stations, station, isFull);
    }

    /**
     * Executes a single redistribution operation.
     *
     * @param stations a set of all available stations.
     * @param station the target station for redistribution.
     * @param isFull indicates whether the target station is full (`true`) or empty (`false`).
     * @throws StationFullException if a station cannot accommodate more vehicles.
     * @throws StationEmptyException if a station has no vehicles to redistribute.
     * @throws NoVehicleOfThisTypeAvailableException if no vehicles of the required type are available for redistribution.
     */
    private void reallocationAlea(Set<Station> stations, Station station, boolean isFull) throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        if (isFull) {
            List<Station> filterStation = stations.stream().filter(Station::canBeDropOff).collect(Collectors.toList());
            if (!filterStation.isEmpty()) {
                Vehicle vehicle = station.rentVehicle(Vehicle.class::isInstance);
                Station s = filterStation.get(random.nextInt(filterStation.size()));
                s.dropOffVehicle(vehicle);
            }
        } else {
            List<Station> filterStation = stations.stream().filter(Station::canBeRent).collect(Collectors.toList());
            if (!filterStation.isEmpty()) {
                Station s = filterStation.get(random.nextInt(filterStation.size()));
                Vehicle vehicle = s.rentVehicle(v -> v instanceof Vehicle);
                station.dropOffVehicle(vehicle);
            }
        }
    }
}
