package rockwithme.app.scheduller;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rockwithme.app.event.OnInitEvent;
import rockwithme.app.model.service.BandOfTheWeekServiceDTO;
import rockwithme.app.service.BandService;

@Component
public class BandOfTheWeek implements ApplicationListener<ApplicationStartedEvent> {
    private BandOfTheWeekServiceDTO bandOfTheWeekServiceDTO;
    private final BandService bandService;

    public BandOfTheWeek(BandService bandService) {
        this.bandService = bandService;
    }

    public BandOfTheWeekServiceDTO getBandOfTheWeekServiceDTO() {
        return bandOfTheWeekServiceDTO;
    }

    public void setBandOfTheWeekServiceDTO(BandOfTheWeekServiceDTO bandOfTheWeekServiceDTO) {
        this.bandOfTheWeekServiceDTO = bandOfTheWeekServiceDTO;
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void bandOfTheWeek() {
        this.bandOfTheWeekServiceDTO = this.bandService.getBandOfTheWeek();
    }

    @EventListener(OnInitEvent.class)
    public void setBandOfTheWeek() {
        this.bandOfTheWeekServiceDTO = this.bandService.getBandOfTheWeek();
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        this.bandOfTheWeekServiceDTO = this.bandService.getBandOfTheWeek();
    }
}
