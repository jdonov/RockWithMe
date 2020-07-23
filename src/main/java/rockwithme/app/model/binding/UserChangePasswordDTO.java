package rockwithme.app.model.binding;

import org.hibernate.validator.constraints.Length;

public class UserChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public UserChangePasswordDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Length(min = 3, message = "Password must be at least 3 characters long!")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
