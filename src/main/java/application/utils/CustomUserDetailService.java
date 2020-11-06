package application.utils;

import application.entities.Role;
import application.entities.User;
import application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService service;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = service.findByUsername(username);
        List<Role> listActiveRoles = service.getActiveListRole(user.getId());
        boolean isActive = true;

        if (listActiveRoles.size() == 0) {
            isActive = false;
        }

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        for (Role role : listActiveRoles) {
            setAuths.add(new SimpleGrantedAuthority(role.getName()));
        }

        if (user != null) {
            org.springframework.security.core.userdetails.User userDetail =
                    new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                            isActive, true, true, true, setAuths);

            return userDetail;
        }
        throw new UsernameNotFoundException(username + " not found");
    }
}

