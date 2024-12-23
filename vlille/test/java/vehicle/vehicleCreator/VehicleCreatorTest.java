package vehicle.vehicleCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vehicle.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

abstract class  VehicleCreatorTest {


    private VehicleCreator v;


    @BeforeEach
    void setUp() {
        this.v = this.createVehicleCreator();
    }

    protected abstract VehicleCreator createVehicleCreator();

    @Test
    void testVehicleFromASameCreatorAsDifferentID(){
        Vehicle u = this.v.createVehicle();
        Vehicle x = this.v.createVehicle();

        assertTrue(u.getId()<x.getId());
    }

    @Test
    void testVehicleFromDifferentCreatorAsDifferentID(){
        VehicleCreator t = new BikeCreator();
        Vehicle u = this.v.createVehicle();
        Vehicle x = t.createVehicle();

        assertTrue(u.getId()<x.getId());
    }

}