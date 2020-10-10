package com.ig.igtask.api;

import com.ig.igtask.base.api.BaseController;
import com.ig.igtask.model.User;
import com.ig.igtask.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class UserController extends BaseController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(
            path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid @NotBlank User user){
        this.userService.createUser(user);
    }

    @DeleteMapping(
            path = "/user/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("id") long userId){
        this.userService.removeUser(userId);
    }
}
