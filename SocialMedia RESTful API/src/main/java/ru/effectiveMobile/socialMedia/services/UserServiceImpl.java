package ru.effectiveMobile.socialMedia.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.repositories.UserRepository;
import ru.effectiveMobile.socialMedia.security.UserDetailsImpl;

import java.util.Optional;

@Service
@Log4j2

public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public void add(User user) {

    }

    @Override
    public User getUserById(long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таки ID не найден!");
        }
        return userById.get();
    }

    @Override
    public void deleteUserById(long id) {

    }

    @Override
    public void updateUser(long id, User newUser) {

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByName(username);
        if (username.isEmpty()) {
            throw new UsernameNotFoundException("Неверное имя пользователя");
        }
        return new UserDetailsImpl(user.get());

    }
}
