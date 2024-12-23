package station.clientStation;

import vehicle.Scooter;

/**
 * reprend a client who want a Scooter
 */
public class ScooterVisitor extends AbstractClientStation {

    @Override
    public TypeVehicleTest testMethod() {
        return v -> v instanceof Scooter;
    }
}
