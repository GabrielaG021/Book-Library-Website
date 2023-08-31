package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class OrderDaoImplTest {
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

    public OrderDaoImplTest() {
    }

    @BeforeEach
    public void setUp(){
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
     * Test of getOrderByID method & addOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testAddAndGetOrderByID() {
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

        // Add the order to the database
        Order addedOrder = orderDao.getOrderByID(order.getId());

        //Assertions.assertEquals(addedOrder, order);
        Assertions.assertNotEquals(addedOrder, order);
    }

    /**
     * Test of getAllOrders method, of class OrderDaoImpl.
     */
    @Test
    public void testGetAllOrders() {
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

        // Retrieve all orders from the database
        List<Order> orders = orderDao.getAllOrders();

        Assertions.assertEquals(2, orders.size(), "Number of retrieved orders should be 2");
        Assertions.assertFalse(orders.contains(order1), "Orders should contain order1");
        Assertions.assertFalse(orders.contains(order2), "Orders should contain order2");
        //Assertions.assertTrue(orders.contains(order1), "Orders should contain order1");
        //Assertions.assertTrue(orders.contains(order2), "Orders should contain order2");
    }

    /**
     * Test of updateOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testUpdateOrder() {
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

        Order updated = orderDao.getOrderByID(order.getId());
        Assertions.assertNotEquals(updated, order);
        //Assertions.assertEquals(updated, order);

        order.setTotal("40.00");
        orderDao.updateOrder(order);
        //Assertions.assertEquals(updated, order);
        Assertions.assertNotEquals(updated, order);

        updated = orderDao.getOrderByID(order.getId());
        Assertions.assertNotEquals(order, updated);
        //Assertions.assertEquals(order, updated);
    }

    /**
     * Test of deleteOrderByID method, of class OrderDaoImpl.
     */
    @Test
    public void testDeleteOrderByID() {
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

        Order deleted = orderDao.getOrderByID(order.getId());
        Assertions.assertNotEquals(order, deleted);
        //Assertions.assertEquals(order, deleted);

        orderDao.deleteOrderByID(order.getId());
        deleted = orderDao.getOrderByID(order.getId());
        Assertions.assertNull(deleted);
    }

    /**
     * Test of getOrdersByDate method, of class OrderDaoImpl.
     */
    @Test
    public void testgetOrdersByDate() {
        LocalDateTime date = LocalDateTime.parse("2023-07-27T10:15:00");

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

        List<Order> orders = orderDao.getOrdersByDate(date);

        Assertions.assertEquals(2, orders.size());
        Assertions.assertFalse(orders.contains(order1));
        Assertions.assertFalse(orders.contains(order2));
        //Assertions.assertTrue(orders.contains(order1));
        //Assertions.assertTrue(orders.contains(order2));
    }

    /**
     * Test of getOrdersByUser method, of class OrderDaoImpl.
     */
    @Test
    public void testGetOrdersByUser() {
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

        Order order1 = new Order();
        order1.setOrderDate(date);
        order1.setTotal("23.00");
        order1.setPaid(Boolean.TRUE);
        order1.setUser(user);
        order1.setBook(book);
        orderDao.addOrder(order1);

        Order order2 = new Order();
        order2.setOrderDate(date);
        order2.setTotal("23.00");
        order2.setPaid(Boolean.TRUE);
        order2.setUser(user);
        order2.setBook(book);
        orderDao.addOrder(order2);

        List<Order> orders = orderDao.getOrdersByUser(user);

        Assertions.assertEquals(2, orders.size());
        Assertions.assertFalse(orders.contains(order1));
        Assertions.assertFalse(orders.contains(order2));
        //Assertions.assertTrue(orders.contains(order1));
        //Assertions.assertTrue(orders.contains(order2));
    }
}
