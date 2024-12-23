package vehicle.equipment;

import vehicle.Vehicle;

public class MockEquip extends Equipment{

    /**
     * Constructor.
     *
     * @param vehicle the vehicle
     */
    public MockEquip(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public String decorateEquipment() {
        return String.format("%sMockE ",super.decorateEquipment());
    }
}
