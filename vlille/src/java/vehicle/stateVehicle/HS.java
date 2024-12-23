package vehicle.stateVehicle;


import vehicle.Vehicle;

/**
 * HS state for the StateVehicle interface
 */
public class HS extends AbstractStateVehicle implements StateVehicle{

    /**
     * Constructor state hs
     * @param v - the vehicle state
     */
    public HS(Vehicle v) {
        super(v);
    }

    @Override
    public void toService(){
        this.v.setState(new Service(this.v));
    };

    @Override
    public void toHS(){};

    @Override
    public void toRobed(){};

    @Override
    public boolean isRentable(){
        return false;
    };
}
