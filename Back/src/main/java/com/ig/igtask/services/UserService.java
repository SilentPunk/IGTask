package com.ig.igtask.services;

import com.ig.igtask.model.User;
import com.ig.igtask.repository.BookmarkRepository;
import com.ig.igtask.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private BookmarkRepository bookmarkRepository;

    public UserService(UserRepository userRepository, BookmarkRepository bookmarkRepository) {
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    public void createUser(User user){
        this.userRepository.save(user);
    }

    public void removeUser(long userId){
        this.userRepository.deleteById(userId);
    }


}
