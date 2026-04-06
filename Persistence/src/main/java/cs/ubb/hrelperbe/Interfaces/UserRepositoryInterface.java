package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.BaseModels.User;

public interface UserRepositoryInterface {
    public User getUserByEmail(String email);
}
