package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {
    private IBookRepository bookRepo = new MemoryBookRepository();
    private IReservationRepository reservationRepo = new MemoryReservationRepository();
    private IUserRepository userRepo = new MemoryUserRepository();
    ReservationService r = new ReservationService(bookRepo, reservationRepo, userRepo);

    @Test
    public void noCopiesAvailableTest() {
        Book book = new Book("1", "testBook", 0);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        assertThrows(IllegalStateException.class, () -> {
            r.reserve(user.getId(), book.getId());
        });

    }

    @Test
    public void reserveDecreasesCopiesAvailableTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        try {
            r.reserve(user.getId(), book.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertEquals(2, book.getCopiesAvailable());
    }

    @Test
    public void reservationSuccessfulTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        try {
            r.reserve("11", "1");
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertTrue(reservationRepo.existsByUserAndBook(user.getId(), book.getId()));
    }

    @Test
    public void reserveSameBookTwiceTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        try {
            r.reserve(user.getId(), book.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertThrows(IllegalStateException.class, () -> {
            r.reserve(user.getId(), book.getId());
        });
    }

    @Test
    public void cancellingReservationIncreasesCopiesAvailableTest() {
        Book book = new Book("1", "testBook", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        try {
            r.reserve(user.getId(), book.getId());
            r.cancel(user.getId(), book.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertEquals(3, book.getCopiesAvailable());
    }

    @Test
    public void userCanViewAllReservations() {
        Book book1 = new Book("1", "testBook1", 3);
        Book book2 = new Book("2", "testBook2", 2);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book1);
        bookRepo.save(book2);
        userRepo.save(user);
        try {
            r.reserve(user.getId(), book1.getId());
            r.reserve(user.getId(), book2.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertEquals(2, r.listReservations(user.getId()).size());
    }

    @Test
    public void cancelReservationTest() {
        Book book = new Book("1", "testBook1", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        try {
            r.reserve(user.getId(), book.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        r.cancel(user.getId(), book.getId());
        assertEquals(0, r.listReservations(user.getId()).size());
    }

    @Test
    public void IllegalArgumentExceptionIfNoReservationFoundTest() {
        Book book = new Book("1", "testBook1", 3);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        assertThrows(IllegalArgumentException.class, () -> {
            r.cancel(user.getId(), book.getId());
        });
    }

    @Test
    public void IllegalArgumentExceptionIfBookNotFoundTest() {
        User user = new User("11", "JohnDoe");
        userRepo.save(user);
        assertThrows(IllegalArgumentException.class, () -> {
            r.reserve(user.getId(), "42");
        });
    }

    @Test
    public void ListReservationsForBooksTest() {
        Book book = new Book("1", "testBook1", 3);
        User user1 = new User("11", "JohnDoe");
        User user2 = new User("12", "JaneDoe");
        bookRepo.save(book);
        userRepo.save(user1);
        userRepo.save(user2);
        try {
            r.reserve(user1.getId(), book.getId());
            r.reserve(user2.getId(), book.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertEquals(2, r.listReservationsForBook(book.getId()).size());
    }

    @Test
    public void reservingLastCopyTest() {
        Book book = new Book("1", "testBook1", 1);
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        userRepo.save(user);
        try {
            r.reserve(user.getId(), book.getId());
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        assertEquals(0, book.getCopiesAvailable());
    }

    @Test
    public void userSetToPriorityUserTest() {
        User user = new User("11", "JohnDoe");
        userRepo.save(user);
        user.setPriority(true);
        assertTrue(user.getPriority());
    }

    @Test
    public void priorityUserAddedToWaitingListTest() {
        User user = new User("11", "JohnDoe");
        Book book = new Book("1", "testBook1", 0);
        bookRepo.save(book);
        userRepo.save(user);
        user.setPriority(true);
        r.reserve(user.getId(), book.getId());
        assertEquals(1, r.listWaitingListForBook(book.getId()).size());
    }

    @Test
    public void priorityUserRemovedFromWaitingListTest() {
        User user = new User("11", "JohnDoe");
        Book book = new Book("1", "testBook1", 0);
        bookRepo.save(book);
        userRepo.save(user);
        user.setPriority(true);
        r.reserve(user.getId(), book.getId());
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        assertEquals(0, r.listWaitingListForBook(book.getId()).size());
    }
}
