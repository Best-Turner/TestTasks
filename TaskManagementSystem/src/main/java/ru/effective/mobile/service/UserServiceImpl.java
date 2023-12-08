package ru.effective.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.effective.mobile.model.User;
import ru.effective.mobile.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createUser(String email, String password) {
        saveUser(new User(email, password));
    }

    @Override
    public User findOne(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void changeUser(long userId, User updateUser) {
        updateUser.setId(userId);
        userRepository.save(updateUser);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
