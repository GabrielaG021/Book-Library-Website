package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dao.AuthorDao;
import com.gabrielag.bookwebsite.dto.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Service
public class AuthorServiceImpl implements AuthorServiceLayer {
    @Autowired
    AuthorDao authorDao;

    @Override
    public Author addAuthor(Author author) {
        return authorDao.addAuthor(author);
    }

    @Override
    public Author getAuthorById(int id) {
        return authorDao.getAuthorById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.updateAuthor(author);
    }

    @Override
    public void deleteAuthorByID(int id) {
        authorDao.deleteAuthorByID(id);
    }

    @Override
    public void validateAuthor(Author author) throws DuplicateNameExistsException {
        List<Author> authors = authorDao.getAllAuthors();
        Boolean isDuplicate = false;

        for (Author singleAuthor : authors) {
            if(singleAuthor.getFirstName().toLowerCase().equals(author.getFirstName().toLowerCase())
            && singleAuthor.getLastName().toLowerCase().equals(author.getLastName().toLowerCase())) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            throw new DuplicateNameExistsException("The author full name already exists in our database system.");
        }
    }
}
