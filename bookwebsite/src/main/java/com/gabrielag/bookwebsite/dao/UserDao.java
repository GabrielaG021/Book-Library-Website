package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.User;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface UserDao {
    User addUser(User user);
    User getUserByID(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUserByID(int id);

    List<User> getUsersByBook(Book book);
}
