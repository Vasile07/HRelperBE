package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.DTOs.AccountRegistrationData;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.DTOs.UserDetailsData;

public interface UserServiceInterface {
    public String login(LoginCredentials loginCredentials);

    public void register(AccountRegistrationData accountRegistrationData);

    public UserDetailsData getUserDetails(Integer userId);
}
