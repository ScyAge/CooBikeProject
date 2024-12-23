package station;

import exeption.StationFullException;
import station.clientStation.TypeVehicleTest;
import vehicle.Vehicle;

public class MockStationTestNotif extends Station{

    public static final int capacity = 2;

    public MockStationTestNotif(){
        super();

    }

    protected int randomCapacityMax() {
        return capacity;
    }

    @Override
    public void dropOffVehicle(Vehicle vehicle) throws StationFullException {
        this.vehicles.add(vehicle);
        this.subsribers.forEach(t -> t.notifyStationVehicleAdded(this));
        this.updateStateStation();
    }



    @Override
    public Vehicle rentVehicle(TypeVehicleTest t) {
        this.vehicles.removeFirst();
        this.subsribers.forEach(x -> x.notifyStationVehicleTaked(this));
        this.updateStateStation();

        return null;
    }
}

