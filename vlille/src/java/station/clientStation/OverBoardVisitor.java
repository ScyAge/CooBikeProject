package station.clientStation;

import vehicle.Overboard;

/**
 * reprend a client who want an Overboard
 */
public class OverBoardVisitor extends AbstractClientStation {

    @Override
    public TypeVehicleTest testMethod() {
        return v -> v instanceof Overboard;
    }
}
