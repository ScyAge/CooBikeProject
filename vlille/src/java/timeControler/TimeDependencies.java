package timeControler;


/**
 * Class for the Object depended to time
 */
public abstract class TimeDependencies {

    private final Time time = new Time();

    /**
     * method that force Objet dependent to time to define a method to update time each interval
     */
    public abstract void updateTime();

    /**
     * the time associate to Time dependent object
     * @return a Time object
     */
    public Time getTime() {
        return this.time;
    }
}
