package com.ig.igtask.api;

import com.ig.igtask.base.api.BaseController;
import com.ig.igtask.base.exceptions.base.NoContentFoundException;
import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.model.Bookmark;
import com.ig.igtask.model.Stock;
import com.ig.igtask.model.StockPrice;
import com.ig.igtask.model.User;
import com.ig.igtask.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    public User createUser(@RequestBody @Valid @NotBlank User user){
        return this.userService.createUser(user);
    }

    @DeleteMapping(
            path = "/user/{userId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("userId") long userId){
        this.userService.removeUserById(userId);
    }

    @GetMapping(
            path = "/user/{userId}/bookmarks",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Bookmark> getUserBookmarks(@PathVariable("userId") long userId) throws NotFoundException, NoContentFoundException {
        return this.userService.getUserBookmarks(userId);
    }

    @PostMapping(
            path = "/user/{userId}/bookmark",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void createBookmark(@PathVariable("userId") long userId, @RequestBody StockPrice stockPrice) throws NotFoundException {
        this.userService.createBookmark(userId, stockPrice);
    }

    @DeleteMapping(
            path = "/user/{userId}/bookmark/{bookmarkId}"
    )
    public void removeBookmark(@PathVariable("userId") long userId, @PathVariable("bookmarkId") long bookmarkId) throws NoContentFoundException {
        this.userService.removeBookmark(userId, bookmarkId);
    }
}
