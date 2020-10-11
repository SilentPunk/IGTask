package com.ig.igtask.services;

import com.ig.igtask.base.exceptions.base.NoContentFoundException;
import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.base.exceptions.concrete.NotFoundStockException;
import com.ig.igtask.base.exceptions.concrete.NotFoundUserException;
import com.ig.igtask.model.Bookmark;
import com.ig.igtask.model.Stock;
import com.ig.igtask.model.User;
import com.ig.igtask.repository.BookmarkRepository;
import com.ig.igtask.repository.StockRepository;
import com.ig.igtask.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookmarkService {
    BookmarkRepository bookmarkRepository;
    UserRepository userRepository;
    StockRepository stockRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, StockRepository stockRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
    }

//    public void addBookmark(Bookmark incomingBookmark) throws NotFoundException {
//        User user = getUser(incomingBookmark.getUser(), "BOOKMARK-0001");
//        Stock stock = getStock(incomingBookmark);
//
//        Bookmark bookmark = this.bookmarkRepository
//                                    .findByUserIdAndStockId(user.getId(), stock.getId()).
//                                    orElse(new Bookmark(stock, user));
//
//        bookmark.setStockPrice(incomingBookmark.getStockPrice());
//        this.bookmarkRepository.save(bookmark);
//    }

    public List<Bookmark> getBookmarks(User incomingUser) throws NotFoundException, NoContentFoundException {
        User user = getUser(incomingUser, "BOOKMARK-0003");

        return this.bookmarkRepository
                .findByUserId(user.getId())
                .orElse(new ArrayList<>());
    }


    private User getUser(User incomingUser, String errorCode) throws NotFoundException{
        return this.userRepository
                .findByIdentifier(incomingUser.getIdentifier())
                .orElseThrow(() -> new NotFoundUserException(errorCode, "No user found"));
    }


}
