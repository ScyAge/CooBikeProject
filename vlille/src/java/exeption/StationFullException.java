package exeption;

/**
 * Class StationFullException
 */
public class StationFullException extends Exception{
    public StationFullException(String stationIsFull) {
        super(stationIsFull);
    }
}
