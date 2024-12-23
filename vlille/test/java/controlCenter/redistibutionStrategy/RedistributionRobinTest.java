package controlCenter.redistibutionStrategy;

import controlCenter.ControlCenter;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.MockStationTestRandom;
import station.Station;
import vehicle.Vehicle;
import vehicle.vehicleCreator.ScooterCreator;
import vehicle.vehicleCreator.VehicleCreator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedistributionRobinTest {

    private ControlCenter controlCenter;
    private List<Station> stations = new ArrayList<>();
    private MockStationTestRandom station1 = new MockStationTestRandom();
    private MockStationTestRandom station2 = new MockStationTestRandom();
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private VehicleCreator vehicleCreator = new ScooterCreator();

    @BeforeEach
    public void init() throws StationFullException {
        stations.add(station1);
        stations.add(station2);
        controlCenter = new ControlCenter(stations);
        vehicle1 = vehicleCreator.createVehicle();
        vehicle2 = vehicleCreator.createVehicle();
        station1.dropOffVehicle(vehicle1);
        station1.dropOffVehicle(vehicle2);
    }

    @Test
    public void testRedistributionFull() throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        assertTrue(station1.getVehicles().size() == 2);
        List<Station> filterStation = stations.stream().filter(s -> !s.equals(station1)).filter(Station::canBeDropOff).toList();
        filterStation.forEach(t ->assertTrue(t.canBeDropOff()));
        controlCenter.setStrategy(new RedistributionRobin());
        controlCenter.executeStrategyOnStation(station1);
        assertTrue(station1.getVehicles().size() == 1);
    }

    @Test
    public void testRedistributionEmpty() throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        assertTrue(station2.getVehicles().size() == 0);
        controlCenter.setStrategy(new RedistributionRobin());
        controlCenter.executeStrategyOnStation(station2);
        assertTrue(station2.getVehicles().size() == 1);
    }
}