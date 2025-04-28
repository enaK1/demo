import com.example.demo.Entities.Book;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class BookTest {

    @Test
    public void testBookCheckOutStatus() {
        // Arrange
        Book book = new Book() {
            @Override
            public double lateFeeCalculator(LocalDate dueDate) {
                return 0;
            }
        };
        book.setBookId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");

        // Initial state
        assertFalse(book.isCheckedOut());
        assertNull(book.getLastCheckOutDate());
        assertNull(book.getLastCheckInDate());

        // Act - Check out the book
        book.setCheckedOut(true);
        LocalDate checkOutDate = LocalDate.now();
        book.setLastCheckOutDate(checkOutDate);

        // Assert
        assertTrue(book.isCheckedOut());
        assertEquals(checkOutDate, book.getLastCheckOutDate());

        // Act - Check in the book
        book.setCheckedOut(false);
        LocalDate checkInDate = LocalDate.now().plusDays(14);
        book.setLastCheckInDate(checkInDate);

        // Assert
        assertFalse(book.isCheckedOut());
        assertEquals(checkInDate, book.getLastCheckInDate());
    }
}
