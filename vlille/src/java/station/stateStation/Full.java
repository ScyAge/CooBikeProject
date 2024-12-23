package station.stateStation;

import station.Station;

/**
 * Class full state
 */
public class Full extends StateStation {

    /**
     * Constructor full state
     * @param s - the station state
     */
    public Full(Station s){
        super(s);
    }

    @Override
    public void toEmpty() {

    }


    @Override
    public void toNormal() {
        this.s.setStateStation(new Normal(this.s));
    }

    @Override
    public void toFull() {

    }

    @Override
    public boolean canBeRent() {
        return true;
    }

    @Override
    public boolean canBeDropOff() {
        return false;
    }
}
