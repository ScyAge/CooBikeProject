package vehicle.stateVehicle;


import vehicle.Vehicle;

/**
 * Service state for the StateVehicle interface
 */
public class Service extends AbstractStateVehicle implements StateVehicle{

    /**
     * Cosntructor
     * @param v - The vehicle
     */
    public Service(Vehicle v) {
        super(v);
    }

    @Override
    public void toService(){};

    @Override
    public void toHS(){
        this.v.setState(new HS(this.v));
    };

    @Override
    public void toRobed(){
        this.v.setState(new Robed(this.v));
    };

    @Override
    public boolean isRentable(){
        return true;
    };
}
