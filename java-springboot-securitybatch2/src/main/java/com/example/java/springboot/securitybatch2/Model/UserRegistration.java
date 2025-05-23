package com.example.java.springboot.securitybatch2.Model;

import com.example.java.springboot.securitybatch2.Entity.Role;
import lombok.Data;

@Data
public class UserRegistration {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
