package controlCenter;

import controlCenter.redistibutionStrategy.MockControlCenterTestFilterStation;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.MockStationTestNotif;
import station.MockStationTestRandom;
import station.Station;
import station.stateStation.Empty;
import station.stateStation.Full;
import station.stationVisitor.MockStationVisitor;
import station.stationVisitor.StationVisitor;
import timeControler.TimeDependencies;
import vehicle.Bike;
import vehicle.Overboard;
import vehicle.Scooter;
import vehicle.Vehicle;
import vehicle.stateVehicle.HS;
import vehicle.vehicleVisitor.Repair;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControlCenterTest {

    private Station station;
    private ControlCenter controlCenter;
    private Bike bike;
    private Scooter scooter;
    private Overboard overboard;
    private MockControlCenterTestFilterStation cf;
    private MockControlCenterTestExecuteThowsVecAndStation ce;

    @BeforeEach
    void setUp() {
        List<Station> t = new ArrayList<>();
        this.station = new MockStationTestNotif();
        t.add(station);
        this.controlCenter = new ControlCenter(t);

        this.bike = new Bike(0);
        this.scooter = new Scooter(0);
        this.overboard = new Overboard(0);

        this.ce = new MockControlCenterTestExecuteThowsVecAndStation();
        this.cf = new MockControlCenterTestFilterStation();
    }

    @Test
    void notifyStationVehicleAddedTest() throws Exception {
        Station test = new Station();
        test.addSubscriber(controlCenter);

        //if the station is already in the map
        assertEquals(0, controlCenter.getNbVehicleStation(station));
        station.dropOffVehicle(bike);
        assertEquals(1, controlCenter.getNbVehicleStation(station));

        //if the station not in the map
        assertFalse(controlCenter.getStations().containsKey(test));
        test.dropOffVehicle(bike);
        assertTrue(controlCenter.getStations().containsKey(test));
        assertEquals(1, controlCenter.getNbVehicleStation(test));

    }

    @Test
    void notifyStationVehicleTakedTestOk() throws Exception {
        Station test = new MockStationTestNotif();
        test.addSubscriber(controlCenter);
        this.station.dropOffVehicle(this.overboard);
        test.getVehicles().add(bike);

        //if the station is already in the map
        int nb_v = this.controlCenter.getNbVehicleStation(station);
        assertEquals(0, nb_v - this.station.getVehicles().size());
        station.rentVehicle(t -> t instanceof Vehicle);
        assertEquals(nb_v - 1, controlCenter.getNbVehicleStation(station));
        assertThrows(NoSuchElementException.class, () -> station.rentVehicle(t -> t instanceof Vehicle));
        assertEquals(nb_v - 1, controlCenter.getNbVehicleStation(station));

        //if the station not in the map
        int nb_vehicle_station = test.getVehicles().size();
        assertFalse(controlCenter.getStations().containsKey(test));
        test.rentVehicle(t -> t instanceof Vehicle);
        assertTrue(controlCenter.getStations().containsKey(test));
        assertEquals(controlCenter.getNbVehicleStation(test), nb_vehicle_station - 1);
        assertThrows(NoSuchElementException.class, () -> test.rentVehicle(t -> t instanceof Vehicle));
        assertEquals(controlCenter.getNbVehicleStation(test), nb_vehicle_station - 1);
    }

    //TODO faire test KO pour les deux précédent test
    @Test
    void notifyStationVehicleAddedTestKo() throws Exception {
        Station test = new MockStationTestRandom();
        test.addSubscriber(controlCenter);
        test.dropOffVehicle(bike);
        test.dropOffVehicle(scooter);

        int nb_v = this.controlCenter.getNbVehicleStation(test);
        assertEquals(0, nb_v - test.getVehicles().size());
        assertThrows(StationFullException.class, () -> test.dropOffVehicle(overboard));
        assertEquals(0, nb_v - test.getVehicles().size());
    }

    @Test
    void testNotifyWhenStationIsFullEmpty() throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        //already in list of redistribute vehicle because she was empty when added at the contstruction
        assertInstanceOf(Empty.class, this.station.getStateStation());
        assertTrue(this.controlCenter.getStationToRedistribute().contains(this.station));
        this.station.dropOffVehicle(this.bike);

        //because a vehicle has been added so she don't need to be redistributed anymore
        assertFalse(this.controlCenter.getStationToRedistribute().contains(this.station));

        //station is full so she come back in the list
        this.station.dropOffVehicle(this.bike);
        assertTrue(this.controlCenter.getStationToRedistribute().contains(this.station));
        assertInstanceOf(Full.class, this.station.getStateStation());

        //if we rent all the vehicles the station is in the list
        this.station.rentVehicle(v -> v instanceof Vehicle);
        this.station.rentVehicle(v -> v instanceof Vehicle);
        assertInstanceOf(Empty.class, this.station.getStateStation());
        assertTrue(this.controlCenter.getStationToRedistribute().contains(this.station));
    }

    @Test
    void testExecuteEventVehicleKo() throws StationFullException {
        // L'exception n'est pas levé car elle est géré avec un catch
        Station station1 = new Station();
        //verification exepxtion has not been lauch before
        int count_init = this.ce.call_Null_point;
        assertEquals(0,count_init);

        //add subcriber and vehicle to station
        station1.addSubscriber(ce);
        station1.dropOffVehicle(bike);
        station1.dropOffVehicle(overboard);
        Repair repair = new Repair(ce);

        //execute the event who must lauch the exeption
        ce.executeEventVehicle(repair);
        assertEquals(1,count_init+ this.ce.call_Null_point);


    }

    @Test
    void testExecuteVisitThrowError() throws StationFullException {
        // L'exception n'est pas levé car elle est géré avec un catch
        Station station1 = new Station();
        //verification exepxtion has not been lauch before
        int count_init = this.ce.call_Exep_point;
        assertEquals(0,count_init);

        //add subcriber and vehicle to station
        station1.addSubscriber(ce);
        station1.dropOffVehicle(bike);
        station1.dropOffVehicle(overboard);
        this.bike.toHS();
        this.overboard.toHS();
        Repair repair = new Repair(ce);

        //execute the event who the first one don't lauch exeption
        ce.executeEventVehicle(repair);
        assertEquals(0,count_init+ this.ce.call_Exep_point);

        //the second one lauch exeption because the Vehicle visitor do something
        ce.executeEventVehicle(repair);
        assertEquals(1, this.ce.call_Exep_point);
    }

    @Test
    void testExecuteEventStationOK() throws StationFullException {

        // create a station with vehicle
        Station station1 = new Station();
        station1.addSubscriber(cf);
        station1.dropOffVehicle(bike);
        station1.dropOffVehicle(overboard);


        // 2 station
        Station station2 = new Station();
        station2.addSubscriber(cf);
        station2.dropOffVehicle(new Overboard(9));

        //mock return and use is effect only on station with 2 vehicle or more and turn hs the first bike
        StationVisitor visitor = new MockStationVisitor();

        assertEquals(2,cf.getStations().size());

        cf.executeEventStation(visitor);

        //the good station returned
        assertSame(MockControlCenterTestFilterStation.stationGet,station1);
        // the first vehicle now hs
        assertInstanceOf(HS.class,station1.getVehicle().getState());

    }
    @Test
    void testExecuteEventStationKONoVehicleFiltered() throws StationFullException {
        //verification exepxtion has not been lauch before
        int count_init = this.ce.call_Null_point;
        assertEquals(0,count_init);

        // create a station with vehicle
        Station station1 = new Station();
        station1.addSubscriber(ce);
        station1.dropOffVehicle(bike);

        // 2 station
        Station station2 = new Station();
        station2.addSubscriber(ce);
        station2.dropOffVehicle(new Overboard(9));
        StationVisitor visitor = new MockStationVisitor();

        //execute the event who must lauch the exeption
        ce.executeEventStation(visitor);
        assertEquals(1,count_init+ this.ce.call_Null_point);
    }

    @Test
    void testExecuteVisitStationThrowError() throws StationFullException {

        //verification exepxtion has not been lauch before
        int count_init = this.ce.call_Exep_point;
        assertEquals(0,count_init);

        // create a station with vehicle
        Station station1 = new Station();
        station1.addSubscriber(ce);
        station1.dropOffVehicle(bike);
        station1.dropOffVehicle(overboard);
        station1.dropOffVehicle(new Scooter(8));

        StationVisitor visitor = new MockStationVisitor();

        //execute the event who must lauch the exeption cant work cause the vehilceVisior himself throw an error
        ce.executeEventStation(visitor);
        assertEquals(1,count_init+ this.ce.call_Exep_point);
    }

    @Test
    void testControleCenterConstructorStation() throws StationFullException {

        List<Station> stations= new ArrayList<>();
        //creation of a station without  vehicle
        Station station1 = new Station();
        stations.add(station1);
        //creation of a station with 1 vehicle
        this.station.dropOffVehicle(this.bike);
        stations.add(this.station);


        ControlCenter c = new ControlCenter(stations);

        assertEquals(c.getStations().size(),stations.size());
        assertEquals(0,c.getStations().get(station1));
        assertEquals(1,c.getStations().get(station));

        assertTrue(c.getStationToRedistribute().contains(station1));
        assertFalse(c.getStationToRedistribute().contains(station));
    }


    @Test
    void testRedistribution() throws StationFullException {
        Station s1 = new Station();
        Station s2 = new Station();
        List<Station> test= new ArrayList<>();
        test.add(s1);
        test.add(s2);
        ControlCenter c1 = new ControlCenter(test);


        //test redistribution with 2 station and 2 vehicle


        s2.dropOffVehicle(this.bike);
        s2.dropOffVehicle(this.overboard);

        //gestion time
        List<TimeDependencies> t= new ArrayList<>();
        t.add(s1);
        t.add(s2);

        t.forEach(x -> x.getTime().addOneInterValeNoModif());
        t.forEach(x -> x.getTime().addOneInterValeNoModif());

        assertEquals(2,s1.getTime().getInterValeNoModif());
        assertEquals(2,s2.getTime().getInterValeNoModif());
        assertEquals(2,c1.getStations().get(s2));
        assertEquals(0,c1.getStations().get(s1));
        assertTrue(c1.getStationToRedistribute().contains(s1));


        c1.executeStrategyRedistribution();

        assertEquals(0,s1.getTime().getInterValeNoModif());
        assertEquals(0,s2.getTime().getInterValeNoModif());
        assertEquals(0,c1.getStations().get(s2));
        assertEquals(2,c1.getStations().get(s1));
        assertFalse(c1.getStationToRedistribute().contains(s1));

        assertTrue(s1.getVehicles().contains(this.bike) ||s1.getVehicles().contains(this.overboard));

    }


}