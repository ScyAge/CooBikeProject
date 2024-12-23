package controlCenter;

import station.Station;
import station.stationVisitor.StationVisitor;
import vehicle.Vehicle;
import vehicle.vehicleVisitor.VehicleVisitor;

public class MockControlCenterTestExecuteThowsVecAndStation extends ControlCenter{


    public int call_Null_point = 0;
    public int call_Exep_point = 0;

    @Override
    public void executeEventVehicle(VehicleVisitor vehicleVisitor) {
        try {
            Vehicle vehicle = super.vehicleFilterCondition(vehicleVisitor);
            vehicleVisitor.visit(vehicle);
        } catch (NullPointerException e) {
            call_Null_point++;
        } catch (Exception e) {
            call_Exep_point++;
        }
    }

    @Override
    public void executeEventStation(StationVisitor stationVisitor){
        try {
            Station station = super.getStationCorrepondingFilter(stationVisitor);
            stationVisitor.visit(station);
        } catch (NullPointerException e) {
            call_Null_point++;
        } catch (Exception e) {
            call_Exep_point++;
        }
    }
}
