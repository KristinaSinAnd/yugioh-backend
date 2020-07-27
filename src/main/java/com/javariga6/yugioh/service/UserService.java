package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.getOne(id);
    }

    public User getUserByEmail(String email){
        return userRepository.getFirstByEmail(email);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        User userToDelete = userRepository.getFirstByEmail(email);
        userRepository.delete(userToDelete);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
