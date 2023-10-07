package com.gabrielag.bookwebsite.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gabriela Gutierrez
 */

// Class represents blueprint for creating 'Author' objects
public class Book {
    /**
     * Private variables = Instance variables.
     * Instance Variables.: variable specific to a certain object.
     * Use of encapsulation through these private instance variables
     * to enclose the data within them.
     * Control of Access or modification through public getter and setter methods
     * to protect the integrity of data.
     */
    private int id;
    // @NotBlank is used as validation constraints for the class fields
    @NotBlank(message = "Please enter book title!")
    private String title;
    @NotBlank(message = "Please enter book price!")
    private String price;
    // Composition: 'Book' class has a reference to an 'Author' Object
    private Author author;
    // Composition: 'Book' class has a reference to a list of 'Category' Objects
    private List<Category> categories;

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    // Enables comparison of two 'Book' objects for equality based on their attributes.
    // This is essential when working with collection of objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (!Objects.equals(price, book.price)) return false;
        if (!Objects.equals(author, book.author)) return false;
        return Objects.equals(categories, book.categories);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }
}
