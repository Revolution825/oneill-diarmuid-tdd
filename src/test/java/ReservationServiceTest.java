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
        Book book = new Book("1", "testBook", 0 );
        User user = new User("11", "JohnDoe");
        bookRepo.save(book);
        assertThrows(NoAvailableCopiesException.class,() -> {r.reserve(user.getId(), book.getId());});

    }
}
