package rockwithme.app.exeption;

public class NoRegisteredSkills extends RuntimeException{
    public NoRegisteredSkills(String message) {
        super(message);
    }
}
