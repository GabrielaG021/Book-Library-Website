package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Author;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import com.gabrielag.bookwebsite.dao.AuthorDaoImpl.AuthorMapper;
import com.gabrielag.bookwebsite.dao.CategoryDaoImpl.CategoryMapper;
import com.gabrielag.bookwebsite.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

// Responsible for interacting with and managing all author-related data.
@Repository
public class BookDaoImpl implements BookDao {
    /**
     * Class that allows you to perform CRUD operations (Create, Read, Update, Delete) related to author data.
     * includes adding authors, retrieving authors by ID, retrieving all authors, updating author information,
     * and deleting authors.
     * */
    // Dependency Injection that simplified database operations
    @Autowired
    JdbcTemplate jdbc;

    // SQL QUERIES
    final String GET_ALL_BOOKS = "SELECT * FROM book";
    final String GET_AUTHOR_FOR_BOOK = "SELECT a.* FROM author a INNER JOIN book b ON a.AuthorId = b.AuthorId WHERE b.BookId = ?";
    final String GET_CATEGORIES_FOR_BOOK = "SELECT c.* FROM category c INNER JOIN book_category bc ON c.CategoryId = bc.CategoryId WHERE bc.BookId = ?";
    final String GET_BOOK_BY_ID = "SELECT * FROM book WHERE BookId = ?";
    final String DELETE_FROM_BOOK_CATEGORY = "DELETE FROM book_category WHERE BookId = ?";
    final String DELETE_FROM_ORDER = "DELETE FROM `order` WHERE BookId = ?";
    final String DELETE_BOOK = "DELETE FROM book WHERE BookId = ?";
    final String UPDATE_BOOK = "UPDATE book SET title = ?, price = ?, AuthorId = ? WHERE BookId = ?";
    final String INSERT_BOOKCATEGORY = "INSERT INTO book_category (BookId, CategoryId) VALUES (?, ?)";
    final String ADD_BOOK = "INSERT INTO book (title, price, AuthorId) VALUES (?, ?, ?)";
    final String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    final String GET_BOOKS_BY_CATEGORY = "SELECT DISTINCT b.* FROM book b INNER JOIN book_category bc ON b.BookId = bc.BookId WHERE bc.CategoryId = ?";
    final String GET_BOOKS_BY_AUTHOR = "SELECT * FROM book WHERE AuthorId = ?";
    final String GET_BOOKS_BY_USER = "SELECT b.* FROM book b INNER JOIN `order` o ON b.BookId = o.BookId WHERE o.UserId = ?";

    @Override
    @Transactional
    public Book addBook(Book book) {
        jdbc.update(ADD_BOOK,
                book.getTitle(),
                book.getPrice(),
                book.getAuthor().getId());

        int newId = jdbc.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        book.setId(newId);
        addBookToBookCategory(book);

        return book;
    }

    @Override
    public Book getBooksByID(int id) {
        try {
            Book book = jdbc.queryForObject(GET_BOOK_BY_ID, new BookMapper(), id);
            book.setAuthor(getAuthorForBook(id));
            book.setCategories(getCategoriesForBook(id));

            return book;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = jdbc.query(GET_ALL_BOOKS, new BookMapper());
        for (Book book : books) {
            book.setAuthor(getAuthorForBook(book.getId()));
            book.setCategories(getCategoriesForBook(book.getId()));
        }
        return books;
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        jdbc.update(UPDATE_BOOK,
                book.getTitle(),
                book.getPrice(),
                book.getAuthor().getId(),
                book.getId());

        jdbc.update(DELETE_FROM_BOOK_CATEGORY, book.getId());

        addBookToBookCategory(book);
    }

    @Override
    @Transactional
    public void deleteBookByID(int id) {
        // First, Delete associations of the Book with Category in book_category table
        jdbc.update(DELETE_FROM_BOOK_CATEGORY, id);

        // Second, delete associations of the Book with Order
        jdbc.update(DELETE_FROM_ORDER, id);

        // Third, delete the book record from the book table
        jdbc.update(DELETE_BOOK, id);
    }

    @Override
    public List<Book> getBooksByCategory(Category category) {
        List<Book> books = jdbc.query(GET_BOOKS_BY_CATEGORY, new BookMapper(), category.getId());

        for (Book book : books) {
            book.setAuthor(getAuthorForBook(book.getId()));
            book.setCategories(getCategoriesForBook(book.getId()));
        }
        return books;
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return jdbc.query(GET_BOOKS_BY_AUTHOR, new BookMapper(), author.getId());
    }

    @Override
    public List<Book> getBooksByUser(User user) {
        List<Book> books = jdbc.query(GET_BOOKS_BY_USER, new BookMapper(), user.getId());
        for (Book book : books){
            book.setAuthor(getAuthorForBook(book.getId()));
            book.setCategories(getCategoriesForBook(book.getId()));
        }
        return books;
    }


    // Helper Methods
    private void addBookToBookCategory(Book book) {
        for (Category cat : book.getCategories()) {
            jdbc.update(INSERT_BOOKCATEGORY, book.getId(), cat.getId());
        }
    }

    private List<Category> getCategoriesForBook(int bookId) {
        List<Category> bookCategories = jdbc.query(GET_CATEGORIES_FOR_BOOK, new CategoryMapper(), bookId);
        return bookCategories;
    }

    private Author getAuthorForBook(int bookId) {
        try {
            return jdbc.queryForObject(GET_AUTHOR_FOR_BOOK, new AuthorMapper(), bookId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    // Book Mapper
    /**
     * Mappers are responsible for mapping the result set from a database
     * query to a specific object or entity.
     * Encapsulates the mapping logic within the class.
     */
    public static final class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("BookId"));
            book.setTitle(rs.getString("title"));
            book.setPrice(rs.getString("price"));
            return book;
        }
    }
}
