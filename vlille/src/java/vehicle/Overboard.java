package vehicle;

import vehicle.vehicleVisitor.VehicleVisitor;

/**
 * Overboard class
 */
public class Overboard extends Vehicle {

    public static final int maxNbTimeRented = 2;
    /**
     * Constructor overboard
     * @param id - the id
     */
    public Overboard(int id) {
        super(id);
    }

    @Override
    public String decorateEquipment() {
        return "This is an Overboard";
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
