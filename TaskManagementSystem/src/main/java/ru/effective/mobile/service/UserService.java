package ru.effective.mobile.service;

import ru.effective.mobile.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void createUser(String email, String password);
    User findOne(long userId);

    List<User> findAll();

    void changeUser(User updateUser, User newUser);

    void deleteUser(long userId);
}
