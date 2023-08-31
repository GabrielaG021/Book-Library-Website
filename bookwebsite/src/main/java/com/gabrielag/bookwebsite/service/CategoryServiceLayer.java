package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface CategoryServiceLayer {
    Category addCategory(Category category);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    void updateCategory(Category category);
    void deleteCategoryByID(int id);

    List<Category> getCategoriesByBook(Book book);

    void validateCategory(Category category) throws DuplicateNameExistsException;
}
