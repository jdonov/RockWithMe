package rockwithme.app.model.binding;
import rockwithme.app.constraint.EnumValue;
import rockwithme.app.model.entity.Town;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Set;

public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String town;
    private int age;
    private String imgUrl;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String firstName, String lastName, String username, String town, int age, String imgUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.town = town;
        this.age = age;
        this.imgUrl = imgUrl;
    }

    @NotBlank(message = "First name can not be empty!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Last name can not be empty!")
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

    @EnumValue(enumClass = Town.class, message = "Select valid town!")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Positive(message = "Age must be positive!")
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
