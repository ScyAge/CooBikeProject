import controlCenter.ControlCenter;
import displayer.ConsoleDisplayer;
import displayer.DisplayerInterface;
import exeption.NoVehicleOfThisTypeAvailableException;
import exeption.StationEmptyException;
import exeption.StationFullException;
import listchooser.ListChooser;
import listchooser.RandomListChooser;
import listchooser.RandomSetChooser;
import listchooser.SetChooser;
import station.Station;
import station.clientStation.AbstractClientStation;
import station.clientStation.BikeVisitor;
import station.clientStation.OverBoardVisitor;
import station.clientStation.ScooterVisitor;
import station.stationVisitor.Rober;
import station.stationVisitor.StationVisitor;
import timeControler.TimeDependencies;
import vehicle.Overboard;
import vehicle.Scooter;
import vehicle.Vehicle;
import vehicle.vehicleCreator.BikeCreator;
import vehicle.vehicleCreator.OverboardCreator;
import vehicle.vehicleCreator.ScooterCreator;
import vehicle.vehicleVisitor.Repair;
import vehicle.vehicleVisitor.VehicleVisitor;

import java.util.*;

/**
 * Class main
 */
public class Main {

    private static final HashSet<Vehicle> allVec = new HashSet<>();
    private static final BikeCreator bc = new BikeCreator();
    private static final OverboardCreator oc = new OverboardCreator();
    private static final ScooterCreator sc = new ScooterCreator();
    private static final ControlCenter mainControlCenter = new ControlCenter(allVec);
    private static final StationVisitor rober = new Rober();
    private static final List<VehicleVisitor> workerVec = new ArrayList<>();
    private static final List<Vehicle> rentedVec = new ArrayList<>();
    private static ListChooser<Vehicle> listChooserVehicle = new RandomListChooser<>();
    private static SetChooser<Station> stationSetChooser = new RandomSetChooser<>();
    private static Random random = new Random();
    private static DisplayerInterface displayer = new ConsoleDisplayer();
    private static List<AbstractClientStation> clientStations = new ArrayList<>();
    private static List<TimeDependencies> timeDependencies = new ArrayList<>();

    /**
     * The cycle depot tour
     * @throws StationFullException - if the station is full
     */
    private static void roundDropOff() throws StationFullException {
        if(!rentedVec.isEmpty()){
            int nbDropOff = random.nextInt(1,rentedVec.size()+1);
            for (int i=0; i<nbDropOff; i++) {
                dropVehicleStationAlea();
            }
        }
        else {
            System.out.println("Aucun véhicule n'a été loué, donc aucun a été deposé !");
            System.out.println();
        }
    }

    /**
     * The tour to rent bikes
     */
    private static void dropVehicleStationAlea() {
        try {
            Vehicle vehicle = listChooserVehicle.choose(rentedVec);
            Station station = stationSetChooser.choose(mainControlCenter.getStations().keySet());
            station.dropOffVehicle(vehicle);
            rentedVec.remove(vehicle);
            displayer.displayDropOffVehicleInformaion(station, vehicle);
            System.out.println();
        } catch (StationFullException e) {
            displayer.displayExeption(e.getMessage());
        }

    }

    /**
     * The trick to update the behavior
     */
    private static void roundRent() {
        int nbRend = random.nextInt(0, 11);
        for (int i=0; i<nbRend;i++) {
            rentVehicleStationAlea();
        }

    }

    /**
     * rents a vehicle randomly
     */
    private static void rentVehicleStationAlea() {
        ListChooser<AbstractClientStation> abstractClientStationListChooser = new RandomListChooser<>();
        Station station = stationSetChooser.choose(mainControlCenter.getStations().keySet());
        Vehicle vehicle = abstractClientStationListChooser.choose(clientStations).visit(station);
        if (vehicle != null) {
            rentedVec.add(vehicle);
            displayer.displayRentVehicleInformaion(station, vehicle);
            System.out.println();
        }
    }

    /**
     * Update the round
     */
    private static void roundUpdate(){
        timeDependencies.forEach(t -> t.getTime().addOneInterValeNoModif());
        timeDependencies.forEach(t -> t.updateTime());
        mainControlCenter.executeStrategyRedistribution();
        mainControlCenter.executeEventStation(rober);
        workerVec.forEach(t -> mainControlCenter.executeEventVehicle(t));
        displayer.displayControlCenter(mainControlCenter);
    }

    /**
     * The execute main
     * @param arg - arg
     * @throws StationFullException - Exception if the station is full
     */
    public static void main(String[] arg) throws StationFullException {

        //creation of 30 vehicle
        for(int i=0; i<10;i++){
            allVec.add(bc.createVehicle());
        }
        for(int i=0; i<10;i++){
            allVec.add(oc.createVehicle());
        }
        for(int i=0; i<10;i++){
            allVec.add(sc.createVehicle());
        }

        //creation de 10 Station
        List<Station> allStation = new ArrayList<>();
        for(int i=0; i<10;i++){
            allStation.add(new Station());
        }

        //add subscribe control center to them
        allStation.forEach(t -> t.addSubscriber(mainControlCenter));


        //add repairer
        for(int i=0; i<5;i++){
            workerVec.add(new Repair(mainControlCenter));
        }

        Iterator<Vehicle> x = allVec.iterator();
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 3;j++){
                if(x.hasNext()){
                    allStation.get(i).dropOffVehicle(x.next());
                }
            }
        }

        clientStations.add(new ScooterVisitor());
        clientStations.add(new OverBoardVisitor());
        clientStations.add(new BikeVisitor());

        timeDependencies.addAll(allStation);
        workerVec.forEach(t -> {
            if (t instanceof Repair)
                timeDependencies.add((TimeDependencies) t);
        });
        System.out.println(timeDependencies);


        int nb_tour = 0;
        while(nb_tour < 10) {
            System.out.println();
            System.out.println("Tour n°" + nb_tour);
            displayer.displayControlCenter(mainControlCenter);
            roundDropOff();
            roundRent();
            roundUpdate();
            displayer.displayControlCenter(mainControlCenter);
            nb_tour++;
        }
        System.out.println("Station pleine, cheh, volez le vélo de Monsieur Quinton et deposez le votre il est meilleur !");
    }
}
