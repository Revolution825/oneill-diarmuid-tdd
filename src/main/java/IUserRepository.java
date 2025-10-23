package main.java;

public interface IUserRepository {
    void save(User user);

    User findUserById(String userId);
}
