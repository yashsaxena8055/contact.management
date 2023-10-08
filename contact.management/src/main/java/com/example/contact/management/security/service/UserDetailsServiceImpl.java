package com.example.contact.management.security.service;

import com.example.contact.management.dao.UserDAO;
import com.example.contact.management.model.User;
import com.example.contact.management.constant.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userDAO.findByUsername(username);
       if(!opt.isPresent()){
           throw new UsernameNotFoundException("No user is present with username : "+username);
       }
       User user = opt.get();
      if(Arrays.asList(UserStatus.DELETED,UserStatus.INACTIVE).contains(user.getStatus())){
          throw new UsernameNotFoundException("User is either deleted or inactive in our system with this is username : "+username);
      }
      return new UserDetail(user);
    }
}
