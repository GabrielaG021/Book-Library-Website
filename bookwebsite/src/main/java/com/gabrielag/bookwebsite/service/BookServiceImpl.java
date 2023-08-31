package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dao.BookDao;
import com.gabrielag.bookwebsite.dto.Author;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import com.gabrielag.bookwebsite.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Service
public class BookServiceImpl implements BookServiceLayer {
    @Autowired
    BookDao bookDao;

    @Override
    public Book addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public Book getBooksByID(int id) {
        return bookDao.getBooksByID(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBookByID(int id) {
        bookDao.deleteBookByID(id);
    }

    @Override
    public List<Book> getBooksByCategory(Category category) {
        return bookDao.getBooksByCategory(category);
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return bookDao.getBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksByUser(User user) {
        return bookDao.getBooksByUser(user);
    }

    @Override
    public void validateBook(Book book) throws DuplicateNameExistsException {
        List<Book> books = bookDao.getAllBooks();
        boolean isDuplicate = false;

        for (Book singleBook : books) {
            if (singleBook.getTitle().toLowerCase().equals(book.getTitle().toLowerCase())) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            throw new DuplicateNameExistsException("The book title already exists in the database system");
        }
    }
}
