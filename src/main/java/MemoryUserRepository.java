package main.java;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements IUserRepository {
    private final Map<String, User> userRepo = new HashMap<>();

    @Override
    public void save(User user) {
        userRepo.put(user.getId(), user);
    }

    @Override
    public User findUserById(String userId) {
        return userRepo.get(userId);
    }

}
