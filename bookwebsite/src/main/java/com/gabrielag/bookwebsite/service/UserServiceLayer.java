package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.User;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface UserServiceLayer {
    User addUser(User user);
    User getUserByID(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUserByID(int id);

    List<User> getUsersByBook(Book book);

    void validateUser(User user) throws DuplicateNameExistsException, InvalidDataException;
}
