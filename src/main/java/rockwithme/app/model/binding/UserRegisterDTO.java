package rockwithme.app.model.binding;

import org.hibernate.validator.constraints.Length;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.Town;

import javax.validation.constraints.NotBlank;


public class UserRegisterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private Role role;
    private Town town;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String firstName, String lastName, String username, String password, String confirmPassword, Role role, Town town) {
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

    @NotBlank(message = "Username can not be empty!")
    @Length(min = 3, max = 15, message = "Username must be between 3 and 15 letters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Password can not be empty!")
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

    @NotBlank(message = "Role can not be null!")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @NotBlank(message = "Please enter town!")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
