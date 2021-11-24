package com.ig.igtask.services;

import com.ig.igtask.base.exceptions.base.NoContentFoundException;
import com.ig.igtask.base.exceptions.base.NotFoundException;
import com.ig.igtask.base.exceptions.concrete.NotFoundStockException;
import com.ig.igtask.base.exceptions.concrete.NotFoundUserException;
import com.ig.igtask.model.Bookmark;
import com.ig.igtask.model.Stock;
import com.ig.igtask.model.StockPrice;
import com.ig.igtask.model.User;
import com.ig.igtask.repository.BookmarkRepository;
import com.ig.igtask.repository.StockRepository;
import com.ig.igtask.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.print.Book;
import java.util.*;
import java.util.stream.Stream;

public class UserServiceTest {

    //tested resource
    private UserService userService;

    //mocks
    private UserRepository userRepository;
    private BookmarkRepository bookmarkRepository;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.bookmarkRepository = Mockito.mock(BookmarkRepository.class);
        this.stockRepository = Mockito.mock(StockRepository.class);

        this.userService = new UserService(this.userRepository,
                this.bookmarkRepository,
                this.stockRepository);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    public void createUser(User givenUser) {
        //when
        User resultUser = this.userService.createUser(givenUser);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .save(givenUser);

        Assertions
                .assertThat(resultUser)
                .isEqualTo(givenUser);

    }

    private static Stream<Arguments> userProvider() {
        return Stream.of(
                Arguments.of(new User()),
                Arguments.of(new User(UUID.randomUUID().toString())),
                Arguments.of(new User("testIdentifier"))
        );
    }

    @ParameterizedTest
    @CsvSource({"1", "23", "543"})
    public void removeUserById(long givenUserId) {
        //when
        this.userService.removeUserById(givenUserId);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .deleteById(givenUserId);
    }

    @ParameterizedTest
    @MethodSource("getUserBookmarksProvider")
    public void getUserBookmarksReturnsBookmarkList(long givenUserId, User givenUser, List<Bookmark> givenBookmarkList) throws NotFoundException, NoContentFoundException {
        //given
        Mockito.when(this.userRepository.findById(givenUserId)).thenReturn(Optional.of(givenUser));
        Mockito.when(this.bookmarkRepository.findByUserId(givenUserId)).thenReturn(Optional.of(givenBookmarkList));

        //when
        List<Bookmark> resultBookmarkList = this.userService.getUserBookmarks(givenUserId);

        //then
        Assertions
                .assertThat(resultBookmarkList)
                .isEqualTo(givenBookmarkList);

        Mockito.verify(this.userRepository, Mockito.times(1))
                .findById(givenUserId);

        Mockito.verify(this.bookmarkRepository, Mockito.times(1))
                .findByUserId(givenUserId);
    }

    private static Stream<Arguments> getUserBookmarksProvider() {
        return Stream.of(
                Arguments.of(1, new User(), new ArrayList<>() {{
                            add(new Bookmark());
                            add(new Bookmark(1));
                            add(new Bookmark(new Stock(), new User()));
                        }}
                )
        );
    }

    @ParameterizedTest
    @CsvSource({"1", "23", "543"})
    public void getUserBookmarksReturnsNotFoundUserException(long givenUserId) throws NotFoundException, NoContentFoundException {
        //given
        Mockito.when(this.userRepository.findById(givenUserId)).thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatThrownBy(() -> this.userService.getUserBookmarks(givenUserId))
                .isInstanceOf(NotFoundException.class);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findById(givenUserId);
    }

    @ParameterizedTest
    @CsvSource({"1", "23", "543"})
    public void getUserBookmarksReturnsNoContentFoundException(long givenUserId) throws NotFoundException, NoContentFoundException {
        //given
        Mockito.when(this.userRepository.findById(givenUserId)).thenReturn(Optional.of(new User()));
        Mockito.when(this.bookmarkRepository.findByUserId(givenUserId)).thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatThrownBy(() -> this.userService.getUserBookmarks(givenUserId))
                .isInstanceOf(NoContentFoundException.class);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findById(givenUserId);

        Mockito.verify(this.bookmarkRepository, Mockito.times(1))
                .findByUserId(givenUserId);
    }

