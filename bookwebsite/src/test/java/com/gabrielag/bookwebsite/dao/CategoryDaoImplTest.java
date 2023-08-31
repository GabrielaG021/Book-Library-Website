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
public class CategoryDaoImplTest {
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

    public CategoryDaoImplTest() {
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
     * Test of getCategoryByID method & addCategory method, of class CategoryDaoImpl.
     */
    @Test
    public void testAddAndGetCategoryByID() {
        Category category = new Category();
        category.setName("Fantasy");
        category.setBooks(new ArrayList<>());
        category = categoryDao.addCategory(category);

        Category added = categoryDao.getCategoryById(category.getId());
        Assertions.assertEquals(category, added);
    }

    /**
     * Test of  getAllCategories method, of class CategoryDaoImpl.
     */
    @Test
    public void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("Fantasy");
        category1.setBooks(new ArrayList<>());
        category1 = categoryDao.addCategory(category1);

        Category category2 = new Category();
        category2.setName("Romance");
        category2.setBooks(new ArrayList<>());
        category2 = categoryDao.addCategory(category2);

        List<Category> categories = categoryDao.getAllCategories();

        Assertions.assertEquals(2, categories.size());
        Assertions.assertTrue(categories.contains(category1));
        Assertions.assertTrue(categories.contains(category2));
    }

    /**
     * Test of updateCategory method, of class CategoryDaoImpl.
     */
    @Test
    public void testUpdateCategory() {
        Category category = new Category();
        category.setName("Fantasy");
        category.setBooks(new ArrayList<>());
        category = categoryDao.addCategory(category);

        Category updated = categoryDao.getCategoryById(category.getId());
        Assertions.assertEquals(category, updated);

        category.setName("Mystery");
        categoryDao.updateCategory(category);
        Assertions.assertNotEquals(category, updated);

        updated = categoryDao.getCategoryById(category.getId());
        Assertions.assertEquals(category, updated);
    }

    /**
     * Test of deleteCategoryByID method, of class CategoryDaoImpl.
     */
    @Test
    public void testDeleteCategoryById() {
        Category category = new Category();
        category.setName("Fantasy");
        category.setBooks(new ArrayList<>());
        category = categoryDao.addCategory(category);

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        authorDao.addAuthor(author);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        book.setCategories(categories);
        book = bookDao.addBook(book);

        // Before deleting the category, remove its association from the book
        List<Category> bookCategories = book.getCategories();
        bookCategories.remove(category);
        book.setCategories(bookCategories);
        bookDao.updateBook(book);

        categoryDao.deleteCategoryByID(category.getId());

        Category deleted = categoryDao.getCategoryById(category.getId());
        Assertions.assertNull(deleted);
    }

    /**
     * Test of getCategoryByBook method, of class CategoryDaoImpl.
     */
    @Test
    public void testGetCategoriesByBook() {
        Category category = new Category();
        category.setName("Fantasy");
        category.setBooks(new ArrayList<>());
        category = categoryDao.addCategory(category);

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice("19.99");
        book.setAuthor(author);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        book.setCategories(categories);

        // Before adding the book, ensure that the author exists in the database
        if (author.getId() == 0) {
            author = authorDao.addAuthor(author);
        }

        book = bookDao.addBook(book);

        List<Category> categoriesByBook = categoryDao.getCategoriesByBook(book);

        Assertions.assertNotNull(categoriesByBook);
        Assertions.assertEquals(1, categoriesByBook.size());
        Assertions.assertEquals(category.getId(), categoriesByBook.get(0).getId());
    }
}
