package rockwithme.app.scheduller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rockwithme.app.service.BandService;

@Component
public class DeleteBandsWithoutMembers {

    private final BandService bandService;

    public DeleteBandsWithoutMembers(BandService bandService) {
        this.bandService = bandService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 */1 * ? * *")
    private void deleteBandsWithoutMembers() {
        this.bandService.findAllToDelete().forEach(b -> System.out.println(b.getName()));
        this.bandService.deleteBandsWithNoMembers();
    }
}
