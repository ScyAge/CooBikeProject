package station;

import controlCenter.ControlCenter;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import station.stateStation.Empty;
import station.stateStation.StateStation;
import station.clientStation.TypeVehicleTest;
import station.stationVisitor.StationVisitor;
import timeControler.TimeDependencies;
import vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to manage stations
 */
public class Station extends TimeDependencies {


    /**
     * List of the vehicle
     */
    protected final List<Vehicle>vehicles;
    /**
     * List of subscriber
     */
    protected final List<ControlCenter> subsribers;
    private final int id;
    private final int capacityMax;
    private StateStation stateStation;
    private static int id_vec = 0;

    /**
     * Constructor Station
     */
    public Station() {
        this.id = createId();
        this.capacityMax = randomCapacityMax();
        this.vehicles = new ArrayList<>(this.capacityMax);
        this.stateStation = new Empty(this);
        this.subsribers = new ArrayList<>();
    }

    public Station(ControlCenter controlCenter) {
        this.id = createId();
        this.capacityMax = randomCapacityMax();
        this.vehicles = new ArrayList<>(this.capacityMax);
        this.stateStation = new Empty(this);
        this.subsribers = new ArrayList<>();
        this.subsribers.add(controlCenter);
    }

    /**
     * Get the capacityMax station
     * @return the capacityMax station
     */
    public int getCapacityMax() {
        return capacityMax;
    }

    /**
     * Private method that creates an identifier for each station
     * @return int - Id
     */
    private static int createId() {
        return id_vec++;
    }

    /**
     * Private method that creates a maximum vehicle capacity for each station
     * @return int - Between 10 to 20 vehicle capacity
     */
    protected int randomCapacityMax() {
        Random x = new Random();
        return 10 + x.nextInt(11);
    }

    /**
     * Add a vehicle to the station
     * @param vehicle - The vehicle to be added
     * @throws StationFullException - if the station is full
     */
    public void dropOffVehicle(Vehicle vehicle) throws StationFullException {
        if(this.canBeDropOff()){
            addVehicleToStation(vehicle);
        }
        else {
            throw new StationFullException("Station is Full, dropOffVehicle cancel");
        }
    }

    /**
     * add a vehicle in station and update State,Time, and notify the controlCenter
     * @param vehicle the vehicle added
     */
    private void addVehicleToStation(Vehicle vehicle) {
        this.subsribers.forEach(c -> c.addVehicleList(vehicle));
        this.vehicles.add(vehicle);
        this.subsribers.forEach(t -> t.notifyStationVehicleAdded(this));
        this.updateStateStation();
        vehicle.addOneNbTimeRented();
        this.resetTimeSation();
    }

    /**
     * take a vehicle from the station if possible
     * @param t - The vehicle
     * @return - The vehicle rented
     * @throws NoVehicleOfThisTypeAvailableException - if there is no vehicle of this type
     * @throws StationEmptyException - If the station is empty
     */
    public Vehicle rentVehicle(TypeVehicleTest t) throws NoVehicleOfThisTypeAvailableException, StationEmptyException {

        if(this.canBeRent()){
            for (Vehicle vehicle : this.getVehicles()) {
                if (t.testTypeVehicle(vehicle) && vehicle.isRentable()) {
                    return removeVehicleFromStation(vehicle);
                }
            }
            throw new NoVehicleOfThisTypeAvailableException("No vehicle of this type in the station, rentVehicle cancel");
        }
        throw new StationEmptyException("Station is empty,rentVehicle cancel");
    }

    /**
     * methode that remove a vehicle from a station and notify is the remove and updateState and the time
     * @param vehicle the vehicle to remove
     * @return the vehicle
     */
    private Vehicle removeVehicleFromStation(Vehicle vehicle) {
        this.subsribers.forEach(c -> c.removeVehicleList(vehicle));
        this.getVehicles().remove(vehicle);
        this.subsribers.forEach(x -> x.notifyStationVehicleTaked(this));
        this.updateStateStation();
        this.resetTimeSation();
        return vehicle;
    }

    /**
     * Accept or reject visitors to the station
     * @param stationVisitor - Visitor type
     * @throws Exception - Exception
     */
    public void accept(StationVisitor stationVisitor) throws Exception {
        stationVisitor.visit(this);
    }

    /**
     * Change station state to empty
     */
    private void toEmpty() {
        this.stateStation.toEmpty();
    }

    /**
     * Changes station state to Normal
     */
    private void toNormal() {
        this.stateStation.toNormal();
    }

    /**
     * Changes station vehicle to full
     */
    private void toFull() {
        this.stateStation.toFull();
    }


    /**
     * can the station accommodate vehicle ?
     * @return boolean - true or false
     */
    public boolean canBeDropOff() {
        return this.stateStation.canBeDropOff();
    }

    /**
     * can the station rent any type of vehicle
     * @return boolean - true or false
     */
    public boolean canBeRent() {
        return this.stateStation.canBeRent();
    }

    /**
     * Get vehicles list
     * @return the list vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }


    /**
     * Add a subscriber to the station
     * @param controlCenter a subscriber
     */
    public void addSubscriber(ControlCenter controlCenter) {
        this.subsribers.add(controlCenter);
    }

    /**
     * Remove a subscriber from the station
     * @param controlCenter a subscriber
     */
    public void removeSubscriber(ControlCenter controlCenter) {
        this.subsribers.remove(controlCenter);
    }

    /**
     * Get the vehicle
     * @return the vehicle
     */
    public Vehicle getVehicle(){
        return this.vehicles.getFirst();
    }

    /**
     * update the state of the station after every time a vehicle is taken or dropped
     */
    protected void updateStateStation(){
        if(this.vehicles.size() <= 0){
            this.toEmpty();
            this.subsribers.forEach(t -> t.notifyStationEmpty(this));

        } else if (this.vehicles.size() >= this.capacityMax) {
            this.toFull();
            this.subsribers.forEach(t -> t.notifyStationFull(this));
        }
        else{
            this.toNormal();
        }
    }

    /**
     * setter for the state of the station
     * @param s the station
     */
    public void setStateStation(StateStation s){
        this.stateStation = s;
    }

    /**
     * getter  for the state of the station
     * @return the state on the station
     */
    public StateStation getStateStation() {
        return stateStation;
    }

    /**
     * getter for the list of control center
     * @return the list of control center
     */
    public List<ControlCenter> getSubsribers() {
        return subsribers;
    }

    @Override
    public void updateTime() {

    }

    /**
     * reset the time of the station
     */
    private void resetTimeSation(){
        this.getTime().resetCount();
    }

    /**
     * getter for the station id
     * @return an id
     */
    public int getId() {
        return id;
    }
}
