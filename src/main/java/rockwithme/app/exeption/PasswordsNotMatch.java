package rockwithme.app.exeption;

public class PasswordsNotMatch extends RuntimeException {
    public PasswordsNotMatch() {
    }

    public PasswordsNotMatch(String message) {
        super(message);
    }
}
