package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {
    private IBookRepository bookRepo = new MemoryBookRepository();
    private IReservationRepository reservationRepo = new MemoryReservationRepository();
    ReservationService r = new ReservationService(bookRepo, reservationRepo);

    @Test
    public void reservationCopiesAvailableLessThanOneTest() {
        Book book = new Book("1", "testBook", 0);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        assertThrows(NoAvailableCopiesException.class, () -> {
            r.reserve(user.getId(), book.getId());
        });

    }

    @Test
    public void reserveDecreasesCopiesAvailableTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        try {
            r.reserve(user.getId(), book.getId());
        } catch (NoAvailableCopiesException e) {
            System.out.println(e);
        }
        assertEquals(2, book.getCopiesAvailable());
    }

    @Test
    public void reservationSuccessfulTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        try {
            r.reserve("11", "1");
        } catch (NoAvailableCopiesException e) {
            System.out.println(e);
        }
        assertTrue(reservationRepo.existsByUserAndBook(user.getId(), book.getId()));
    }

    @Test
    public void reserveSameBookTwiceTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        try {
            r.reserve(user.getId(), book.getId());
        } catch (NoAvailableCopiesException e) {
            System.out.println(e);
        }
        assertThrows(IllegalStateException.class, () -> {
            r.reserve(user.getId(), book.getId());
        });
    }
}
