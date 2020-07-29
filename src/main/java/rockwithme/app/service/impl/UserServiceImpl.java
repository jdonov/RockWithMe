package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rockwithme.app.exeption.PasswordsNotMatchException;
import rockwithme.app.exeption.UserAlreadyExistsException;
import rockwithme.app.exeption.UserWithoutRolesException;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserSearchBindingDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.UserAdminServiceDTO;
import rockwithme.app.model.service.UserMyDetailsServiceDTO;
import rockwithme.app.model.service.UserPublicDetailsServiceDTO;
import rockwithme.app.model.service.UserSearchDetailsDTO;
import rockwithme.app.repository.UserRepository;
import rockwithme.app.specification.UserSpecificationsBuilder;
import rockwithme.app.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterDTO userDto) {
        this.userRepository.findByUsername(userDto.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException(String.format("User with username '%s' already exists.", userDto.getUsername()));
        });
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new PasswordsNotMatchException("Passwords doesn't match!");
        }
        User user = this.modelMapper.map(userDto, User.class);
        user.setAuthorities(Set.of(Role.valueOf(userDto.getRole())));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<UserSearchDetailsDTO> searchUsers(UserSearchBindingDTO userSearchBindingDTO) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();

        if (userSearchBindingDTO.getUsername() != null && !userSearchBindingDTO.getUsername().isEmpty()) {
            builder.with("username", ":", userSearchBindingDTO.getUsername());
        }
        if (userSearchBindingDTO.getFirstName() != null && !userSearchBindingDTO.getFirstName().isEmpty()) {
            builder.with("firstName", ":", userSearchBindingDTO.getFirstName());
        }
        if (userSearchBindingDTO.getLastName() != null && !userSearchBindingDTO.getLastName().isEmpty()) {
            builder.with("lastName", ":", userSearchBindingDTO.getLastName());
        }

        Specification<User> spec = builder.build();
        return this.userRepository.findAll(spec).stream()
                .map(user -> this.modelMapper.map(user, UserSearchDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addBand(User user, Band band) {
        user.getBands().add(band);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void removeBand(User user, Band band) {
        user.getBands().remove(band);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void addRequest(User user, JoinRequest request) {
        user.getRequests().add(request);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void addLike(Like like, User user) {
        user.getLikes().add(like);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    @Override
    public void updatePlayer(UserUpdateDTO userUpdateDTO) {
        User user = this.userRepository.findByUsername(userUpdateDTO.getUsername()).orElse(null);
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setTown(Town.valueOf(userUpdateDTO.getTown()));
        user.setAge(userUpdateDTO.getAge());
        user.setImgUrl(userUpdateDTO.getImgUrl());
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserRegisterDTO updateUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        UserRegisterDTO userRegisterDTO = this.modelMapper.map(user, UserRegisterDTO.class);
        return null;
    }

    @Override
    public UserMyDetailsServiceDTO getUserDetailsByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        return this.modelMapper.map(user, UserMyDetailsServiceDTO.class);
    }

    @Override
    public UserPublicDetailsServiceDTO getUserPublicDetailsById(String userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        UserPublicDetailsServiceDTO userPublic = this.modelMapper.map(user, UserPublicDetailsServiceDTO.class);
        return userPublic;
    }

    @Override
    public boolean checkIfValidOldPassword(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        return this.passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void changeUserPassword(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void addNewRole(String userId, Role role) {
        User user = this.userRepository.findById(userId).orElse(null);
        user.getAuthorities().add(role);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void removeUserRole(String userId, Role role) {
        User user = this.userRepository.findById(userId).orElse(null);
        if (user.getAuthorities().size() > 1) {
            Set<Role> newRoles = user.getAuthorities().stream()
                    .filter(r -> !r.equals(role))
                    .collect(Collectors.toSet());
            user.setAuthorities(newRoles);
            this.userRepository.saveAndFlush(user);
        } else {
            throw new UserWithoutRolesException(String.format("Can not delete role! User %s has only one role!", user.getUsername()));
        }
    }

    @Override
    public int getCountOfAllUsers() {
        return this.userRepository.findAllUsersCount();
    }

    @Override
    public UserAdminServiceDTO getLastRegisteredUser() {
        return this.modelMapper.map(this.userRepository.findLastRegistered(), UserAdminServiceDTO.class);
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.
                        getAuthorities().
                        stream().
                        map(this::map).
                        collect(Collectors.toList())
        );
    }
    private GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority(role.name());
    }
}
