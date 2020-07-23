package rockwithme.app.exeption;

public class NotRequiredSkills extends RuntimeException{
    public NotRequiredSkills() {
    }

    public NotRequiredSkills(String message) {
        super(message);
    }
}
