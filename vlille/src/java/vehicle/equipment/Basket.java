package vehicle.equipment;

import vehicle.Vehicle;

/**
 * Basket class.
 */
public class Basket extends Equipment {

    /**
     * Constructor.
     * @param vehicle the vehicle
     */
    public Basket(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public String decorateEquipment() {
        return String.format("%s a basket ",super.decorateEquipment());
    }
}
