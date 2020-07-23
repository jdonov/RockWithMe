package rockwithme.app.model.binding;
import rockwithme.app.model.entity.Town;

import java.util.Set;

public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String username;
    private Town town;
    private int age;
    private String imgUrl;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String firstName, String lastName, String username, Town town, int age, String imgUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.town = town;
        this.age = age;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
