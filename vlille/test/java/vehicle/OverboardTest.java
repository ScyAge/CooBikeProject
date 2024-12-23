package vehicle;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class OverboardTest extends VehicleTest {

    @Override
    protected Vehicle createVehicle() {
        return new Overboard(0);
    }

    @Override
    protected int returnMaxtimerented() {
        return Overboard.maxNbTimeRented;
    }
}