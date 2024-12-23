package station.clientStation;

import displayer.ConsoleDisplayer;
import displayer.DisplayerInterface;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import station.Station;
import vehicle.Vehicle;

/**
 * The abstract client station class
 */
public abstract class AbstractClientStation {


    private final DisplayerInterface diplayer = new ConsoleDisplayer();
    /**
     * visit a station a take a vehicle of the type wanted
     * @param station the station selected to take a vehicle
     * @return Vehicle - The vehicle visit if is ok else return null
     */
    public Vehicle visit(Station station) {
        try {
            return station.rentVehicle(this.testMethod());
        } catch (NoVehicleOfThisTypeAvailableException | StationEmptyException e) {
            diplayer.displayExeption(e.getMessage());
        }
        return null;
    }

    /**
     * methat which return a TypeVehicleTest
     * @return an object of type TypeVehicleTest which is an anonyme interface
     */
    public abstract TypeVehicleTest testMethod();
}
