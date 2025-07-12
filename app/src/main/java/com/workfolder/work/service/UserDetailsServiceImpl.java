package com.workfolder.work.service;

import com.workfolder.work.entity.User;
import com.workfolder.work.entity.UserPrincipal;
import com.workfolder.work.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found by username: " + username));
        return UserPrincipal.create(user);
    }
}
