package com.gabrielag.bookwebsite.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Gabriela Gutierrez
 */

public class Order {
    private int id;
    private LocalDateTime orderDate;
    private String total;
    private Boolean isPaid;
    private User user;
    private Book book;

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Boolean isPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (!Objects.equals(orderDate, order.orderDate)) return false;
        if (!Objects.equals(total, order.total)) return false;
        if (!Objects.equals(isPaid, order.isPaid)) return false;
        if (!Objects.equals(user, order.user)) return false;
        if (!Objects.equals(book, order.book)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (isPaid != null ? isPaid.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}
