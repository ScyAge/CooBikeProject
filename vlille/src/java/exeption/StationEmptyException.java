package exeption;

/**
 * Class StationEmptyException
 */
public class StationEmptyException extends Exception{
    public StationEmptyException(String stationIsEmpty) {
        super(stationIsEmpty);
    }
}
