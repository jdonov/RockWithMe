package rockwithme.app.model.binding;

import org.hibernate.validator.constraints.Length;
import rockwithme.app.constraint.EnumValue;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.Town;

import javax.validation.constraints.NotBlank;


public class UserRegisterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private String role;
    private String town;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String firstName, String lastName, String username, String password, String confirmPassword, String role, String town) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
        this.town = town;
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

    @Length(min = 3, max = 15, message = "Username must be between 3 and 15 letters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, message = "Password must be at least 3 characters long!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "Please confirm password!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @EnumValue(enumClass = Role.class, message = "Select valid role!")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @EnumValue(enumClass = Town.class, message = "Select valid town!")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
