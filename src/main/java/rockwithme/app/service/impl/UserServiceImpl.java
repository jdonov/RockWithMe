package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import rockwithme.app.model.service.*;
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
    public UserServiceDTO registerUser(UserRegisterDTO userDto) {
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
        user = this.userRepository.saveAndFlush(user);
        return this.modelMapper.map(user, UserServiceDTO.class);
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
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName", "lastName", "username");
        return this.userRepository.findAll(spec, sort).stream()
                .map(user -> this.modelMapper.map(user, UserSearchDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceDTO addBand(User user, Band band) {
        user.getBands().add(band);
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
    }

    @Override
    public UserServiceDTO removeBand(User user, Band band) {
        user.getBands().remove(band);
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
    }

    @Override
    public UserServiceDTO addRequest(User user, JoinRequest request) {
        user.getRequests().add(request);
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
    }

    @Override
    public UserServiceDTO addLike(Like like, User user) {
        user.getLikes().add(like);
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    @Override
    public UserServiceDTO updatePlayer(UserUpdateDTO userUpdateDTO) {
        User user = this.userRepository.findByUsername(userUpdateDTO.getUsername()).orElse(null);
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setTown(Town.valueOf(userUpdateDTO.getTown()));
        user.setAge(userUpdateDTO.getAge());
        if(userUpdateDTO.getImgUrl() != null) {
            user.setImgUrl(userUpdateDTO.getImgUrl());
        }
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
    }

    @Override
    public UserServiceDTO updateUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        UserServiceDTO userServiceDTO = this.modelMapper.map(user, UserServiceDTO.class);
        return userServiceDTO;
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
    public boolean changeUserPassword(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        user.setPassword(this.passwordEncoder.encode(password));
        return this.userRepository.saveAndFlush(user) != null;
    }

    @Override
    public UserServiceDTO addNewRole(String userId, Role role) {
        User user = this.userRepository.findById(userId).orElse(null);
        user.getAuthorities().add(role);
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
    }

    @Override
    public UserServiceDTO removeUserRole(String userId, Role role) {
        User user = this.userRepository.findById(userId).orElse(null);
        if (user.getAuthorities().size() > 1) {
            Set<Role> newRoles = user.getAuthorities().stream()
                    .filter(r -> !r.equals(role))
                    .collect(Collectors.toSet());
            user.setAuthorities(newRoles);
            return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceDTO.class);
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
