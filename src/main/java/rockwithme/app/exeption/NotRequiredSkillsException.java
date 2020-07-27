package rockwithme.app.exeption;

public class NotRequiredSkillsException extends RuntimeException{
    public NotRequiredSkillsException() {
    }

    public NotRequiredSkillsException(String message) {
        super(message);
    }
}
