package com.example.java.springboot.securitybatch2.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    private String userName;
    private String password;
}
