package com.example.java.springboot.securitybatch2.Service.UserService;

import com.example.java.springboot.securitybatch2.Entity.User;
import com.example.java.springboot.securitybatch2.Model.AuthenticationRequest;
import com.example.java.springboot.securitybatch2.Model.AuthenticationResponse;
import com.example.java.springboot.securitybatch2.Model.UserRegistration;
import com.example.java.springboot.securitybatch2.Repository.UserRepository;
import com.example.java.springboot.securitybatch2.Service.JwtService.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    //when user Register from client
    public AuthenticationResponse saveUser(UserRegistration userRegistration){
        User user = User.builder()
                .firstName(userRegistration.getFirstName())
                .lastName(userRegistration.getLastName())
                .email(userRegistration.getEmail())
                .password(passwordEncoder.encode(userRegistration.getPassword()))
                .role(userRegistration.getRole()).build();
        User savedUser = userRepository.save(user);
        //generate the token
        String jwtToken = jwtService.generateToken(user);
        // return jwt token
        return AuthenticationResponse.builder().accessToken(jwtToken).build(); //jwtService will help us to generate the jwt token.
    }

    //when user will Login to authenticate user.
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUserName(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(authenticationRequest.getUserName()).orElseThrow(() -> new RuntimeException("user not found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }
}
