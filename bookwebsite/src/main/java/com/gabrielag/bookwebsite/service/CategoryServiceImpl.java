package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dao.CategoryDao;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Service
public class CategoryServiceImpl implements CategoryServiceLayer{
    @Autowired
    CategoryDao categoryDao;

    @Override
    public Category addCategory(Category category) {
        return categoryDao.addCategory(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public void deleteCategoryByID(int id) {
        categoryDao.deleteCategoryByID(id);
    }

    @Override
    public List<Category> getCategoriesByBook(Book book) {
        return categoryDao.getCategoriesByBook(book);
    }

    @Override
    public void validateCategory(Category category) throws DuplicateNameExistsException {
        List<Category> categories = categoryDao.getAllCategories();
        boolean isDuplicate = false;

        for (Category singleCategory : categories) {
            if(singleCategory.getName().toLowerCase().equals(category.getName().toLowerCase())) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            throw new DuplicateNameExistsException("The category name already exists in our database system");
        }
    }
}
