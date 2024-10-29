package com.bandlan.bandlabassignment.service;

import com.bandlan.bandlabassignment.models.dbentity.UserInfo;
import com.bandlan.bandlabassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserInfo> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
