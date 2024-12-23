package controlCenter.redistibutionStrategy;

import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import station.Station;
import vehicle.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RedistributionRobin class
 * Implements a round-robin redistribution strategy for vehicles between stations.
 */
public class RedistributionRobin implements RedistributionStrategy {

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
            reallocationRobin(stations, station, true);
        else if (!station.canBeRent())
            reallocationRobin(stations, station, false);
    }

    /**
     * Performs redistribution in a round-robin manner based on whether the target station is full or empty.
     *
     * @param stations a set of all available stations.
     * @param station the target station for redistribution.
     * @param isFull indicates whether the target station is full (`true`) or empty (`false`).
     * @throws StationFullException if a station cannot accommodate more vehicles.
     * @throws StationEmptyException if a station has no vehicles to redistribute.
     * @throws NoVehicleOfThisTypeAvailableException if no vehicles of the required type are available for redistribution.
     */
    private void reallocationRobin(Set<Station> stations, Station station, boolean isFull) throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        int nbReallocation = station.getCapacityMax() / 2;
        if (isFull) {
            for (int i = 0; i < nbReallocation; i++) {
                List<Station> filterStation = stations.stream().filter(s -> !s.equals(station)).filter(Station::canBeDropOff).toList();
                if (!filterStation.isEmpty()) {
                    Vehicle vehicle = station.rentVehicle(v -> v instanceof Vehicle);
                    Station s = takeEmptiestStation(filterStation);
                    s.dropOffVehicle(vehicle);
                }
            }
        } else {
            for (int i = 0; i < nbReallocation; i++) {
                List<Station> filterStation = stations.stream().filter(s -> !s.equals(station)).filter(Station::canBeRent).collect(Collectors.toList());
                if (!filterStation.isEmpty()) {
                    Station s = takeFullestStation(filterStation);
                    Vehicle vehicle = s.rentVehicle(v -> v instanceof Vehicle);
                    station.dropOffVehicle(vehicle);
                }
                else {
                    break;
                }
            }
        }
    }

    /**
     * Finds the station with the most vehicles.
     *
     * @param stations a list of stations to compare.
     * @return the station with the highest number of vehicles, or `null` if the list is empty.
     */
    private Station takeFullestStation(List<Station> stations) {
        Optional<Station> stationFullest = stations.stream().max(Comparator.comparingInt(s -> s.getVehicles().size()));
        return stationFullest.orElse(null);
    }

    /**
     * Finds the station with the least vehicles.
     *
     * @param stations a list of stations to compare.
     * @return the station with the lowest number of vehicles, or `null` if the list is empty.
     */
    private Station takeEmptiestStation(List<Station> stations) {
        Optional<Station> stationEmptiest = stations.stream().min(Comparator.comparingInt(s -> s.getVehicles().size()));
        return stationEmptiest.orElse(null);
    }
}
