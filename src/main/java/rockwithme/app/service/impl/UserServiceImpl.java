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
import rockwithme.app.exeption.PasswordsNotMatch;
import rockwithme.app.exeption.UserAlreadyExists;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.BandUserBandsServiceDTO;
import rockwithme.app.model.service.UserMyDetailsServiceDTO;
import rockwithme.app.model.service.UserPublicDetailsServiceDTO;
import rockwithme.app.repository.UserRepository;
import rockwithme.app.service.UserService;

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
    public User registerUser(UserRegisterDTO userDto) {
        this.userRepository.findByUsername(userDto.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExists(String.format("User with username '%s' already exists.", userDto.getUsername()));
        });
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new PasswordsNotMatch("Passwords doesn't match!");
        }
        User user = this.modelMapper.map(userDto, User.class);
        user.setAuthorities(Set.of(userDto.getRole()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<User> searchUsers(Specification<User> specification) {
        return this.userRepository.findAll(specification);
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
        if (!userUpdateDTO.getFirstName().isEmpty()) {
            user.setFirstName(userUpdateDTO.getFirstName());
            this.userRepository.saveAndFlush(user);
        }
        if (!userUpdateDTO.getLastName().isEmpty()) {
            user.setLastName(userUpdateDTO.getLastName());
            this.userRepository.saveAndFlush(user);
        }
        if (userUpdateDTO.getAge() > 0) {
            user.setAge(userUpdateDTO.getAge());
            this.userRepository.saveAndFlush(user);
        }
        if (userUpdateDTO.getImgUrl() != null && !userUpdateDTO.getImgUrl().isEmpty()) {
            user.setImgUrl(userUpdateDTO.getImgUrl());
            this.userRepository.saveAndFlush(user);
        }
        if (userUpdateDTO.getTown() != null) {
            user.setTown(userUpdateDTO.getTown());
            this.userRepository.saveAndFlush(user);
        }
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
