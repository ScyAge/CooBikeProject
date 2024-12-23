package controlCenter.redistibutionStrategy;

import controlCenter.ControlCenter;
import station.Station;
import station.stationVisitor.StationVisitor;

public class MockControlCenterTestFilterStation extends ControlCenter {

    public static Station stationGet= null;

    @Override
    protected Station getStationCorrepondingFilter(StationVisitor visitor) throws NullPointerException {
        stationGet = super.getStationCorrepondingFilter(visitor);
        return stationGet;
    }
}
