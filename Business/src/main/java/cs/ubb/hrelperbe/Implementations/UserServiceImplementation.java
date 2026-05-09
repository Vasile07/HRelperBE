package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.User;
import cs.ubb.hrelperbe.Constants.UserType;
import cs.ubb.hrelperbe.CustomException;
import cs.ubb.hrelperbe.DTOs.AccountRegistrationData;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.DTOs.TokenDto;
import cs.ubb.hrelperbe.DTOs.UserDetailsData;
import cs.ubb.hrelperbe.Interfaces.UserRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.UserServiceInterface;
import cs.ubb.hrelperbe.Mappers.UserMapper;
import cs.ubb.hrelperbe.authentication.TokenProvider;
import cs.ubb.hrelperbe.validation.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserServiceImplementation implements UserServiceInterface {
    private final UserRepositoryInterface userRepository;

    private final UserValidator userValidator;

    private final UserMapper userMapper;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepositoryInterface userRepository, UserValidator userValidator, UserMapper userMapper, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenDto login(LoginCredentials loginCredentials) {
        User user = userRepository.getUserByEmail(loginCredentials.getEmail());
        if (!passwordEncoder.matches(loginCredentials.getPassword(), user.getPassword())){
            throw new CustomException("Invalid credentials", HttpStatus.NOT_FOUND);
        }

        var tokenDto = new TokenDto();
        tokenDto.setAccessToken(tokenProvider.generateAccessToken(user));
        tokenDto.setIdToken(tokenProvider.generateIdToken(user));
        return tokenDto;
    }

    @Override
    public void register(AccountRegistrationData accountRegistrationData) {
        User user = userMapper.toEntity(accountRegistrationData);
        String type = accountRegistrationData.getType();
        if (type == null || type.isBlank()){
            throw new IllegalArgumentException("User type must not be null or empty");
        }
        user.setType(UserType.valueOf(type.toUpperCase()));
        userValidator.validate(user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetailsData getUserDetails(Integer userId) {
        User user = userRepository.getUserById(userId);
        return new UserDetailsData(
                user.getName() + " " + user.getSurname(),
                user.getEmail(),
                user.getType().name().toLowerCase(Locale.ROOT)
        );
    }
}
