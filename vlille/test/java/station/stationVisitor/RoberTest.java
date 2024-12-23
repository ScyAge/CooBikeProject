package station.stationVisitor;

import controlCenter.ControlCenter;
import exeption.CantBeRobeException;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import vehicle.Bike;
import vehicle.Scooter;
import vehicle.Vehicle;
import vehicle.stateVehicle.HS;
import vehicle.stateVehicle.Robed;

import static org.junit.jupiter.api.Assertions.*;

class RoberTest {

    private Station station;
    private ControlCenter controlCenter;
    private Bike bike;
    private Rober rober;

    @BeforeEach
    void setUp() throws StationFullException {

        this.station = new Station();
        this.controlCenter = new ControlCenter();
        this.bike = new Bike(8);
        this.station.addSubscriber(controlCenter);
        this.station.dropOffVehicle(this.bike);
        this.rober = new Rober();
    }

    @Test
    void testRobeStationOk() throws Exception {
        assertEquals(1,this.station.getVehicles().size());
        assertEquals(1,this.controlCenter.getStations().get(this.station));
        assertTrue(rober.canBeRobe(this.station));

        rober.visit(this.station);

        assertFalse(this.station.getVehicles().contains(this.bike));
        assertInstanceOf(Robed.class,this.bike.getState());
        assertEquals(0,this.controlCenter.getStations().get(this.station));
        assertEquals(0,this.station.getVehicles().size());
        assertTrue(controlCenter.getStationToRedistribute().contains(this.station));
    }


    @Test
    void testRobeStationKo() throws Exception {
        assertEquals(1,this.station.getVehicles().size());
        assertEquals(1,this.controlCenter.getStations().get(this.station));
        this.bike.toHS();

        assertFalse(rober.canBeRobe(this.station));
        assertThrows(CantBeRobeException.class,() -> this.rober.visit(this.station));
        assertTrue(this.station.getVehicles().contains(this.bike));
        assertInstanceOf(HS.class,this.bike.getState());
        assertEquals(1,this.controlCenter.getStations().get(this.station));
        assertEquals(1,this.station.getVehicles().size());
        assertFalse(controlCenter.getStationToRedistribute().contains(this.station));

    }

    @Test
    void testPredicateTestFilter() throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        //case true station with 1 vehicle and in the same state for 2 turn
        this.station.getTime().addOneInterValeNoModif();
        this.station.getTime().addOneInterValeNoModif();

        assertTrue(this.rober.predicatTestFilter(station));

        //case false
        // 2 cond are false
        Station s2 = new Station();
        s2.addSubscriber(controlCenter);
        s2.dropOffVehicle(new Scooter(5));
        s2.dropOffVehicle(new Scooter(5));
        s2.getTime().addOneInterValeNoModif();

        assertFalse(this.rober.predicatTestFilter(s2));

        // to much or less vehicle
        s2.getTime().addOneInterValeNoModif();
        assertFalse(this.rober.predicatTestFilter(s2));

        //case not much time
        s2.rentVehicle(t -> t instanceof Vehicle);
        s2.getTime().addOneInterValeNoModif();

        assertFalse(this.rober.predicatTestFilter(s2));

    }


}