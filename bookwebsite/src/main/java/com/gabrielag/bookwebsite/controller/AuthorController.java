package com.gabrielag.bookwebsite.controller;

import com.gabrielag.bookwebsite.dto.Author;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Controller
public class AuthorController {
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

    @GetMapping("authors")
    public String displayAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("addAuthor")
    public String addAuthor(Integer id, Model model) {
        return "addAuthor";
    }

    @PostMapping("addAuthor")
    public String addAuthor(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String description = request.getParameter("description");

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setDescription(description);

        // Validate Description text area
        if (description.trim().isEmpty() || description.length() > 255 /*Characters*/) {
            return "redirect:/addAuthor?error=invalidDescription";
        }

        try {
            // Validate author for duplicates
            authorService.validateAuthor(author);

            // If no duplicates, add the author
            authorService.addAuthor(author);
        } catch (DuplicateNameExistsException e) {
            return "redirect:/addAuthor?error=duplicate";
        }

        return "redirect:/authors";
    }

    @PostMapping("deleteAuthor")
    public String deleteAuthor(Integer id) {
        authorService.deleteAuthorByID(id);
        return "redirect:/authors";
    }

    @GetMapping("viewAuthor")
    public String viewAuthor(Integer id, Model model) {
        Author author = authorService.getAuthorById(id);
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("books", books);
        model.addAttribute("author", author);

        return "viewAuthor";
    }

    @GetMapping("editAuthor")
    public String editAuthor(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Author author = authorService.getAuthorById(id);

        model.addAttribute("author", author);

        return "editAuthor";
    }

    @PostMapping("editAuthor")
    public String performEditAuthor(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Author author = authorService.getAuthorById(id);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String description = request.getParameter("description");

        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setDescription(description);

        // Validate description
        if (description.trim().isEmpty() || description.length() > 255) {
            return "redirect:/editAuthor?id=" + id + "&error=invalidDescription";
        }

        try {
            // Validate the author for duplicates
            authorService.validateAuthor(author);

            // If no duplicates, update the author
            authorService.updateAuthor(author);
        } catch (DuplicateNameExistsException e) {
            return "redirect:/editAuthor?id=" + id + "&error=duplicate";
        }

        return "redirect:/authors";
    }
}
