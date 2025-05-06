package com.project.user_service.services;


import com.project.user_service.dao.UserDAO;
import com.project.user_service.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User saveUser(User user) {
        return userDAO.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        if (userDAO.findByEmail(email).isEmpty()) {
            return Optional.empty();
        }
        return userDAO.findByEmail(email);
    }

    public User findById(int id) {
        return userDAO.findById(id).get();
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }


    public boolean updateKycStatus(int userId, String status) {
        Optional<User> userOpt = userDAO.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setKycStatus(status);
            userDAO.save(user);
            return true;
        }
        return false;
    }


}