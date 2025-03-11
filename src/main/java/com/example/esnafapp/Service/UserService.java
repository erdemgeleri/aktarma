package com.example.esnafapp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.esnafapp.Model.User;
import com.example.esnafapp.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
	public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
	public void saveUser(User user) {
        userRepository.save(user);
    }
	public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
	
	public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
