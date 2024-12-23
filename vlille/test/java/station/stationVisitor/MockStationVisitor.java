package station.stationVisitor;

import station.Station;

public class MockStationVisitor implements StationVisitor{

    @Override
    public void visit(Station station) throws Exception {
        if(control(station)){
            station.getVehicle().toHS();
        }
        else{
            throw  new Exception("ne peut rien faire");
        }
    }

    private boolean control(Station s){
        return s.getVehicles().size() <3;
    }

    @Override
    public boolean predicatTestFilter(Station s) {
        return s.getVehicles().size() >=2;
    }
}
