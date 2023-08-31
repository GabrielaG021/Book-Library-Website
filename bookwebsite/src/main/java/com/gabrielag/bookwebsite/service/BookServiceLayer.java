package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dto.Author;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import com.gabrielag.bookwebsite.dto.User;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface BookServiceLayer {
    Book addBook(Book book);
    Book getBooksByID(int id);
    List<Book> getAllBooks();
    void updateBook(Book book);
    void deleteBookByID(int id);

    List<Book> getBooksByCategory(Category category);
    List<Book> getBooksByAuthor(Author author);
    List<Book> getBooksByUser(User user);

    void validateBook(Book book) throws DuplicateNameExistsException;
}
