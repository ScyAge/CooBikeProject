package vehicle.equipment;

import vehicle.Vehicle;

/**
 * Electric class.
 */
public class Electric extends Equipment {
    /**
     * Constructor.
     * @param vehicle the vehicle
     */
    public Electric(Vehicle vehicle) {
        super(vehicle);
    }
    @Override
    public String decorateEquipment() {
        return   String.format("%s an electrical supply ",super.decorateEquipment());
    }
}
