package timeControler;

import station.Station;

/**
 * Class Time
 */
public class Time {

    private int interValeNoModif;

    /**
     * constructor for the class time
     */
    public Time(){
        this.interValeNoModif = 0;
    }

    /**
     * getter for the interval attribute
     * @return the value of the interval
     */
    public int getInterValeNoModif() {
        return interValeNoModif;
    }

    /**
     * reset the counter of interval noModif to 0
     */
    public void resetCount(){
        this.interValeNoModif =0;
    }

    /**
     * test if intervalNoModif is higher or equals to i
     * @param i the value which intervalModif is compare to
     * @return a boolean
     */
    public boolean intervalNoModifSupEqHas(int i){
        return interValeNoModif >= i;
    }

    /**
     * add one to the Intervall no modif
     */
    public void addOneInterValeNoModif() {
        this.interValeNoModif++ ;
    }


}
