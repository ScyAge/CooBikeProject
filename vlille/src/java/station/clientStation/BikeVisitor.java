package station.clientStation;

import vehicle.Bike;

/**
 * Class bike visitor
 */
public class BikeVisitor extends AbstractClientStation {

    /**
     * Return instance Bike
     * @return - Return instance Bike
     */
    @Override
    public TypeVehicleTest testMethod() {
        return v -> v instanceof Bike;
    }

}
