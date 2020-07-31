package rockwithme.app.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OnInitPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public OnInitPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishInit() {
        OnInitEvent onInitEvent = new OnInitEvent(this);
        this.applicationEventPublisher.publishEvent(onInitEvent);
    }
}
