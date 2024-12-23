package vehicle.vehicleCreator;

import vehicle.Bike;

/**
 * class BikeCreator generates bikes
 */
public class BikeCreator extends VehicleCreator {

    private int id;

    /**
     * Constructor
     */
    public BikeCreator() {
        super();
    }

    /**
     * {@inheritDoc}.
     * Create a Bike
     * @return Bike
     */
    public Bike createVehicle() {
        return new Bike(this.generateId());
    }

    /**
     * Return id of this vehicle
     * @return int - the id of this vehicle
     */
    public int getId() {
        return id;
    }

}
