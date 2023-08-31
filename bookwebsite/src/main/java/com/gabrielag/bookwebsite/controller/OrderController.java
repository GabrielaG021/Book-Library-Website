package com.gabrielag.bookwebsite.controller;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Order;
import com.gabrielag.bookwebsite.dto.User;
import com.gabrielag.bookwebsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Controller
public class OrderController {
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

    @GetMapping("orders")
    public String displayOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        List<Book> books = bookService.getAllBooks();
        List<User> users = userService.getAllUsers();

        model.addAttribute("orders", orders);
        model.addAttribute("books", books);
        model.addAttribute("users", users);

        return "orders";
    }

    @GetMapping("addOrder")
    public String addOrder(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("books", books);
        model.addAttribute("order", new Order());

        return "addOrder";
    }

    @PostMapping("addOrder")
    public String addOrder(HttpServletRequest request) {
        Order order = new Order();
        String bookId = request.getParameter("bookId");
        String userId = request.getParameter("userId");
        Boolean selectedValue = Boolean.parseBoolean(request.getParameter("isPaid"));

        String dateTime = request.getParameter("orderDate");
        LocalDateTime orderDate = LocalDateTime.parse(dateTime);
        order.setOrderDate(orderDate);

        order.setBook(bookService.getBooksByID(Integer.parseInt(bookId)));
        order.setUser(userService.getUserByID(Integer.parseInt(userId)));
        order.setPaid(selectedValue);

        order.setTotal(order.getBook().getPrice());
        orderService.addOrder(order);

        return "redirect:/orders";
    }

    @PostMapping("deleteOrder")
    public String deleteOrder(Integer id) {
        orderService.deleteOrderByID(id);

        return "redirect:/orders";
    }

    @GetMapping("viewOrderInvoice")
    public String viewOrderInvoice(Integer id, Model model) {
        Order order = orderService.getOrderByID(id);
        model.addAttribute("order", order);

        return "viewOrderInvoice";
    }

    @GetMapping("editOrder")
    public String editOrder(Integer id, Model model) {
        Order order = orderService.getOrderByID(id);
        List<Book> books = bookService.getAllBooks();
        List<User> users = userService.getAllUsers();

        model.addAttribute("order", order);
        model.addAttribute("books", books);
        model.addAttribute("users", users);

        return "editOrder";
    }

    @PostMapping("editOrder")
    public String performEditOrder(HttpServletRequest request) {
        Order order = orderService.getOrderByID(Integer.parseInt(request.getParameter("id")));
        Boolean selectedValue = Boolean.parseBoolean(request.getParameter("isPaid"));
        order.setTotal(request.getParameter("total"));
        order.setPaid(selectedValue);

        orderService.updateOrder(order);

        return "redirect:/orders";
    }
}
