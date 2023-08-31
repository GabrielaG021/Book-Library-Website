package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@SpringBootTest
public class BookDaoImplTest {
    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;


    public BookDaoImplTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Author> authors = authorDao.getAllAuthors();
        authors.forEach(author -> {
            authorDao.deleteAuthorByID(author.getId());
        });

        List<Book> books = bookDao.getAllBooks();
        books.forEach(book -> {
            bookDao.deleteBookByID(book.getId());
        });

        List<Category> categories = categoryDao.getAllCategories();
        categories.forEach(category -> {
            categoryDao.deleteCategoryByID(category.getId());
        });

        List<User> users = userDao.getAllUsers();
        users.forEach(user -> {
            userDao.deleteUserByID(user.getId());
        });

        List<Order> orders = orderDao.getAllOrders();
        orders.forEach(order -> {
            orderDao.deleteOrderByID(order.getId());
        });

    }

    /**
     * Test of getBookByID method & addBook method, of class BookDaoImpl.
     */
    @Test
    public void testAddAndGetBookByID() {
        // Create author and add it to database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Category category = new Category();
        category.setName("Fiction");
        category = categoryDao.addCategory(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        book.setCategories(categories);
        book = bookDao.addBook(book);

        Book added = bookDao.getBooksByID(book.getId());
        Assertions.assertEquals(book, added);
    }

    /**
     * Test of getAllBooks method, of class BookDaoImpl.
     */
    @Test
    public void testGetAllBooks() {
        // Create author and add it to database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Category category = new Category();
        category.setName("Fiction");
        category = categoryDao.addCategory(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPrice("20.00");
        book1.setAuthor(author);
        book1.setCategories(categories);
        bookDao.addBook(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPrice("15.99");
        book2.setAuthor(author);
        book2.setCategories(categories);
        bookDao.addBook(book2);

        List<Book> books = bookDao.getAllBooks();

        Assertions.assertEquals(2, books.size());
        Assertions.assertTrue(books.contains(book1));
        Assertions.assertTrue(books.contains(book2));
    }

    /**
     * Test of updateBook method, of class BookDaoImpl.
     */
    @Test
    public void testUpdateBook() {
        // Create author and add it to database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Category category = new Category();
        category.setName("Fiction");
        category = categoryDao.addCategory(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        book.setCategories(categories);
        book = bookDao.addBook(book);

        Book updated = bookDao.getBooksByID(book.getId());
        Assertions.assertEquals(book, updated);

        book.setPrice("24.99");
        bookDao.updateBook(book);
        Assertions.assertNotEquals(book, updated);

        updated = bookDao.getBooksByID(book.getId());
        Assertions.assertEquals(book, updated);
    }

    /**
     * Test of deleteBookByID method, of class BookDaoImpl.
     */
    @Test
    public void testDeleteBookByID() {
        // Add an author to the database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        authorDao.addAuthor(author);

        // Add a category to the database
        Category category = new Category();
        category.setName("Fiction");
        categoryDao.addCategory(category);

        // Add a book to the database
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        book.setCategories(categories);
        bookDao.addBook(book);

        // Get the book by ID
        Book retrievedBook = bookDao.getBooksByID(book.getId());

        // Check if the retrieved book matches the added book
        Assertions.assertEquals(book, retrievedBook);

        // Delete the book by ID
        bookDao.deleteBookByID(book.getId());

        // Try to retrieve the deleted book by ID
        Book deletedBook = bookDao.getBooksByID(book.getId());

        // Check if the deleted book is null
        Assertions.assertNull(deletedBook);
    }
}
