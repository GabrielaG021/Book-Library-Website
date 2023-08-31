package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dto.Author;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface AuthorServiceLayer {
    Author addAuthor(Author author);
    Author getAuthorById(int id);
    List<Author> getAllAuthors();
    void updateAuthor(Author author);
    void deleteAuthorByID(int id);

    void validateAuthor(Author author) throws DuplicateNameExistsException;
}
