package com.spring.smartcontact.config;

import com.spring.smartcontact.model.User;
import com.spring.smartcontact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.cert.Certificate;

public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        User user=userRepository.getUserByEmail(s);
        if(user==null){
            throw new UsernameNotFoundException("Could not found user");
        }
        CustomUserDetail customUserDetail=new CustomUserDetail(user);

        return customUserDetail;
    }
}
