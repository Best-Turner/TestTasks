package ru.effectiveMobile.socialMedia.services;

import org.springframework.stereotype.Service;
import ru.effectiveMobile.socialMedia.model.User;

import java.util.List;

@Service
public interface UserService {
    void add(User user);

    User getUserById(long ig);

    void deleteUserById(long id);

    void updateUser(long id, User newUser);
    List<User> getAllUsers();


}
