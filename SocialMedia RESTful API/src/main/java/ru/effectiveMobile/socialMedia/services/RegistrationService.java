package ru.effectiveMobile.socialMedia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.effectiveMobile.socialMedia.model.Role;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.repositories.UserRepository;

import java.util.Collections;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
    }
}
