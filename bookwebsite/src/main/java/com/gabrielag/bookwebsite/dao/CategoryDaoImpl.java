package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import com.gabrielag.bookwebsite.dao.BookDaoImpl.BookMapper;
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

@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    JdbcTemplate jdbc;

    // SQL QUERIES
    final String GET_ALL_CATEGORIES = "SELECT * FROM category";
    final String ADD_CATEGORY = "INSERT INTO category (categoryName) VALUES (?)";
    final String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE CategoryId = ?";
    final String UPDATE_CATEGORY = "UPDATE category SET categoryName = ? WHERE CategoryId = ?";
    final String DELETE_CATEGORY = "DELETE FROM category WHERE CategoryId = ?";
    final String DELETE_FROM_BOOK_CATEGORY = "DELETE FROM book_category WHERE BookId = ?";
    final String GET_BOOKS_BY_CATEGORY = "SELECT DISTINCT b.* FROM book b INNER JOIN book_category bc ON b.BookId = bc.BookId WHERE bc.CategoryId = ?";
    final String GET_INSERT_ID = "SELECT LAST_INSERT_ID()";
    final String GET_CATEGORIES_BY_BOOK = "SELECT c.* FROM category c INNER JOIN book_category bc ON c.CategoryId = bc.CategoryId WHERE bc.BookId = ?";


    @Override
    public Category addCategory(Category category) {

        jdbc.update(ADD_CATEGORY,
                category.getName());
        int newId = jdbc.queryForObject(GET_INSERT_ID, Integer.class);
        category.setId(newId);
        return category;
    }

    @Override
    public Category getCategoryById(int id) {
        try {
            Category category = jdbc.queryForObject(GET_CATEGORY_BY_ID, new CategoryMapper(), id);
            category.setBooks(getBooksByCategory(category));
            return category;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = jdbc.query(GET_ALL_CATEGORIES, new CategoryMapper());

        for (Category category : categories) {
            category.setBooks(getBooksByCategory(category));
        }
        return categories;
    }

    @Override
    public void updateCategory(Category category) {
        jdbc.update(UPDATE_CATEGORY,
                category.getName(),
                category.getId());
    }

    @Override
    @Transactional
    public void deleteCategoryByID(int id) {
        jdbc.update(DELETE_FROM_BOOK_CATEGORY, id);
        jdbc.update(DELETE_CATEGORY, id);
    }

    @Override
    public List<Category> getCategoriesByBook(Book book) {
        List<Category> categories = jdbc.query(GET_CATEGORIES_BY_BOOK, new CategoryMapper(), book.getId());

        for (Category category : categories) {
            category.setBooks(getBooksByCategory(category));
        }

        return categories;
    }


    // HELPER METHODS
    private List<Book> getBooksByCategory(Category category) {
        return jdbc.query(GET_BOOKS_BY_CATEGORY, new BookMapper(), category.getId());
    }


    // CATEGORY MAPPER
    /**
     * Mappers are responsible for mapping the result set from a database
     * query to a specific object or entity.
     * Encapsulates the mapping logic within the class.
     */
    public static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("CategoryId"));
            category.setName(rs.getString("categoryName"));

            return category;
        }
    }
}
