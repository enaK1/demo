package com.example.demo.Rest;

import com.example.demo.CheckoutRequest;
import com.example.demo.Entities.Book;
import com.example.demo.Entities.Loan;
import com.example.demo.Entities.Member;
import com.example.demo.ExceptionHandler.ResourceNotFoundException;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.LoanRepository;
import com.example.demo.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @GetMapping("/member/{memberId}")
    public List<Loan> getLoansByMemberId(@PathVariable Long memberId) {
        return loanRepository.findByMember_MemberId(memberId);
    }

    @GetMapping("/book/{bookId}")
    public List<Loan> getLoansByBookId(@PathVariable Long bookId) {
        return loanRepository.findByBook_BookId(bookId);
    }

    @GetMapping("/available-books")
    public List<Book> getAvailableBooks() {
        return bookRepository.findByIsCheckedOutFalse();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> activeLoans = loanRepository.findByReturnDateIsNull();
        return ResponseEntity.ok(activeLoans);
    }

    @GetMapping("/{loanId}/late-fee")
    public ResponseEntity<Double> calculateLateFee(@PathVariable Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        // This will automatically use the correct implementation based on book type
        double lateFee = loan.getBook().lateFeeCalculator(loan.getDueDate());
        return ResponseEntity.ok(lateFee);
    }

    @PostMapping
    public ResponseEntity<?> checkoutBook(@RequestBody CheckoutRequest request) {
        try {
            Book book = bookRepository.findById(request.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.isCheckedOut()) {
                return ResponseEntity.badRequest().body("Book already checked out");
            }

            Member member = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            Loan loan = new Loan();
            loan.setBook(book);
            loan.setMember(member);
            loan.setCheckOutDate(LocalDate.now());
            loan.setDueDate(LocalDate.now().plusDays(2));

            book.setCheckedOut(true);
            bookRepository.save(book);

            Loan savedLoan = loanRepository.save(loan);
            return ResponseEntity.ok(savedLoan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{loanId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Book book = loan.getBook();
        book.setCheckedOut(false);
        bookRepository.save(book);

        System.out.println("Returning book ID: " + book.getBookId() +
                ", New status: " + book.isCheckedOut());

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        return ResponseEntity.ok("Book returned successfully");
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long loanId) {
        try {
            if (!loanRepository.existsById(loanId)) {
                throw new ResourceNotFoundException("Loan not found with id: " + loanId);
            }
            loanRepository.deleteById(loanId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
