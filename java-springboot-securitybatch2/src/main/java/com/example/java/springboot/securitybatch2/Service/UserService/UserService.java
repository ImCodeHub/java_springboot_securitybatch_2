package com.example.java.springboot.securitybatch2.Service.UserService;

import com.example.java.springboot.securitybatch2.Entity.User;
import com.example.java.springboot.securitybatch2.Model.AuthenticationResponse;
import com.example.java.springboot.securitybatch2.Model.UserRegistration;
import com.example.java.springboot.securitybatch2.Repository.UserRepository;
import com.example.java.springboot.securitybatch2.Service.JwtService.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    public AuthenticationResponse saveUser(UserRegistration userRegistration){
        User user = User.builder()
                .firstName(userRegistration.getFirstName())
                .lastName(userRegistration.getLastName())
                .email(userRegistration.getEmail())
                .role(userRegistration.getRol()).build();
        User savedUser = userRepository.save(user);
        //generate the token
        String jwtToken = jwtService.generateToken(user);
        // return jwt token
        return AuthenticationResponse.builder().accessToken(jwtToken).build(); //jwtService will help us to generate the jwt token.
    }
}
