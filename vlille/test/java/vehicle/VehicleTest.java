package vehicle;

import exeption.StationFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.MockStationTestNotif;
import station.MockStationVehcNbTimeRent;
import station.Station;
import vehicle.equipment.MockEquip;
import vehicle.stateVehicle.HS;
import vehicle.stateVehicle.Robed;

import static org.junit.jupiter.api.Assertions.*;

abstract class VehicleTest {

    protected Vehicle vehicle;
    protected Station station;
    private MockStationVehcNbTimeRent stationDrop;
    private Vehicle mockVec;

    protected abstract Vehicle createVehicle();
    protected abstract int returnMaxtimerented();

    @BeforeEach
    void init() {
        this.vehicle = this.createVehicle();
        this.station = new MockStationTestNotif();
        this.stationDrop = new MockStationVehcNbTimeRent();
        this.mockVec = new MockVehicle(0);
    }

    @Test
    void isRentableTestOk() {
        assertTrue(vehicle.isRentable());
    }

    @Test
    void isRentableTestKo1() {
        vehicle.toHS();
        assertFalse(vehicle.isRentable());
    }

    @Test
    void isRentableTestKo2() {
        vehicle.toRobed();
        assertFalse(vehicle.isRentable());
    }

    /*
    @Test
    void getNbTimeRentedTest() {
        vehicle.setStation(station);
        assertEquals(0, vehicle.getNbTimeRented());
        try {
            station.rentVehicle(t -> t instanceof Vehicle);
            station.dropOffVehicle(vehicle);
            assertEquals(1, vehicle.getNbTimeRented());
        } catch (NoVehicleOfThisTypeAvailableException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    */


    @Test
    void addOneNbTimeRentedTest() {
        assertEquals(vehicle.getNbTimeRented(), 0);
        vehicle.addOneNbTimeRented();
        assertEquals(vehicle.getNbTimeRented(), 1);
    }

    @Test
    void decorateEquipmentTestWith() {
        //test case when the first decorator is wrapped
        assertFalse(this.vehicle.decorateEquipment().contains("with"));

        //decorate de equip
        Vehicle t = new MockEquip(this.vehicle);


        assertTrue(t.decorateEquipment().contains("with"));
    }

    @Test
    void decorateEquipmentTestAnd() {
        //test case when the vehicle is already decorate, the String can't have more than than 1 with so after is only and
        Vehicle t = new MockEquip(this.vehicle);
        assertTrue(t.decorateEquipment().contains("with"));

        //decorate de equip
        Vehicle u = new MockEquip(t);
        assertTrue(u.decorateEquipment().contains("and"));

        //let's add one more and see if we have 2 and
        Vehicle y = new MockEquip(u);
        assertTrue(y.decorateEquipment().contains("and MockE and MockE"));



    }

    @Test
    void testNbTimeRentedAugmentedWhenVehicleIsDropOff() throws StationFullException {
        int nbTimeRentedInit = this.vehicle.getNbTimeRented();

        this.stationDrop.dropOffVehicle(this.vehicle);

        assertEquals(1,this.vehicle.getNbTimeRented()-nbTimeRentedInit);
    }


    @Test
    void testStateChangeWhenVehicleIsDropOffAndNbTimeAugmentedLimitReach() throws StationFullException {
        int nbTimeRentedInit = this.mockVec.getNbTimeRented();

        this.stationDrop.dropOffVehicle(this.mockVec);

        assertEquals(1,this.mockVec.getNbTimeRented()-nbTimeRentedInit);
        assertFalse(this.mockVec.isRentable());
        assertInstanceOf(HS.class,this.mockVec.getState());

    }

    @Test
    void testDecorate(){
        assertTrue(this.vehicle.decorateEquipment().contains(this.vehicle.getClass().getSimpleName()));
    }

    @Test
    void testMaxNbTimeRented() {
        assertEquals(this.returnMaxtimerented(),this.vehicle.nbMaxTimeRented());
    }
}