package rockwithme.app.scheduller;

import org.springframework.context.ApplicationEvent;

public class OnInitEvent extends ApplicationEvent {
    public OnInitEvent(Object source) {
        super(source);
    }
}
