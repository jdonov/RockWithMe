package rockwithme.app.exeption;

public class UserWithoutRolesException extends UserRoleException{

    public UserWithoutRolesException(String message) {
        super(message);
    }
}
