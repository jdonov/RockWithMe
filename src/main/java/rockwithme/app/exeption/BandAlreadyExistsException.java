package rockwithme.app.exeption;

public class BandAlreadyExistsException extends RuntimeException{
    public BandAlreadyExistsException(String message) {
        super(message);
    }
}
