package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Repository
public class UserDAOImpl implements UserDao {
    @Autowired
    JdbcTemplate jdbc;

    // SQL QUERIES
    final String GET_ALL_USERS = "SELECT * FROM `user`";
    final String ADD_USER = "INSERT INTO `user` (userFirstName, userLastName, userAddress, userPhone) VALUES (?, ?, ?, ?)";
    final String GET_USER_BY_ID = "SELECT * FROM `user` WHERE UserId = ?";
    final String UPDATE_USER = "UPDATE `user` SET userFirstName = ?, userLastName = ?, userAddress = ?, userPhone = ? WHERE UserId = ?";
    final String DELETE_USER = "DELETE FROM `user` WHERE UserId = ?";
    final String DELETE_USER_BY_ORDER = "DELETE FROM `order` WHERE UserId = ?";
    final String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    final String GET_USER_BY_BOOK = "SELECT u.* FROM `user` u INNER JOIN `order` o ON o.UserId = u.UserId WHERE o.BookId = ?";

    @Override
    public User addUser(User user) {
        jdbc.update(ADD_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhone());

        // Retrieve last inserted Id
        int newId = jdbc.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        // Set the user id
        user.setId(newId);
        return user;
    }

    @Override
    public User getUserByID(int id) {
        try {
            return jdbc.queryForObject(GET_USER_BY_ID, new UserMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbc.query(GET_ALL_USERS, new UserMapper());
    }

    @Override
    public void updateUser(User user) {
        jdbc.update(UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhone(),
                user.getId());
    }

    @Override
    @Transactional
    public void deleteUserByID(int id) {
        // Step 1: Delete associated Order entries related to the User
        jdbc.update(DELETE_USER_BY_ORDER, id);

        // Step 2: Delete the User
        jdbc.update(DELETE_USER, id);
    }

    @Override
    public List<User> getUsersByBook(Book book) {
        return jdbc.query(GET_USER_BY_BOOK, new UserMapper(), book.getId());
    }


    // USER MAPPER
    /**
     * Mappers are responsible for mapping the result set from a database
     * query to a specific object or entity.
     * Encapsulates the mapping logic within the class.
     */
    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("UserId"));
            user.setFirstName(rs.getString("userFirstName"));
            user.setLastName(rs.getString("userLastName"));
            user.setAddress(rs.getString("userAddress"));
            user.setPhone(rs.getString("userPhone"));

            return user;
        }
    }
}
