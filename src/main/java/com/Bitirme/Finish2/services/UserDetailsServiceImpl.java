package com.Bitirme.Finish2.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Bitirme.Finish2.entities.User;
import com.Bitirme.Finish2.repository.UserRepository;
import com.Bitirme.Finish2.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //JwtUserDetails den aldığı user'i bu classda kullanacağız.

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        return JwtUserDetails.create(user); //username ile repomuza döndüğümüzde UserDetails tipinde bir user dönecek...
    }
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).get();
        return JwtUserDetails.create(user);//id ile repomuza döndüğümüzde UserDetails tipinde bir user dönecek...
    }


}
