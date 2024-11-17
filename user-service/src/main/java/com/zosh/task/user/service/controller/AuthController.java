package com.zosh.task.user.service.controller;

import com.zosh.task.user.service.config.JwtProvider;
import com.zosh.task.user.service.modal.User;
import com.zosh.task.user.service.repository.UserRepository;
import com.zosh.task.user.service.request.LoginRequest;
import com.zosh.task.user.service.response.AuthResponse;
import com.zosh.task.user.service.service.CustomerUserServiceImplementation;
import com.zosh.task.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUserServiceImplementation customerUserDetails;
    @Autowired
    private UserService userService;



    @PostMapping("/signup")
    public ResponseEntity <?> CreateUserHandler(
        @RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role = user.getRole();
        String phone = user.getPhone();

        User isEmailExist  = userRepository.findByEmail(email);
        if (isEmailExist != null) {
//            throw new Exception("Email đã được sử dụng cho tài khoản khác!!");
            return ResponseEntity.status(226).body("Email đã được sử dụng cho tài khoản khác!!");
        }

        //Create new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setPhone(phone);
        createdUser.setIsActive(true);
        createdUser.setRole(role);

        User saveUser= userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Đăng ký thành công");
        authResponse.setRole(saveUser.getRole());

        return  new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        System.out.println(userName + "------" + password);

        try {
            Authentication authentication = authenticate(userName, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JwtProvider.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse();
            User user = userRepository.findByEmail(userName);
            authResponse.setMessage("Đăng nhập thành công!!");
            authResponse.setJwt(token);
            authResponse.setRole(user.getRole());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity.status(226).body("Sai thông tin đăng nhập!!!");
        }
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<Boolean> findByEmail (@RequestParam String email) {
        System.out.println("mail: "+email   );
        User user= userRepository.findByEmail(email);
        System.out.println(user);
        if(user == null ){
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }


    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails  = customerUserDetails.loadUserByUsername(userName);
        System.out.println("sigin in userDetails- "+userDetails);

        if(userDetails == null ) {
            System.out.println("sign in userdetails - null "+ userDetails);
            throw new BadCredentialsException("Thiếu thông tin mật khẩu hoặc tên đăng nhập");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw  new BadCredentialsException("Nhập mật khẩu không chính xác");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
