package station.clientStation;

import vehicle.Scooter;
import vehicle.Vehicle;

class ScooterVisitorTest extends AbstractClientStationTest {

    @Override
    protected AbstractClientStation createAbstractClientStation() {
        return new ScooterVisitor();
    }

    @Override
    protected Vehicle createVehicle() {
        return new Scooter(0);
    }
}