package com.example.java.springboot.securitybatch2.Controller;

import com.example.java.springboot.securitybatch2.Model.AuthenticationResponse;
import com.example.java.springboot.securitybatch2.Model.UserRegistration;
import com.example.java.springboot.securitybatch2.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;
    public ResponseEntity<AuthenticationResponse> registration(@RequestBody UserRegistration userRegistration){
        AuthenticationResponse authenticationResponse = userService.saveUser(userRegistration);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }
}
