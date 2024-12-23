package vehicle;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ScooterTest extends VehicleTest {
    @Override
    protected Vehicle createVehicle() {
        return new Scooter(0);
    }

    @Override
    protected int returnMaxtimerented() {
        return Scooter.maxNbTimeRented;
    }
}