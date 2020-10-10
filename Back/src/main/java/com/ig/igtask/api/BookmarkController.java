package com.ig.igtask.api;

import com.ig.igtask.base.api.BaseController;
import com.ig.igtask.base.exceptions.base.NoContentFoundException;
import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.model.Bookmark;
import com.ig.igtask.model.User;
import com.ig.igtask.services.BookmarkService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookmarkController extends BaseController {
    private BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping(
            path = "/bookmark",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void addBookmark(@RequestBody @Valid Bookmark bookmark) throws NotFoundException {
        bookmarkService.addBookmark(bookmark);
    }

    @GetMapping(
            path = "/bookmarks",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Bookmark> getBookmarks(@RequestBody @Valid User user) throws NotFoundException, NoContentFoundException {
        return this.bookmarkService.getBookmarks(user);
    }

}
