package vehicle.vehicleVisitor;

import controlCenter.ControlCenter;
import exeption.StationFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import vehicle.Vehicle;
import vehicle.stateVehicle.HS;
import vehicle.stateVehicle.Service;
import vehicle.vehicleCreator.BikeCreator;
import vehicle.vehicleCreator.VehicleCreator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RepairTest {

    private Repair repair;
    private ControlCenter controlCenter;
    VehicleCreator vehicleCreator = new BikeCreator();
    Vehicle v1;
    Vehicle v2;
    Vehicle v3;
    List<Station> stations;
    Set<Vehicle> vehicles;
    Station s1;
    Station s2;

    @BeforeEach
    void setUp() throws StationFullException {
        stations = new ArrayList<>();
        vehicles = new HashSet<>();
        s1 = new Station();
        s2 = new Station();
        stations.add(s1);
        stations.add(s2);
        v1 = vehicleCreator.createVehicle();
        v2 = vehicleCreator.createVehicle();
        v3 = vehicleCreator.createVehicle();
        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);
        controlCenter = new ControlCenter(vehicles);
        s1.dropOffVehicle(v1);
        s1.dropOffVehicle(v2);
        s1.dropOffVehicle(v3);
        repair = new Repair(controlCenter);
    }

    @Test
    public void testFilterCondition() {
        assertFalse(repair.filterCondition(v1));
        v1.toHS();
        assertTrue(repair.filterCondition(v1));
    }

    @Test
    public void visitBisCannotWorkVehicleTest() throws Exception {
        // v1 et v2 sont dans le control center, pour le test, on l'appelle depuis repair
        v1.toHS();
        repair.visit(v1);
        v2.toHS();
        assertThrows(Exception.class, () -> repair.visit(v2));
    }

    @Test
    public void visitBisRemoveVehicleTest() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertFalse(controlCenter.getVehicles().contains(v1));
    }

    @Test
    public void visitBisResetCountTest() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertTrue(repair.getTime().getInterValeNoModif() == 0);
    }

    @Test
    public void visitBisVehicleAttributTest() {
        assertTrue(repair.getVehicle() == null);
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertTrue(repair.getVehicle() == v1);
    }

    @Test
    public void updateTimeTestPlusOne() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertTrue(repair.getTime().getInterValeNoModif() == 0);
        this.repair.getTime().addOneInterValeNoModif();
        assertTrue(repair.getTime().getInterValeNoModif() == 1);
    }

    @Test
    public void updateTimeTestTwoTime() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertEquals(0, repair.getTime().getInterValeNoModif());
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.updateTime();
        assertEquals(0, repair.getTime().getInterValeNoModif());
    }

    @Test
    public void vehicleAddedAfterRepairTest() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertFalse(controlCenter.getVehicles().contains(v1));
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.updateTime();
        assertTrue(controlCenter.getVehicles().contains(v1));
    }

    @Test
    public void setStateToServiceAfterRepairVehicleTest() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertTrue(v1.getState().getClass() == HS.class);
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.updateTime();
        assertTrue(v1.getState().getClass() == Service.class);
    }

    @Test
    public void vehicleAttributNullAfterVehicleRepairTest() {
        v1.toHS();
        controlCenter.executeEventVehicle(repair);
        assertTrue(repair.getVehicle() == v1);
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.getTime().addOneInterValeNoModif();
        this.repair.updateTime();
        assertTrue(repair.getVehicle() == null);
    }

}