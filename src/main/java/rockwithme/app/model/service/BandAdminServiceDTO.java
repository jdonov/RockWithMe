package rockwithme.app.model.service;

import java.time.LocalDateTime;

public class BandAdminServiceDTO extends BaseServiceModel{
    private String name;
    private LocalDateTime registrationDate;

    public BandAdminServiceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
