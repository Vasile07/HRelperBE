package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.AccountRegistrationData;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.Implementations.UserServiceImplementation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserServiceImplementation userService;

    public UserController(UserServiceImplementation userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody LoginCredentials loginCredentials){
        return userService.login(loginCredentials);
    }

    @PostMapping(path = "/register")
    public void register(@RequestBody AccountRegistrationData accountRegistrationData){
        userService.register(accountRegistrationData);
    }
}
