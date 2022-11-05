package com.cookysys.social_media_project.repositories;

import java.util.List;
//import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cookysys.social_media_project.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByCredentialsUsername(String username);

	Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

	List<User> findAllByDeletedFalse();

}
