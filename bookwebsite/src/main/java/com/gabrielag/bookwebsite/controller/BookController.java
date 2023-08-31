package com.gabrielag.bookwebsite.controller;

import com.gabrielag.bookwebsite.dto.Author;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Category;
import com.gabrielag.bookwebsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Controller
public class BookController {
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

    @GetMapping("books")
    public String displayBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<Author> authors = authorService.getAllAuthors();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);

        return "books";
    }

    @GetMapping("addBook")
    public String addBook(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        // Add a new Book object to the model for form-backing
        model.addAttribute("book", new Book());

        return "addBook";
    }

    @PostMapping("addBook")
    public String addBook(@Valid Book book, BindingResult result, HttpServletRequest request, Model model) {
//        if (result.hasErrors()) {
//            List<Author> authors = authorService.getAllAuthors();
//            List<Category> categories = categoryService.getAllCategories();
//
//            model.addAttribute("authors", authors);
//            model.addAttribute("categories", categories);
//            return "addBook";
//        }
//
//        bookService.addBook(book);
//
//        return "redirect:/books";

        String authorId = request.getParameter("authorId");
        String[] categoryIds = request.getParameterValues("categoryId");

        book.setAuthor(authorService.getAuthorById(Integer.parseInt(authorId)));

        if (categoryIds == null) {
            FieldError error = new FieldError("book", "categories", "Book must have at least one category");
            result.addError(error);
        } else {
            List<Category> categories = new ArrayList<>();
            for(String categoryId : categoryIds) {
                categories.add(categoryService.getCategoryById(Integer.parseInt(categoryId)));
            }
            book.setCategories(categories);
        }

        if(result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
        }

        bookService.addBook(book);

        return "redirect:/books";
    }

    @PostMapping("deleteBook")
    public String deleteBook(Integer id) {
        bookService.deleteBookByID(id);

        return "redirect:/books";
    }

    @GetMapping("viewBook")
    public String viewBook(Integer id, Model model) {
        Book book = bookService.getBooksByID(id);

        if (book != null) {
            model.addAttribute("book", book);
            return "viewBook";
        } else {
            return "redirect:/books";
        }
    }

    @GetMapping("editBook")
    public String editBook(Integer id, Model model) {
        Book book = bookService.getBooksByID(id);

        if (book != null) {
            List<Author> authors = authorService.getAllAuthors();
            List<Category> categories = categoryService.getAllCategories();

            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            return "editBook";
        } else {
            return "redirect:/books";
        }
    }

    @PostMapping("editBook")
    public String performEditBook(@Valid Book book, BindingResult result, HttpServletRequest request, Model model) {
        String authorId = request.getParameter("authorId");
        String[] categoryIds = request.getParameterValues("categoryId");

        book.setAuthor(authorService.getAuthorById(Integer.parseInt(authorId)));

        if (categoryIds == null) {
            FieldError error = new FieldError("book", "categories", "Book must have at least one category");
            result.addError(error);
        } else {
            List<Category> categories = new ArrayList<>();
            for(String categoryId : categoryIds) {
                categories.add(categoryService.getCategoryById(Integer.parseInt(categoryId)));
            }
            book.setCategories(categories);
        }

        if(result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "editBook";
        }

        bookService.updateBook(book);

        return "redirect:/books";
    }


    @GetMapping("booksByCategory")
    public String getBooksByCategory(@RequestParam(name = "categoryId", defaultValue = "0") int categoryId, Model model) {
        List<Book> books;

        if (categoryId != 0) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                books = bookService.getBooksByCategory(category);
            } else {
                // Category not found, redirect to all books
                return "redirect:/books";
            }
        } else {
            // Show all books when category is 0
            books = bookService.getAllBooks();
        }

        List<Author> authors = authorService.getAllAuthors();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);

        return "books";
    }

//    @GetMapping("booksByAuthor")
//    public String getBooksByAuthor(@RequestParam(name = "authorId", defaultValue = "0") int authorId, Model model) {
//        List<Book> books;
//
//        if (authorId != 0) {
//            Author author = authorService.getAuthorById(authorId);
//            if (author != null) {
//                books = bookService.getBooksByAuthor(author);
//
//                // Set the author for each book to ensure it is not null
//                for (Book book : books) {
//                    book.setAuthor(author);
//
//                    // Load categories for each book
//                    book.setCategories(bookService.getCategoriesForBook(book.getId()));
//                }
//            } else {
//                // Author not found, redirect to all books
//                return "redirect:/books";
//            }
//        } else {
//            // Show all books when author is 0
//            books = bookService.getAllBooks();
//        }
//
//        List<Author> authors = authorService.getAllAuthors();
//        List<Category> categories = categoryService.getAllCategories();
//
//        model.addAttribute("books", books);
//        model.addAttribute("authors", authors);
//        model.addAttribute("categories", categories);
//
//        return "books";
//    }
}
