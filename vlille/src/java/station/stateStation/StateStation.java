package station.stateStation;

import station.Station;

/**
 * Interface for all possible station states
 */
public abstract class StateStation {

    /**
     * The station
     */
    protected Station s;

    /**
     * Constructor of statestation
     * @param s - The station test
     */
    public StateStation(Station s){
        this.s = s;
    }

    /**
     * Changes station status to empty
    */
    public abstract void toEmpty();


    /**
     * Changes station status to Normal
     */
    public  abstract void toNormal();

    /**
     * Changes station status to Full
     */
    public  abstract void toFull();



    /**
     * Checks if the station can rent a vehicle
     * @return boolean - true if there is at least one bike in the station, false otherwise
     */
    public  abstract boolean canBeRent();

    /**
     * Checks whether the station can hold vehicles
     * @return boolean - true if there is at least one free space in the station, false otherwise
     */
    public  abstract boolean canBeDropOff();

}
