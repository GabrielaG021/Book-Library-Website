package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Author;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
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
public class AuthorDaoImplTest {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    CategoryDao categoryDao;

    public AuthorDaoImplTest() {
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
    }

    /**
     * Test of addAuthor method & getAuthorByID method, of class AuthorDaoImpl.
     */
    @Test
    public void testAddAndGetAuthorByID() {
        // Create author and add it to database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        Author added = authorDao.getAuthorById(author.getId());
        Assertions.assertEquals(author, added);
    }

    /**
     * Test of getAllAuthors method, of class AuthorDaoImpl.
     */
    @Test
    public void testGetAllAuthors() {
        // Add two authors to database
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        author1.setDescription("A talented author");
        author1 = authorDao.addAuthor(author1);

        Author author2 = new Author();
        author2.setFirstName("Jane");
        author2.setLastName("Smith");
        author2.setDescription("An experienced writer");
        author2 = authorDao.addAuthor(author2);

        // Retrieve all authors from database
        List<Author> authors = authorDao.getAllAuthors();

        // CHeck if list is not null and contains both authors
        Assertions.assertEquals(3, authors.size());
        Assertions.assertTrue(authors.contains(author1));
        Assertions.assertTrue(authors.contains(author2));
    }

    /**
     * Test of updateAuthor method, of class AuthorDaoImpl.
     */
    @Test
    public void testUpdateAuthor() {
        // Create an author and add it to the database
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDescription("A talented author");
        author = authorDao.addAuthor(author);

        // Update author's properties
        Author updated = authorDao.getAuthorById(author.getId());
        Assertions.assertEquals(author, updated);

        author.setFirstName("Diane");
        author.setLastName("Gomez");
        author.setDescription("A gorgeous and dramatic author");
        authorDao.updateAuthor(author);
        Assertions.assertNotEquals(author, updated);

        updated = authorDao.getAuthorById(author.getId());
        Assertions.assertEquals(author, updated);
    }

    /**
     * Test of deleteAuthorByID method, of class AuthorDaoImpl.
     */
    @Test
    public void testDeleteAuthorByID() {
        // Create an author and add it to the database
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
