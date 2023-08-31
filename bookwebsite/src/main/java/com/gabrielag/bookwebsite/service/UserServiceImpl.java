package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dao.UserDao;
import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Service
public class UserServiceImpl implements UserServiceLayer {
    @Autowired
    UserDao userDao;

    @Override
    public User addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User getUserByID(int id) {
        return userDao.getUserByID(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserByID(int id) {
        userDao.deleteUserByID(id);
    }

    @Override
    public List<User> getUsersByBook(Book book) {
        return userDao.getUsersByBook(book);
    }

    @Override
    public void validateUser(User user) throws DuplicateNameExistsException, InvalidDataException {
        List<User> users = userDao.getAllUsers();
        boolean isDuplicate = false;
        String phonePattern = "\\d{3}-\\d{3}-d{4}";
        String phone = user.getPhone();

        for(User singleUser : users) {
            if (singleUser.getFirstName().toLowerCase().equals(user.getFirstName().toLowerCase())
            && singleUser.getLastName().toLowerCase().equals(user.getLastName().toLowerCase())) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            throw new DuplicateNameExistsException("User full name already exists in our database system.");
        }

        if (phone == null || !phone.matches(phonePattern)) {
            throw new InvalidDataException("Invalid phone format. Please enter valid phone number(Format: ###-###-####)");
        }

    }
}
