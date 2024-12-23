package station.stateStation;

import station.Station;

/**
 * Class normal extend state station
 */
public class Normal extends StateStation {

    /**
     * Constructor Normal state
     * @param s - the station state
     */
    public Normal(Station s){
        super(s);
    }


    @Override
    public void toEmpty() {
        this.s.setStateStation(new Empty(this.s));
    }


    @Override
    public void toNormal() {

    }

    @Override
    public void toFull() {
        this.s.setStateStation(new Full(this.s));
    }


    @Override
    public boolean canBeRent() {
        return true;
    }

    @Override
    public boolean canBeDropOff() {
        return true;
    }
}
