package rockwithme.app.exeption;

public class UserWithoutRolesException extends RuntimeException{

    public UserWithoutRolesException(String message) {
        super(message);
    }
}
