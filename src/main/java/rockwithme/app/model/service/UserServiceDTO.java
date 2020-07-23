package rockwithme.app.model.service;

import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.JoinRequest;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.Town;

import java.util.Set;

public class UserServiceDTO extends BaseServiceModel{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<Role> authorities;
    private Town town;
    private int age;
    private Set<Band> bands;
    private Set<JoinRequest> requests;
}
