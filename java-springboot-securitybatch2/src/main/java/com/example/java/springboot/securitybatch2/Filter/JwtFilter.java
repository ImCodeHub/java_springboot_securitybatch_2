package com.example.java.springboot.securitybatch2.Filter;

import com.example.java.springboot.securitybatch2.Service.JwtService.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("api/v1/auth");
    }

    /**Extracts the JWT from the "Authorization" header.<p>
     Validates the JWT and extracts the username.<p>
     If the JWT is valid and the user is not already authenticated, it loads the user's details and sets the user as authenticated in the Spring Security context.
     Finally, it allows the request to continue to the next filter.*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String userEmail;

        // 1. Extract Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        //2. Check if the Authorization header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 3. If the header is invalid, pass the request to the next filter in the chain and return.
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Extract the JWT from the Authorization header by removing the "Bearer " prefix (start after prefix)
        final String jwt = authHeader.substring(7);

        try {
            // 5. Use the jwtService to extract the username from the JWT.
            userEmail = jwtService.extractUserName(jwt);
        } catch (Exception e) {
            // 6. If an exception occurs during username extraction (e.g., invalid JWT), pass the request to the next filter and return
            filterChain.doFilter(request, response);
            return;
        }
        // 7. Check if the email was extracted successfully and if the user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 8. Load the user details from the database using the extracted email (userEmail)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // 9. Create an Authentication object (UsernamePasswordAuthenticationToken) with the user details
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userEmail,
                    null,
                    userDetails.getAuthorities()
            );
            // 10. Set details about the request in the Authentication object
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 11. Set the Authentication object in the SecurityContextHolder, effectively authenticating the user
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 12. Pass the request to the next filter in the chain (regardless of authentication result)
        filterChain.doFilter(request, response);
    }
}
