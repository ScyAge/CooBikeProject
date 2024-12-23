package vehicle.stateVehicle;

import vehicle.Vehicle;

/**
 * Robed state for the StateVehicle interface
 */
public class Robed extends AbstractStateVehicle implements StateVehicle{

    /**
     * Constructor of robed
     * @param v - the vehicle robed
     */
    public Robed(Vehicle v) {
        super(v);
    }

    @Override
    public void toService(){};

    @Override
    public void toHS(){};

    @Override
    public void toRobed(){};

    @Override
    public boolean isRentable(){
        return false;
    };
}
