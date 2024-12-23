package vehicle.vehicleCreator;

import vehicle.Scooter;

/**
 * class ScooterCreator generates scooters
 */
public class ScooterCreator extends VehicleCreator {

    private int id;

    /**
     * Constructor
     */
    public ScooterCreator() {
        super();
    }
    /**
     * {@inheritDoc}.
     * Create a scooter
     * @return Scooter
     */
    public Scooter createVehicle() {
        return new Scooter(this.generateId());
    }

    /**
     * Return id of this vehicle
     * @return int - the id of this vehicle
     */
    public int getId() {
        return id;
    }
}
