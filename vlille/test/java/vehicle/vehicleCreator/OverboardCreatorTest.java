package vehicle.vehicleCreator;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class OverboardCreatorTest extends VehicleCreatorTest{


    @Override
    protected VehicleCreator createVehicleCreator() {
        return new OverboardCreator();
    }


}