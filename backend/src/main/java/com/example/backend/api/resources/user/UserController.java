package com.example.backend.api.resources.user;

import com.example.backend.api.resources.user.model.User;
import com.example.backend.api.resources.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CrudUserService crudUserService;

    public UserController(CrudUserService crudUserService) {
        this.crudUserService = crudUserService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@RequestBody final UserDTO userDTO) {
        return crudUserService.create(userDTO);
    }

    @GetMapping("/{username}")
    public User findOne(@PathVariable final String username) {
        return crudUserService.findOneByUsername(username);
    }

    @GetMapping("/")
    public Page<User> findAll(@RequestParam Integer page) {
        return crudUserService.findAll(page);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long userId, @RequestBody final UserDTO userDTO) {
        crudUserService.updateOne(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long userId) {
        crudUserService.removeOne(userId);
    }

}
