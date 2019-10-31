package org.mial.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mial.dto.UserDTO;
import org.mial.exception.UserNotFoundException;
import org.mial.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/")
    Stream<UserDTO> users() {
        return userService.getUsers().stream();
    }

    @GetMapping("/page")
    Page<UserDTO> users(@PageableDefault(size = 5)
                              @SortDefault.SortDefaults(
                                  {@SortDefault(sort = "id", direction = ASC)})
                                  Pageable page) {
        return userService.getUsers(page);
    }

    @GetMapping("/{id}")
    UserDTO user(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/")
    Long save(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        userService.removeUser(id);
    }
}
