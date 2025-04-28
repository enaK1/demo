import com.example.demo.Entities.Book;
import com.example.demo.Entities.Loan;
import com.example.demo.Entities.Member;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoanTest {

    @Test
    public void testLoanDateManagement() {
        // Arrange
        Book book = new Book() {
            @Override
            public double lateFeeCalculator(LocalDate dueDate) {
                return 0;
            }
        };
        book.setBookId(1L);
        book.setTitle("Effective Java");

        Member member = new Member();
        member.setMemberId(1L);
        member.setName("John Doe");

        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setBook(book);
        loan.setMember(member);

        // Act - Set check out date
        LocalDate checkOutDate = LocalDate.now();
        loan.setCheckOutDate(checkOutDate);

        // Assert
        assertEquals(checkOutDate, loan.getCheckOutDate());
        assertNull(loan.getReturnDate());

        // Act - Set due date (2 weeks after check out)
        LocalDate dueDate = checkOutDate.plusDays(14);
        loan.setDueDate(dueDate);

        // Assert
        assertEquals(dueDate, loan.getDueDate());

        // Act - Set return date
        LocalDate returnDate = dueDate.minusDays(2); // Returned 2 days early
        loan.setReturnDate(returnDate);

        // Assert
        assertEquals(returnDate, loan.getReturnDate());
    }
}
