package vehicle.vehicleVisitor;

import controlCenter.ControlCenter;
import displayer.ConsoleDisplayer;
import displayer.DisplayerInterface;
import timeControler.Time;
import timeControler.TimeDependencies;
import vehicle.Bike;
import vehicle.Overboard;
import vehicle.Scooter;
import vehicle.Vehicle;
import vehicle.stateVehicle.HS;
import vehicle.stateVehicle.Service;

/**
 * class Repair
 */
public class Repair extends TimeDependencies implements VehicleVisitor {

    private ControlCenter controlCenter;
    private Vehicle vehicle = null;
    private final DisplayerInterface displayerInterface = new ConsoleDisplayer();
    /**
     * Constructor repair
     * @param controlCenter - controlCenter
     */
    public Repair(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
    }

    /**
     * Get the attribute vehicle
     * @return the vehicle attribute
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /** method that visit a vehicle
     * @param vehicle the vehicle to visit
     * @throws Exception - If the repair is working
     */
    public void visitBis(Vehicle vehicle) throws Exception {
        if (canWork()) {
            this.controlCenter.removeVehicleList(vehicle);
            this.vehicle = vehicle;
        }
        else
            throw new Exception("Can't repair this bike, because is already working on a vehicle !");
    }

    @Override
    public void visit(Vehicle vehicle) throws Exception {
        vehicle.accept(this);
    }

    @Override
    public void visit(Bike b) throws Exception {
        this.visitBis(b);
    }

    @Override
    public void visit(Scooter b) throws Exception {
        this.visitBis(b);
    }

    @Override
    public void visit(Overboard b) throws Exception {
        this.visitBis(b);
    }

    /** method that check if the vehicle is in the HS state
     * @param vehicle the vehicle to check
     * @return true if the vehicle is in the HS state, false otherwise
     */
    @Override
    public boolean filterCondition(Vehicle vehicle) {
        return vehicle.getState().getClass() == HS.class;
    }

    /** method that check if the repair can work
     * @return true if the repair can work, false otherwise
     */
    private boolean canWork() {
        return this.vehicle == null;
    }


    /**
     * method that repair a vehicle who is the HS State
     */
    private void repairVehicle() {
        controlCenter.addVehicleList(this.vehicle);
        vehicle.setState(new Service(this.vehicle));
        displayerInterface.displayVehicleVisitor(this,vehicle);
        this.vehicle = null;
        this.getTime().resetCount();

    }

    @Override
    public void updateTime() {
        if (!canWork()) {
            // this.getTime().addOneInterValeNoModif();
            if (this.getTime().intervalNoModifSupEqHas(2)) {
                repairVehicle();
            }
        }
    }
}
