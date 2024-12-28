package com.scm.scm20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repositories.UserRepo;
import com.scm.scm20.services.UserService;


@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // user id : have to generate
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
       // update user2 from users
       user2.setName(user.getName());
       user2.setEmail(user.getEmail());
       user2.setPassword(user.getPassword());
       user2.setPhoneNumber(user.getPhoneNumber());
       user2.setAbout(user.getAbout());
       user2.setProfilePic(user.getProfilePic());
       user2.setEnabled(user.isEnabled());
       user2.setEmailVerfied(user.isEmailVerfied());
       user2.setPhoneVerfied(user.isPhoneVerfied());   
       user2.setProvider(user.getProvider());   
       user2.setProviderUserId(user.getProviderUserId());  
       
       // save the user in database
       User save = userRepo.save(user2);
       return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExists(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}
