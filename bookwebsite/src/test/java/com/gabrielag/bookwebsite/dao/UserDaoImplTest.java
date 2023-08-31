package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@SpringBootTest
public class UserDaoImplTest {
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

    LocalDateTime date = LocalDateTime.now();

    public UserDaoImplTest() {
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
     * Test of getUserByID method & addUser method, of class UserDaoImpl.
     */
    @Test
    public void testAddAndGetUserByID() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAddress("123 Main St");
        user.setPhone("201-555-1234");
        user = userDao.addUser(user);

        User added = userDao.getUserByID(user.getId());
        Assertions.assertEquals(user, added);
    }

    /**
     * Test of getAllUsers method, of class UserDaoImpl.
     */
    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setAddress("123 Main St");
        user1.setPhone("201-555-1234");
        user1 = userDao.addUser(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setAddress("456 Oak St");
        user2.setPhone("305-555-5678");
        user2 = userDao.addUser(user2);

        List<User> users = userDao.getAllUsers();

        Assertions.assertEquals(2, users.size());
        Assertions.assertTrue(users.contains(user1));
        Assertions.assertTrue(users.contains(user2));
    }

    /**
     * Test of updateUser method, of class UserDaoImpl.
     */
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAddress("123 Main St");
        user.setPhone("201-555-1234");
        user = userDao.addUser(user);

        User updated = userDao.getUserByID(user.getId());
        Assertions.assertEquals(user, updated);

        user.setFirstName("Gabriela");
        user.setPhone("305-824-9889");
        userDao.updateUser(user);
        Assertions.assertNotEquals(user, updated);

        updated = userDao.getUserByID(user.getId());
        Assertions.assertEquals(user, updated);
    }

    /**
     * Test of deleteUserById method, of class UserDaoImpl.
     */
    @Test
    public void testDeleteUserByID() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Category category = new Category();
        category.setName("Fantasy");
        category = categoryDao.addCategory(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        book.setCategories(categories);
        book = bookDao.addBook(book);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAddress("123 Main St");
        user.setPhone("201-555-1234");
        user = userDao.addUser(user);

        Order order = new Order();
        order.setOrderDate(date);
        order.setTotal("23.00");
        order.setPaid(Boolean.TRUE);
        order.setUser(user);
        order.setBook(book);
        order = orderDao.addOrder(order);

        // Delete the user by ID
        userDao.deleteUserByID(user.getId());

        // Try to retrieve the user from order after deletion
        Order deletedOrder = orderDao.getOrderByID(order.getId());
        Assertions.assertNull(deletedOrder);

        // Check if the retrieved user is null after deletion
        User deletedUser = userDao.getUserByID(user.getId());
        Assertions.assertNull(deletedUser);
    }

    /**
     * Test of getUsersByBook method, of class UserDaoImpl.
     */
    @Test
    public void testGetUsersByBook() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Category category = new Category();
        category.setName("Fantasy");
        category = categoryDao.addCategory(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        book.setCategories(categories);
        book = bookDao.addBook(book);

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setAddress("123 Main St");
        user1.setPhone("201-555-1234");
        user1 = userDao.addUser(user1);

        User user2 = new User();
        user2.setFirstName("Gabriela");
        user2.setLastName("Doe");
        user2.setAddress("13232 Main Ave.");
        user2.setPhone("305-824-7898");
        user2 = userDao.addUser(user2);

        Order order1 = new Order();
        order1.setOrderDate(date);
        order1.setTotal("23.00");
        order1.setPaid(Boolean.TRUE);
        order1.setUser(user1);
        order1.setBook(book);
        orderDao.addOrder(order1);

        Order order2 = new Order();
        order2.setOrderDate(date);
        order2.setTotal("23.00");
        order2.setPaid(Boolean.TRUE);
        order2.setUser(user2);
        order2.setBook(book);
        orderDao.addOrder(order2);

        List<User> users = userDao.getUsersByBook(book);

        Assertions.assertEquals(2, users.size());
        Assertions.assertTrue(users.contains(user1));
        Assertions.assertTrue(users.contains(user2));
    }
}
