package org.mial.service;

import org.mial.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<UserDTO> getUsers();

    Page<UserDTO> getUsers(Pageable page);

    Optional<UserDTO> getUserById(Long id);

    Long addUser(UserDTO user);

    void removeUser(Long id);
}
