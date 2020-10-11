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

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private BookmarkRepository bookmarkRepository;
    private StockRepository stockRepository;

    public UserService(UserRepository userRepository, BookmarkRepository bookmarkRepository, StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.stockRepository = stockRepository;
    }

    public User createUser(User user){
        this.userRepository.save(user);
        return user;
    }

    public void removeUser(long userId){
        this.userRepository.deleteById(userId);
    }

    public List<Bookmark> getUserBookmarks(long userId) throws NotFoundException, NoContentFoundException {
        this.userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException("USER-0001", "No user found"));

        return this.bookmarkRepository
                .findByUserId(userId)
                .orElseThrow(NoContentFoundException::new);

    }

    public void createBookmark(long userId, Stock stock) throws NotFoundException{
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException("USER-0002", "No user found"));
        this.stockRepository.findByStockName(stock.getStockName()).orElseThrow(() -> new NotFoundStockException("USER-0003", "No stock find"));

        Bookmark bookmark = this.bookmarkRepository
                .findByUserIdAndStockId(userId, stock.getId())
                .orElse(new Bookmark(stock, user));

        bookmark.setStockPrice(stock.getCurrentPrice());

        this.bookmarkRepository.save(bookmark);
    }


}
