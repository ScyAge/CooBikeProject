package displayer;

import controlCenter.ControlCenter;
import station.Station;

import de.vandermeer.asciitable.AsciiTable;
import station.stationVisitor.StationVisitor;
import vehicle.Vehicle;
import vehicle.stateVehicle.HS;
import vehicle.stateVehicle.Service;
import vehicle.vehicleVisitor.VehicleVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Class displayer console
 */
public class ConsoleDisplayer implements DisplayerInterface{

    @Override
    public void displayControlCenter(ControlCenter c) {
        System.out.println("Voici l'état des information que possède le control center");
        tabGeneratorContro(c);
        System.out.println();

    }

    /**
     * generate a tab of information about the control center
     * @param c the control center
     */
    private static void tabGeneratorContro(ControlCenter c) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Num Station","NbVehicleTot","NbVehicleAvailable","NbVehicleHS");
        table.addRule();
        c.getStations().keySet().forEach(t -> {
            int nb_vec_serv = t.getVehicles().stream()
                    .filter(v -> v.getState() instanceof Service)
                    .map(n -> 1)
                    .reduce(0, Integer::sum);
            int nb_vec_HS = t.getVehicles().stream()
                    .filter(v -> v.getState() instanceof HS)
                    .map(n -> 1)
                    .reduce(0, Integer::sum);

            table.addRow(t.getId(), c.getStations().get(t),nb_vec_serv,nb_vec_HS);

        });
        table.addRule();
        System.out.printf(table.render());
    }

    @Override
    public void displayStation(Station s) {
        Map<Class<?>,Integer> test = new HashMap<>();
        s.getVehicles().forEach(vehicle -> {
            if(test.containsKey(vehicle.getClass())){
                test.put(vehicle.getClass(),test.get(vehicle.getClass())+1);
            }
            else{
                test.put(vehicle.getClass(),1);
            }
        });

        System.out.printf("Station id %d, information : \n",s.getId());
        createTabStation(s, test);
        System.out.println();

    }

    /**
     * create the tab of information in a station
     * @param s the station
     * @param mapType  map with all the vehicle type present in a station
     */
    private static void createTabStation(Station s, Map<Class<?>, Integer> mapType) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Vehicle type","number","number available","number HS");
        table.addRule();
        mapType.keySet().forEach(type -> {
            int nb_vec_serv = s.getVehicles().stream()
                    .filter(v -> v.getClass() == type)
                    .filter(v -> v.getState() instanceof Service)
                    .map(n -> 1)
                    .reduce(0, Integer::sum);
            int nb_vec_HS = s.getVehicles().stream()
                    .filter(v -> v.getClass() == type)
                    .filter(v -> v.getState() instanceof HS)
                    .map(n -> 1)
                    .reduce(0, Integer::sum);

            table.addRow(type, mapType.get(type),nb_vec_serv,nb_vec_HS);

        });
        table.addRule();
        System.out.printf(table.render());
    }


    @Override
    public void displayStationVisitor(StationVisitor visitor,Station station) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%s as do is action on Station, id : %d\n",visitor.getClass().getSimpleName(),station.getId());
        System.out.println("---------------------------------------------------------------");
    }

    @Override
    public void displayDropOffVehicleInformaion(Station station, Vehicle vehicle) {
        displayVehicle(vehicle);
        System.out.println("Ce véhicule a été déposé à la station " + station.getId());
    }

    @Override
    public void displayRentVehicleInformaion(Station station, Vehicle vehicle) {
        displayVehicle(vehicle);
        System.out.println("Ce véhicule a été loué à la station " + station.getId());
    }

    @Override
    public void displayExeption(String message) {
        System.out.printf("%s : l'action a été annuler\n",message);
        System.out.println();
    }

    @Override
    public void displayRedistributionInit(Station s) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("-> The station number %d, going to be redistributed\n",s.getId());
        System.out.println("Before redistribution");
        this.displayStation(s);
        System.out.println("---------------------------------------------------------------");
    }

    @Override
    public void displayRedistributionEnd(Station s) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("-> The station number %d, has been redistribute\n",s.getId());
        System.out.println("after redistribution");
        this.displayStation(s);
        System.out.println("---------------------------------------------------------------");
    }

    @Override
    public void displayVehicle(Vehicle v) {
        System.out.printf("->Vehicle number : %d information : %s\n",v.getId(),v.decorateEquipment());
    }

    @Override
    public void displayVehicleVisitor(VehicleVisitor visitor,Vehicle vehicle) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%s as do is action on %s, id : %d\n",visitor.getClass().getSimpleName(),vehicle.decorateEquipment(),vehicle.getId());
        System.out.println("---------------------------------------------------------------");
    }
}
