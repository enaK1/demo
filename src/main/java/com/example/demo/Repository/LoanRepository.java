package com.example.demo.Repository;

import com.example.demo.Entities.Book;
import com.example.demo.Entities.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @EntityGraph(attributePaths = {"member", "book"})
    List<Loan> findAll();

    @EntityGraph(attributePaths = {"member", "book"})
    List<Loan> findByMember_MemberId(Long memberId);

    @EntityGraph(attributePaths = {"member", "book"})
    List<Loan> findByBook_BookId(Long bookId);

    List<Loan> findByReturnDateIsNull();

    boolean existsByBookAndReturnDateIsNull(Book book);
}
