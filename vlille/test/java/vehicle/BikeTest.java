package vehicle;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BikeTest extends VehicleTest {


    @Override
    protected Vehicle createVehicle() {
        return new Bike(0);
    }

    @Override
    protected int returnMaxtimerented() {
        return Bike.maxNbTimeRented;
    }
}