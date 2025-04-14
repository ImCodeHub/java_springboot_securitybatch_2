package com.example.java.springboot.securitybatch2.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hr/api")
@PreAuthorize("hasAnyRole('HR','ADMIN')")
public class HrController {
    @GetMapping("hr-greeting")
    public String greeting(){
        return "this is your HR from somewhere";
    }
}
