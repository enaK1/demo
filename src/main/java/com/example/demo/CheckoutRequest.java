package com.example.demo;

public class CheckoutRequest {
    private Long memberId;
    private Long bookId;

    // Getters and setters
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
}
