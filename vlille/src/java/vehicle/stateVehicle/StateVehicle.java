package vehicle.stateVehicle;

/**
 * Vehicle State management interface
 */
public interface StateVehicle {


    /**
     * method that changes the current state to the Service state
     */
    public void toService();

    /**
     * method that changes the current state to the HS state
     */
    public void toHS();

    /**
     * method that changes the current state to the Robbed state
     */
    public void toRobed();

    /**
     * method that tells us if the vehicle is rentable in its current state
     * @return a boolean as the response
     */
    public boolean isRentable();
}
