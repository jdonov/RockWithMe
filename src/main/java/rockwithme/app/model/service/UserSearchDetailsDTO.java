package rockwithme.app.model.service;

import rockwithme.app.model.entity.Role;

import java.util.Set;

public class UserSearchDetailsDTO extends BaseServiceModel{
    private String firstName;
    private String lastName;
    private String username;
    private Set<Role> authorities;

    public UserSearchDetailsDTO() {
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

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
