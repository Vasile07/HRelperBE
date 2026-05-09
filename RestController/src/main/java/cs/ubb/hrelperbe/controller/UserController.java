package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.AccountRegistrationData;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.DTOs.TokenDto;
import cs.ubb.hrelperbe.DTOs.UserDetailsData;
import cs.ubb.hrelperbe.Interfaces.UserServiceInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public TokenDto login(@RequestBody LoginCredentials loginCredentials){
        return userService.login(loginCredentials);
    }

    @PostMapping(path = "/register")
    public void register(@RequestBody AccountRegistrationData accountRegistrationData){
        userService.register(accountRegistrationData);
    }

    @GetMapping(path = "/me")
    public UserDetailsData getLoggedInUser(Authentication authentication) {
        Integer userId = Integer.parseInt(authentication.getName());
        return userService.getUserDetails(userId);
    }
}
