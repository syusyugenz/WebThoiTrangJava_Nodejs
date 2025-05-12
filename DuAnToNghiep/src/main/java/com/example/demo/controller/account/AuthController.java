package com.example.demo.controller.account;


import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value= {"*"})
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody UserRegisterDTO userDTO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Tên đăng nhập hoặc mật khẩu không đúng");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}