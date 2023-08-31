package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface CategoryDao {
    Category addCategory(Category category);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    void updateCategory(Category category);
    void deleteCategoryByID(int id);

    List<Category> getCategoriesByBook(Book book);
}
