package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FictionBook.class, name = "Fiction"),
        @JsonSubTypes.Type(value = NonFictionBook.class, name ="Non-Fiction")
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String author;
    private boolean isCheckedOut;
    private LocalDate lastCheckOutDate;
    private LocalDate lastCheckInDate;

    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isCheckedOut = false;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }
    public LocalDate getLastCheckOutDate() {
        return lastCheckOutDate;
    }

    public void setLastCheckOutDate(LocalDate lastCheckOutDate) {
        this.lastCheckOutDate = lastCheckOutDate;
    }

    public LocalDate getLastCheckInDate() {
        return lastCheckInDate;
    }

    public void setLastCheckInDate(LocalDate lastCheckInDate) {
        this.lastCheckInDate = lastCheckInDate;
    }

    public abstract double lateFeeCalculator(LocalDate dueDate);
}
