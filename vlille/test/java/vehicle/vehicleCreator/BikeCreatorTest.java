package vehicle.vehicleCreator;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BikeCreatorTest extends VehicleCreatorTest{

    @Override
    protected VehicleCreator createVehicleCreator() {
        return new BikeCreator();
    }

}