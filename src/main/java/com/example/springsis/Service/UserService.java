package com.example.springsis.Service;

import com.example.springsis.Entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String Email);
    Users createUser(Users user);

}
