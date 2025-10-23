package main.java;

import java.util.List;

public class ReservationService {

    private final IBookRepository bookRepo;
    private final IReservationRepository reservationRepo;
    private final IUserRepository userRepo;

    public ReservationService(IBookRepository bookRepo, IReservationRepository reservationRepo,
            IUserRepository userRepo) {
        this.bookRepo = bookRepo;
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
    }

    /**
     * Reserve a book for a user.
     * Throws IllegalArgumentException if book not found.
     * Throws IllegalStateException if no copies available or user already reserved.
     */
    public void reserve(String userId, String bookId) throws IllegalStateException, IllegalArgumentException {
        if (bookRepo.findById(bookId) == null) {
            throw new IllegalArgumentException("Book not found");
        }
        Book book = bookRepo.findById(bookId);
        User user = userRepo.findUserById(userId);
        if (book.getCopiesAvailable() <= 0) {
            if (user.getPriority()) {
                book.addToWaitingList(user);
            } else {
                throw new IllegalStateException("No Available Copies");
            }
        } else {
            if (reservationRepo.existsByUserAndBook(userId, bookId)) {
                throw new IllegalStateException("User has already reserved this book");
            }
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            Reservation reservation = new Reservation(userId, bookId);
            reservationRepo.save(reservation);
        }

    }

    /**
     * Cancel an existing reservation for a user.
     * Throws IllegalArgumentException if no such reservation exists.
     */
    public void cancel(String userId, String bookId) throws IllegalArgumentException {
        Book book = bookRepo.findById(bookId);
        if (!reservationRepo.existsByUserAndBook(userId, bookId)) {
            throw new IllegalArgumentException("Reservation does not exist");
        }
        if (!book.getWaitingList().isEmpty()) {
            book.removeFromWaitingList();
        } else {
            book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        }
        reservationRepo.delete(userId, bookId);
    }

    /**
     * List all active reservations for a given user.
     */
    public List<Reservation> listReservations(String userId) {
        return reservationRepo.findByUser(userId);
    }

    /**
     * list all reservations for a book.
     */
    public List<Reservation> listReservationsForBook(String bookId) {
        return reservationRepo.findByBook(bookId);
    }

    public List<User> listWaitingListForBook(String bookId) {
        Book book = bookRepo.findById(bookId);
        return book.getWaitingList();
    }
}
