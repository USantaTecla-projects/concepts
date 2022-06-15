package com.example.backend.api.resources.user;

import com.example.backend.api.resources.user.dto.UserDTO;
import com.example.backend.api.resources.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@RequestBody final UserDTO userDTO) {
        return userService.create(userDTO);

    }

    @GetMapping("/{userId}")
    public User findOne(@PathVariable final Long userId) {
        return userService.findOne(userId);
    }

    @GetMapping("/")
    public Page<User> findAll(@RequestParam Integer page) {
        return userService.findAll(page);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long userId, @RequestBody final UserDTO userDTO) {
        userService.updateOne(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long userId) {
        userService.removeOne(userId);
    }

}
