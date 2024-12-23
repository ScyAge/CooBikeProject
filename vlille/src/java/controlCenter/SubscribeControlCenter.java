package controlCenter;

import station.Station;

/**
 * interface SubscribeControlCenter
 */
public interface SubscribeControlCenter {
    /**
     * Notify the control center that a station is empty
     * @param s the station that send the notification
     */
    public void  notifyStationEmpty(Station s);

    /**
     * Notify the control center that a station is full
     * @param s the station that send the notification
     */
    public void  notifyStationFull(Station s);

    /**
     * Notify the control center that a vehicle has been added to a station
     * @param s the station that send the notification
     */
    public void  notifyStationVehicleAdded(Station s);


    /**
     * Notify the control center that a vehicle has been taken from a station
     * @param s the station that send the notification
     */
    public void  notifyStationVehicleTaked(Station s);

}
