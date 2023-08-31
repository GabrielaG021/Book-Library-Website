package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Author;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface AuthorDao {
    Author addAuthor(Author author);
    Author getAuthorById(int id);
    List<Author> getAllAuthors();
    void updateAuthor(Author author);
    void deleteAuthorByID(int id);
}
