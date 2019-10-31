package org.mial.client;

import org.mial.domain.UserDTO;

import java.awt.print.Book;
import java.awt.print.Pageable;
import java.util.stream.Stream;

@FeignClient("bookservice")
public interface UserClient {

    Stream<UserDTO> users();

    @RequestMapping(method = RequestMethod.POST, path = "/api/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    Book createBook(@RequestBody Book book);

    age<UserDTO> users(@PageableDefault(size = 5)
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
    void delete(@PathVariable Long id);
}
