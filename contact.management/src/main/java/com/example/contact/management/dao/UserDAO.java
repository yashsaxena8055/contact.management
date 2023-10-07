package com.example.contact.management.dao;

import com.example.contact.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.username =:username")
    public Optional<User> findByUsername(@Param("username") String username);

}
