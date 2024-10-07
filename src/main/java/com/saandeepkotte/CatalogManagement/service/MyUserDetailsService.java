package com.saandeepkotte.CatalogManagement.service;

import com.saandeepkotte.CatalogManagement.model.MyUserDetails;
import com.saandeepkotte.CatalogManagement.model.User;
import com.saandeepkotte.CatalogManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        System.out.println(user.getUsername().equals(username));
        System.out.println(user.getPassword());
        if(user == null) throw new UsernameNotFoundException("User not found");

//        return new MyUserDetails(user);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole() == null ? new String[]{"USER"} : user.getRole().split(","))
                .build();
    }
}
