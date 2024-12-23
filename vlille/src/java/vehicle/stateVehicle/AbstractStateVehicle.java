package vehicle.stateVehicle;

import vehicle.Vehicle;

/**
 * The abstract class of vehicle states
 */
public class AbstractStateVehicle {

    /**
     * The vehicle
     */
    protected Vehicle v;

    /**
     * Constructor of AbstractStateVehicle
     * @param v - The vehicle
     */
    public AbstractStateVehicle(Vehicle v){
        this.v = v;
    }

}
