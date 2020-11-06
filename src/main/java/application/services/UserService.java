package application.services;

import application.constants.Constants;
import application.entities.Role;
import application.entities.User;
import application.entities.UserRole;
import application.repositories.RoleRepository;
import application.repositories.UserRepository;
import application.repositories.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        try {
            user.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
            UserRole userRole = new UserRole();
            userRole.setRoleId(Constants.Role.ROLE_USER);
            userRole.setUserId(user.getId());
            userRoleRepository.saveAndFlush(userRole);
            return user;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public User findByUsername(String username) { return userRepository.findByUsername(username); }

    public List<Role> getActiveListRole(long userId) {
        List<UserRole> listUserRoles = StreamSupport
                .stream(userRoleRepository.findRoleOfUser(userId).spliterator(), false)
                .collect(Collectors.toList());
        return getListRole().stream()
                .filter(role -> {
                    return (listUserRoles.stream()
                            .filter(userRole -> userRole.getRoleId() == role.getId())
                            .findFirst()
                            .orElse(null) != null);
                })
                .collect(Collectors.toList());
    }

    public List<Role> getListRole() {
        return StreamSupport
                .stream(roleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

    }
}
