package com.example.lab6.service;

import com.example.lab6.entity.User;
import com.example.lab6.repository.RoleRepository;
import com.example.lab6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.example.lab6.entity.RoleName.USER;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void addUser(String login, String password) {
     User user = new User();
     user.setLogin(login);
     user.setPassword(new BCryptPasswordEncoder().encode(password));
     user.setRole(roleRepository.findByName(USER));
     userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь " + username + " не найден в базе данных");
        }
        return user;
    }
}
