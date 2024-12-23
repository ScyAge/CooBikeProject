package controlCenter;

import controlCenter.redistibutionStrategy.RedistributionRobin;
import controlCenter.redistibutionStrategy.RedistributionStrategy;
import displayer.ConsoleDisplayer;
import displayer.DisplayerInterface;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import station.Station;
import station.stateStation.Empty;
import station.stateStation.Full;
import station.stationVisitor.StationVisitor;
import vehicle.Vehicle;
import vehicle.vehicleVisitor.VehicleVisitor;

import java.util.*;

/**
 * Control center classControl center class
 */
public class ControlCenter implements SubscribeControlCenter {

    private Map<Station, Integer> stations;
    private Set<Vehicle> vehicles;
    private RedistributionStrategy strategy;
    private List<Station> stationToRedistribute;
    private final DisplayerInterface displayer= new ConsoleDisplayer();
    /**
     * ControlCenter's constructor
     */
    public ControlCenter() {
        this.stations = new HashMap<>();
        this.vehicles = new HashSet<>();
        this.strategy = new RedistributionRobin();
        this.stationToRedistribute = new ArrayList<>();
    }

    /**
     * ControlCenter's constructor with a non-empty list en predefine station
     * @param s - The list station
     */
    public ControlCenter(List<Station> s) {
        this.stations = new HashMap<>();
        this.vehicles = new HashSet<>();
        this.strategy = new RedistributionRobin();
        this.stationToRedistribute = new ArrayList<>();
        if (!s.isEmpty()) {
            s.forEach(t -> this.stations.put(t, t.getVehicles().size()));
            s.forEach(t -> {
                if ((t.getStateStation() instanceof Full) || (t.getStateStation() instanceof Empty)) {
                    this.stationToRedistribute.add(t);
                }
                t.addSubscriber(this);
            });
        }
    }

    /**
     * The constructor ControlCenter
     * @param v - The List of vehicules
     */
    public ControlCenter(Set<Vehicle> v) {
        this.stations = new HashMap<>();
        this.vehicles = new HashSet<>();
        this.strategy = new RedistributionRobin();
        this.stationToRedistribute = new ArrayList<>();
        this.stations = new HashMap<>();
        if (!v.isEmpty())
            this.vehicles.addAll(v);
    }

    /**
     * Add the vehicle on the list
     * @param vehicle - The added vehicle
     */
    public void addVehicleList(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    /**
     * Remove the vehicle on the list
     * @param vehicle - The removed vehicle
     */
    public void removeVehicleList(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    /**
     * Get the vehicles list
     * @return - the vehicle list
     */
    public Set<Vehicle> getVehicles() {
        return this.vehicles;
    }

    /**
     * Change strategy
     *
     * @param strategy - new Strategy
     */
    public void setStrategy(RedistributionStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Execute the strategy on one station
     * @param station station to be redistributed
     * @throws StationFullException - exception StationFullException
     * @throws StationEmptyException - exception StationEmptyException
     * @throws NoVehicleOfThisTypeAvailableException - exception NoVehicleOfThisTypeAvailableException
     */
    public void executeStrategyOnStation(Station station) throws StationFullException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        displayer.displayRedistributionInit(station);
        this.strategy.reallocation(stations.keySet(), station);
        displayer.displayRedistributionEnd(station);
    }

    /**
     * execute the redistribution on all the vehicle which can be redistribuate
     */
    public void executeStrategyRedistribution(){
        try {
            List<Station> stationRedistibutionAvailable = this.stationToRedistribute.stream().filter(t -> t.getTime().intervalNoModifSupEqHas(2)).toList();
            while (!stationRedistibutionAvailable.isEmpty()) {
                Station s = stationRedistibutionAvailable.getFirst();
                this.executeStrategyOnStation(s);
                stationRedistibutionAvailable = this.stationToRedistribute.stream().filter(t -> t.getTime().intervalNoModifSupEqHas(2)).toList();
            }
        }
        catch (Exception e){
            displayer.displayExeption(e.getMessage());
        }
    }

    /**
     * Get number of vehicle to parameter station
     *
     * @param station - the station
     * @return nb vehicle in this station
     */
    public int getNbVehicleStation(Station station) {
        return stations.get(station);
    }

    /**
     * Get map stations
     *
     * @return the map of all stations
     */
    public Map<Station, Integer> getStations() {
        return this.stations;
    }

    @Override
    public void notifyStationEmpty(Station s) {
        this.stationToRedistribute.add(s);
    }

    @Override
    public void notifyStationFull(Station s) {
        this.stationToRedistribute.add(s);
    }

    @Override
    public void notifyStationVehicleAdded(Station s) {
        if (this.stations.containsKey(s)) {
            this.stations.put(s, this.stations.get(s) + 1);
        } else {
            this.stations.put(s, s.getVehicles().size());
        }
        this.refreshListRedistribute(s);
    }

    @Override
    public void notifyStationVehicleTaked(Station s) {
        if (this.stations.containsKey(s)) {
            this.stations.put(s, this.stations.get(s) - 1);
        } else {
            this.stations.put(s, s.getVehicles().size());
        }
        this.refreshListRedistribute(s);
    }

    /**
     * method that remove the station in list station to redistribute because she can't be redistribute anymore
     * @param s - the station
     */
    private void refreshListRedistribute(Station s) {
        this.stationToRedistribute.remove(s);
    }

    /**
     * return the list of the vehicle who potentialy need to be redistributed
     * @return the list station to redistribuate
     */
    public List<Station> getStationToRedistribute() {
        return stationToRedistribute;
    }

    /**
     * Executes a vehicle-related event defined by the provided visitor.
     * @param vehicleVisitor the visitor defining the vehicle event
     */
    public void executeEventVehicle(VehicleVisitor vehicleVisitor) {
        try {
            Vehicle vehicle = vehicleFilterCondition(vehicleVisitor);
            vehicleVisitor.visit(vehicle);
        } catch (NullPointerException e) {

        } catch (Exception e) {
            displayer.displayExeption(e.getMessage());
        }
    }

    /**
     * Filters the first vehicle that matches the visitor's condition.
     * @param vehicleVisitor the visitor with the filter condition
     * @return a vehicle matching the condition
     * @throws NullPointerException if no vehicle matches the condition
     */
    protected Vehicle vehicleFilterCondition(VehicleVisitor vehicleVisitor) throws NullPointerException {
        Iterator<Vehicle> vehicleIterator = this.vehicles.iterator();
        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();
            if (vehicleVisitor.filterCondition(vehicle)) {
                return vehicle;
            };
        }
        throw new NullPointerException("No vehicle correspond to the filter");
    }


    /**
     * method which exexute an event of type Station visitor
     * @param v a visitor of station
     */
    public void executeEventStation(StationVisitor v){
        try{
            Station station = this.getStationCorrepondingFilter(v);
            v.visit(station);
        }
        catch (NullPointerException e){

        }
        catch (Exception e){
            displayer.displayExeption(e.getMessage());
        }
    }

    /**
     * extract the first station which correspond to StationVisitor predicate
     * @param visitor the station visitor
     * @return  a station corresponding to the predicate
     * @throws NullPointerException if there is no station corresponding
     */
    protected Station getStationCorrepondingFilter(StationVisitor visitor) throws NullPointerException{
        Iterator<Station> t = this.getStations().keySet().iterator();

        while(t.hasNext()){
            Station station = t.next();
            if(visitor.predicatTestFilter(station)){
                return station;
            }
        }
        throw new NullPointerException("aucune station ne corréspond au filtre");
    }
}