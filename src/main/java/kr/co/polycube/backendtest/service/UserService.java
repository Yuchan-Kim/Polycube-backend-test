package kr.co.polycube.backendtest.service;

import kr.co.polycube.backendtest.model.User;
import kr.co.polycube.backendtest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
         this.userRepository = userRepository;
    }

    public User createUser(String name) {
         User user = new User(name);
         return userRepository.save(user);
    }

    public Optional<User> getUser(Long id) {
         return userRepository.findById(id);
    }

    public User updateUser(Long id, String name) {
         User user = userRepository.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
         user.setName(name);
         return userRepository.save(user);
    }
}