package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.User;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.Interfaces.UserRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.UserServiceInterface;
import cs.ubb.hrelperbe.authentication.TokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserServiceInterface {
    private final UserRepositoryInterface userRepository;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepositoryInterface userRepository, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginCredentials loginCredentials) {
        User user = userRepository.getUserByEmail(loginCredentials.getEmail());
        if (!passwordEncoder.matches(loginCredentials.getPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }
        return tokenProvider.generateAccessToken(user);
    }
}
