package com.zosh.task.user.service.controller;

import com.zosh.task.user.service.config.JwtProvider;
import com.zosh.task.user.service.modal.User;
import com.zosh.task.user.service.request.NewPasswordRequest;
import com.zosh.task.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader ("Authorization") String jwt){
        User user = userService.getUserProfile(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader ("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser (@RequestHeader ("Authorization") String jwt){
        List<User> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> createNewPassword(@RequestHeader ("Authorization") String jwt,
                                               @RequestBody NewPasswordRequest newPasswordRequest) {
        String pwOld= newPasswordRequest.getPwOld();
        String pwNew = newPasswordRequest.getPwNew();
        User user = userService.getUserProfile(jwt);
        if (!passwordEncoder.matches(pwOld,user.getPassword())){
            return ResponseEntity.status(226).body("Mật khẩu không chính xác !!");
        }else {
            User newUser = userService.newPassword(jwt,pwNew);
            return ResponseEntity.ok().body(newUser);
        }
    }
}
