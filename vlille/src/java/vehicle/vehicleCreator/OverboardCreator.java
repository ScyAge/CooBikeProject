package vehicle.vehicleCreator;

import vehicle.Overboard;

/**
 * class OverboardCreator generates overboards
 */
public class OverboardCreator extends VehicleCreator {

    private int id;

    /**
     * Constructor
     */
    public OverboardCreator() {
        super();
    }
    /**
     * {@inheritDoc}.
     * Create a Overboard
     * @return Overboard
     */
    public Overboard createVehicle() {
        return new Overboard(this.generateId());
    }

    /**
     * Return id of this vehicle
     * @return int - the id of this vehicle
     */
    public int getId() {
        return id;
    }
}
