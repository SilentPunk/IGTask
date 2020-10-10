package com.ig.igtask.repository;

import com.ig.igtask.model.Bookmark;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserIdAndStockId(long userId, long stockId);

    Optional<List<Bookmark>> findByUserId(long userId);
}
