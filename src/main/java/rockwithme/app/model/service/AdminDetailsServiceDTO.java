package rockwithme.app.model.service;

public class AdminDetailsServiceDTO {
    private int registeredUsers;
    private UserAdminServiceDTO lastRegisteredUser;
    private int activeBands;
    private int deletedBands;
    private BandAdminServiceDTO lastRegisteredBand;

    public AdminDetailsServiceDTO() {
    }

    public int getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(int registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public UserAdminServiceDTO getLastRegisteredUser() {
        return lastRegisteredUser;
    }

    public void setLastRegisteredUser(UserAdminServiceDTO lastRegisteredUser) {
        this.lastRegisteredUser = lastRegisteredUser;
    }

    public int getActiveBands() {
        return activeBands;
    }

    public void setActiveBands(int activeBands) {
        this.activeBands = activeBands;
    }

    public int getDeletedBands() {
        return deletedBands;
    }

    public void setDeletedBands(int deletedBands) {
        this.deletedBands = deletedBands;
    }

    public BandAdminServiceDTO getLastRegisteredBand() {
        return lastRegisteredBand;
    }

    public void setLastRegisteredBand(BandAdminServiceDTO lastRegisteredBand) {
        this.lastRegisteredBand = lastRegisteredBand;
    }
}
