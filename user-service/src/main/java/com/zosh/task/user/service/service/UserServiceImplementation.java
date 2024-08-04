package com.zosh.task.user.service.service;

import com.zosh.task.user.service.config.JwtProvider;
import com.zosh.task.user.service.modal.User;
import com.zosh.task.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserProfile(String jwt) {
       String email =  JwtProvider.getEmailFromJwtToken(jwt);
       return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User newPassword(String jwt, String pwNew) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(pwNew));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("Không tìm thấy user!"));
    }


}
