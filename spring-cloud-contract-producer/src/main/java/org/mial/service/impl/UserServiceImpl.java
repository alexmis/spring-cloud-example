package org.mial.service.impl;

import lombok.RequiredArgsConstructor;
import org.mial.dto.UserDTO;
import org.mial.repository.UserRepository;
import org.mial.service.UserService;
import org.mial.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    @Override
    public Collection<UserDTO> getUsers() {
        return repository.findUsers().map(mapper::map).collect(toList());
    }

    @Override
    public Page<UserDTO> getUsers(Pageable page) {
        return repository.findUsers(page).map(mapper::map);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return repository.findUserById(id).map(mapper::map);
    }

    @Override
    public Long addUser(UserDTO user) {
        return repository.saveUser(mapper.map(user));
    }

    @Override
    public void removeUser(Long id) {
        repository.removeUser(id);
    }
}
