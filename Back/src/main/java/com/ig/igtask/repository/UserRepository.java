package com.ig.igtask.repository;

import com.ig.igtask.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByIdentifier(String identifier);

}
