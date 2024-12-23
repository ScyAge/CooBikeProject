package vehicle;

import vehicle.vehicleVisitor.VehicleVisitor;

/**
 * Scooter class
 */
public class Scooter extends Vehicle {

    public static final int maxNbTimeRented = 4;
    /**
     * Constructor scooter
     * @param id - the id
     */
    public Scooter(int id) {
        super(id);
    }

    @Override
    public String decorateEquipment() {
        return "This is a Scooter";
    }

    @Override
    protected int nbMaxTimeRented() {
        return maxNbTimeRented;
    }

    @Override
    public void accept(VehicleVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
