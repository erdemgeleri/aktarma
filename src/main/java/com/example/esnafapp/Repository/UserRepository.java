package com.example.esnafapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.esnafapp.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	@Query("Select u from User u") 
	List<User> findAllUsers();
	
}
