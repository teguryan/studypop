package com.tubes.studypop.repository;

import com.tubes.studypop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}
