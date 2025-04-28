package com.example.demo.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@DiscriminatorValue("FICTION")
public class FictionBook extends Book{

    public FictionBook() {}

    public FictionBook(String title, String author) {
        super(title, author);
    }

    @Override
    public double lateFeeCalculator(LocalDate dueDate) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, today);
            return daysLate * 0.50; // $0.50 late fee per day for fiction books
        }
        return 0.0;
    }
}
