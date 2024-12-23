package station.stationVisitor;

import displayer.ConsoleDisplayer;
import displayer.DisplayerInterface;
import exeption.CantBeRobeException;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import station.Station;
import vehicle.Vehicle;

/**
 * Visor Thief Class
 */
public class Rober implements StationVisitor {

     private final DisplayerInterface displayerInterface = new ConsoleDisplayer();
     private static final int NB_VEHICLE_NEEDED_TO_ROBE = 1;
    /**
     * Rober's constructor
     */
    public Rober() {

    }

    /**
     * method which allows you to visit the station
     * @param station - The station visited
     * @throws Exception - Exception
     */
    @Override
    public void visit(Station station) throws Exception {
        robStation(station);
    }

    /**
     * Steal the vehicle from the station
     * @param station - The stolen station
     * @throws CantBeRobeException if the vehicle in station is HS
     * @throws StationEmptyException if the station isEmpty can't append because of the filter
     * @throws NoVehicleOfThisTypeAvailableException can't append cause we took any type of vehicle
     */
    private void robStation(Station station) throws CantBeRobeException, StationEmptyException, NoVehicleOfThisTypeAvailableException {
        if(this.canBeRobe(station)){
            Vehicle vec = station.getVehicle();
            station.rentVehicle(t -> t instanceof Vehicle);
            vec.toRobed();
            displayerInterface.displayStationVisitor(this,station);

        }
        else{
            throw new CantBeRobeException("This vehicle is not stealable cause he is HS");
        }
    }

    /**
     * verify if the vehicle is not HS
     * @param s a station
     * @return a boolean
     */
    public boolean canBeRobe(Station s){
        return  s.getVehicle().isRentable();
    }

    @Override
    public boolean predicatTestFilter(Station s) {
        return s.getVehicles().size() == NB_VEHICLE_NEEDED_TO_ROBE && s.getTime().intervalNoModifSupEqHas(2);
    }
}
