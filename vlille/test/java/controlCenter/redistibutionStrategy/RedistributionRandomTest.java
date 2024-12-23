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

class RedistributionRandomTest {
    private ControlCenter controlCenter;
    private List<Station> stations = new ArrayList<>();
    private Station station1 = new MockStationTestRandom();
    private Station station2 = new MockStationTestRandom();
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
        assertTrue(nbVehicleStation() == 2);
        controlCenter.setStrategy(new RedistributionRandom());
        controlCenter.executeStrategyOnStation(station1);
        assertTrue(nbVehicleStation() == 2);
    }

    @Test
    public void testRedistributionEmpty() throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        assertTrue(nbVehicleStation() == 2);
        controlCenter.setStrategy(new RedistributionRandom());
        controlCenter.executeStrategyOnStation(station2);
        assertTrue(nbVehicleStation() == 2);
    }

    private int nbVehicleStation() {
        int res = 0;
        for (Station station : stations)
            res += station.getVehicles().size();
        return res;
    }
}