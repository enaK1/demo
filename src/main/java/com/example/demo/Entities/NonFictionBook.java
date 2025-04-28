package com.example.demo.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@DiscriminatorValue("NON_FICTION")
public class NonFictionBook extends Book {
    public NonFictionBook() {}

    public NonFictionBook(String title, String author) {
        super(title, author);
    }

    @Override
    public double lateFeeCalculator(LocalDate dueDate) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, today);
            return daysLate * 0.75; // $0.75 late fee per day for non-fiction books
        }
        return 0.0;
    }
}