    @ParameterizedTest
    @MethodSource("createBookmarkProvider")
    public void createBookmark(long givenUserId, User givenUser, Stock givenStock, Bookmark givenBookmark, StockPrice givenStockPrice) throws NotFoundException {
        //given
        Mockito.when(this.userRepository.findById(givenUserId)).thenReturn(Optional.of(givenUser));
        Mockito.when(this.stockRepository.findByStockName(Mockito.any())).thenReturn(Optional.of(givenStock));
        Mockito.when(this.bookmarkRepository.findByUserIdAndStockId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(givenBookmark));

        //when
        this.userService.createBookmark(givenUserId, givenStockPrice);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findById(givenUserId);

        Mockito.verify(this.stockRepository, Mockito.times(1))
                .findByStockName(givenStockPrice.getStock().getStockName());

        Mockito.verify(this.bookmarkRepository, Mockito.times(1))
                .findByUserIdAndStockId(givenUserId, givenStock.getId());

        Mockito.verify(givenBookmark, Mockito.times(1))
                .setStockPrice(givenStockPrice.getCurrentPrice());

    }

    private static Stream<Arguments> createBookmarkProvider() {
        return Stream.of(
                Arguments.of(1, new User("testUser"), new Stock("fancyStockName"), Mockito.mock(Bookmark.class), new StockPrice(new Stock(), 1.21))
        );
    }

    @ParameterizedTest
    @MethodSource("idAndStockPriceProvider")
    public void createBookmarkWithNotFoundUserException(long givenUserId, StockPrice givenStockPrice) throws NotFoundException {
        //given
        Mockito.when(this.userRepository.findById(givenUserId)).thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatThrownBy(() -> this.userService.createBookmark(givenUserId, givenStockPrice))
                .isInstanceOf(NotFoundUserException.class);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findById(givenUserId);

    }

    @ParameterizedTest
    @MethodSource("idAndStockPriceProvider")
    public void createBookmarkWithNotFoundStockException(long givenUserId, StockPrice givenStockPrice) throws NotFoundException {
        //given
        Mockito.when(this.userRepository.findById(givenUserId)).thenReturn(Optional.of(new User()));
        Mockito.when(this.stockRepository.findByStockName(Mockito.any())).thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatThrownBy(() -> this.userService.createBookmark(givenUserId, givenStockPrice))
                .isInstanceOf(NotFoundStockException.class);

        //then
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findById(givenUserId);

        Mockito.verify(this.stockRepository, Mockito.times(1))
                .findByStockName(givenStockPrice.getStock().getStockName());

    }

    private static Stream<Arguments> idAndStockPriceProvider() {
        return Stream.of(
                Arguments.of(13, new StockPrice(new Stock("test"), 1.21)),
                Arguments.of(321, new StockPrice(new Stock("stockName"), 2341.21))
        );
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "3, 412", "23, 65"})
    public void removeBookmark(long givenUserId, long givenBookmarkId) throws NoContentFoundException {
        //given
        Bookmark givenBookmark = new Bookmark();
        Mockito.when(this.bookmarkRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.of(givenBookmark));

        //when
        this.userService.removeBookmark(givenUserId, givenBookmarkId);

        //then
        Mockito.verify(this.bookmarkRepository, Mockito.times(1))
                .findByIdAndUserId(givenBookmarkId, givenUserId);

        Mockito.verify(this.bookmarkRepository, Mockito.times(1))
                .delete(givenBookmark);
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "3, 412", "23, 65"})
    public void removeBookmarkThrowsNoContentFound(long givenUserId, long givenBookmarkId) throws NoContentFoundException {
        //given
        Mockito.when(this.bookmarkRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatThrownBy(() -> this.userService.removeBookmark(givenUserId, givenBookmarkId))
                .isInstanceOf(NoContentFoundException.class);

        //then
        Mockito.verify(this.bookmarkRepository, Mockito.times(1))
                .findByIdAndUserId(givenBookmarkId, givenUserId);


    }

}
