package rockwithme.app.model.service;

import rockwithme.app.model.entity.Town;
import java.util.Set;

public class UserPublicDetailsServiceDTO extends BaseServiceModel{
    private String firstName;
    private String lastName;
    private String username;
    private Town town;
    private int age;
    private Set<BandUserBandsServiceDTO> bands;
    private String imgUrl;

    public UserPublicDetailsServiceDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<BandUserBandsServiceDTO> getBands() {
        return bands;
    }

    public void setBands(Set<BandUserBandsServiceDTO> bands) {
        this.bands = bands;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
