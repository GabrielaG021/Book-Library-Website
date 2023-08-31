package com.gabrielag.bookwebsite.controller;

import com.gabrielag.bookwebsite.dto.Order;
import com.gabrielag.bookwebsite.dto.User;
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
public class UserController {
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

    @GetMapping("users")
    public String displayUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("addUser")
    public String addUser(Model model){

        return "addUser";
    }

    @PostMapping("addUser")
    public String addUser(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhone(phone);


        userService.addUser(user);


        return "redirect:/users";
    }


    @PostMapping("deleteUser")
    public String deleteUser(Integer id) {
        userService.deleteUserByID(id);

        return "redirect:/users";
    }

    @GetMapping("viewUser")
    public String viewUser(Integer id, Model model) {
        User user = userService.getUserByID(id);
        List<Order> orders = orderService.getOrdersByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "viewUser";
    }

    @GetMapping("editUser")
    public String editUser(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserByID(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("editUser")
    public String performEditUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        User user = userService.getUserByID(id);
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setAddress(request.getParameter("address"));
        user.setPhone(request.getParameter("phone"));

        // Save new user
        userService.updateUser(user);

        return "redirect:/users";
    }
}
