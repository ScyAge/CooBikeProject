package station.clientStation;

import exeption.StationFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import vehicle.MockVehicle;
import vehicle.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractClientStationTest {

    protected AbstractClientStation abstractClientStation;
    protected Station station = new Station();
    protected Vehicle vehicle;

    protected abstract AbstractClientStation createAbstractClientStation();
    protected abstract Vehicle createVehicle();

    @BeforeEach
    public void init() throws StationFullException {
        this.abstractClientStation = this.createAbstractClientStation();
        this.vehicle = createVehicle();
        station.dropOffVehicle(this.vehicle);
    }

    @Test
    public void testVisitOk() {
        Vehicle vehicle = this.abstractClientStation.visit(station);
        assertEquals(vehicle, this.vehicle);
    }

    @Test
    public void testVisitKo() {
        Station s = new Station();
        Vehicle vehicle = this.abstractClientStation.visit(s);
        assertEquals(vehicle, null);
    }
    @Test
    public void testVehicleRightType(){
        assertFalse(this.abstractClientStation.testMethod().testTypeVehicle(new MockVehicle(8)));
    }
}