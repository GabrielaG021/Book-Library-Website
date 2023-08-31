package com.gabrielag.bookwebsite.controller;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import com.gabrielag.bookwebsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Controller
public class CategoryController {
    @Autowired
    OrderServiceLayer orderService;

    @Autowired
    UserServiceLayer userService;

    @Autowired
    BookServiceLayer bookService;

    @Autowired
    AuthorServiceLayer authorService;

    @Autowired
    CategoryServiceLayer categoryService;

    @GetMapping("categories")
    public String displayCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("categories", categories);
        model.addAttribute("books", books);

        return "categories";
    }

    @GetMapping("addCategory")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping("addCategory")
    public String addCategory(@Valid Category category, BindingResult result) {
        // validations - field errors
        if (result.hasErrors()){
            return "addCategory";
        }

        try {
            categoryService.validateCategory(category);

            categoryService.addCategory(category);
        } catch(DuplicateNameExistsException e) {
            return "addCategory";
        }
        return "redirect:/categories";
    }

    @PostMapping("deleteCategory")
    public String deleteCategory(Integer id) {
        categoryService.deleteCategoryByID(id);

        return "redirect:/categories";
    }

    @GetMapping("viewCategory")
    public String viewCategory(Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);

        return "viewCategory";
    }

    @GetMapping("editCategory")
    public String editCategory(Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);

        model.addAttribute("category", category);

        return "editCategory";
    }

    @PostMapping("editCategory")
    public String performEditCategory(Category category, HttpServletRequest request) {
        categoryService.updateCategory(category);

        return "redirect:/categories";
    }
}
