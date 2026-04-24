package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.BaseModels.User;

public interface UserRepositoryInterface {
    public User getUserByEmail(String email);

    public User getUserById(Integer userId);

    public void save(User user);
}
