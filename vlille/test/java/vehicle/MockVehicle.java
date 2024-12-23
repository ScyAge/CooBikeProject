package vehicle;

import vehicle.stateVehicle.Service;
import vehicle.vehicleVisitor.VehicleVisitor;

public class MockVehicle extends Vehicle{




    public MockVehicle(int id) {
        super(id);
    }

    @Override
    public String decorateEquipment() {
        return "";
    }

    @Override
    public void accept(VehicleVisitor visitor) throws Exception {

    }

    @Override
    protected int nbMaxTimeRented() {
        return 1;
    }





}
