package org.mial.repository;

import org.mial.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository {

    Stream<User> findUsers();

    Page<User> findUsers(Pageable page);

    Optional<User> findUserById(Long id);

    Long saveUser(User user);

    void removeUser(Long id);
}
