package com.gabrielag.bookwebsite.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gabriela Gutierrez
 */

public class Category {
    /**
     * Private variables = Instance variables.
     * Instance Variables.: variable specific to a certain object.
     * Use of encapsulation through these private instance variables
     * to enclose the data within them.
     * Control of Access or modification through public getter and setter methods
     * to protect the integrity of data.
     */
    private int id;
    @NotBlank(message = "Please input name for category!")
    private String name;
    // Composition: 'Category' class has a reference to a list of 'Book' Objects
    private List<Book> books;

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Enables comparison of two 'Category' objects for equality based on their attributes.
    // This is essential when working with collection of objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (!Objects.equals(name, category.name)) return false;
        return Objects.equals(books, category.books);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (books != null ? books.hashCode() : 0);
        return result;
    }
}
