package station.clientStation;

import vehicle.Bike;
import vehicle.Vehicle;

class BikeVisitorTest extends AbstractClientStationTest {

    @Override
    protected AbstractClientStation createAbstractClientStation() {
        return new BikeVisitor();
    }

    @Override
    protected Vehicle createVehicle() {
        return new Bike(0);
    }
}