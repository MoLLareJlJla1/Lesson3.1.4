package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repo.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;


@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email" + username + "not found"));
}

    @Override
    public void saveUser(User user) {
            userRepository.save(user);
    }

    @Override
    public List<User> showAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
