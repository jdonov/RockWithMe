package rockwithme.app.exeption;

public class PasswordsNotMatchException extends RuntimeException {
    public PasswordsNotMatchException() {
    }

    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
