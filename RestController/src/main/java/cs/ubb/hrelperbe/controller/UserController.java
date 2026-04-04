package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.Implementations.UserServiceImplementation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
