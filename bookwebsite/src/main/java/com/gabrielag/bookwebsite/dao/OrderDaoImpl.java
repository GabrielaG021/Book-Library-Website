package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Book;
import com.gabrielag.bookwebsite.dto.Order;
import com.gabrielag.bookwebsite.dto.User;
import com.gabrielag.bookwebsite.dao.BookDaoImpl.BookMapper;
import com.gabrielag.bookwebsite.dao.UserDAOImpl.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    JdbcTemplate jdbc;

    // SQL QUERIES
    final String GET_ALL_ORDERS = "SELECT * FROM `order`";
    final String GET_ORDER_BY_ID = "SELECT * FROM `order` WHERE OrderId = ?";
    final String GET_BOOK_FOR_ORDER = "SELECT b.* FROM Book b INNER JOIN `order` o ON b.BookId = o.BookId WHERE o.OrderId = ?";
    final String GET_USER_FOR_ORDER = "SELECT u.* FROM `user` u INNER JOIN `order` o ON u.UserId = o.UserId WHERE o.OrderId = ?";
    final String ADD_ORDER = "INSERT INTO `order` (orderDate, total, isPaid, UserId, BookID) VALUES (?, ?, ?, ?, ?)";
    final String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    final String UPDATE_ORDER = "UPDATE `order` SET orderDate = ?, total = ?, isPaid = ?, UserId = ?, BookID = ? WHERE OrderId = ?";
    final String DELETE_ORDER = "DELETE FROM `order` WHERE OrderId = ?";
    final String GET_ORDERS_BY_USER = "SELECT * FROM `order` WHERE UserId = ?";
    final String GET_ORDERS_BY_DATE = "SELECT * FROM `order` WHERE orderDate = ?";
    final String GET_ORDERS_BY_PAID_STATUS = "SELECT * FROM `order` WHERE isPaid = ?";

    @Override
    public Order addOrder(Order order) {

        jdbc.update(ADD_ORDER,
                order.getOrderDate(),
                order.getTotal(),
                order.isPaid(),
                order.getUser().getId(),
                order.getBook().getId());

        int newId = jdbc.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        order.setId(newId);
        return order;
    }

    @Override
    public Order getOrderByID(int id) {
        try {
            Order order = jdbc.queryForObject(GET_ORDER_BY_ID, new OrderMapper(), id);
            order.setBook(getBookForOrder(id));
            order.setUser(getUserForOrder(id));
            return order;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = jdbc.query(GET_ALL_ORDERS, new OrderMapper());
        associateBooksAndUsers(orders);
        return orders;
    }


    @Override
    public void updateOrder(Order order) {
        jdbc.update(UPDATE_ORDER,
                order.getOrderDate(),
                order.getTotal(),
                order.isPaid(),
                order.getUser().getId(),
                order.getBook().getId(),
                order.getId());
    }

    @Override
    public void deleteOrderByID(int id) {
        jdbc.update(DELETE_ORDER, id);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        List<Order> orders = jdbc.query(GET_ORDERS_BY_USER, new OrderMapper(), user.getId());
        associateBooksAndUsers(orders);
        return orders;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime date) {
        List<Order> orders = jdbc.query(GET_ORDERS_BY_DATE, new OrderMapper(), date);
        associateBooksAndUsers(orders);
        return orders;
    }

    @Override
    public List<Order> getOrdersByPaidStatus(boolean isPaid) {
        List<Order> orders = jdbc.query(GET_ORDERS_BY_PAID_STATUS, new OrderMapper(), isPaid);
        associateBooksAndUsers(orders);
        return orders;
    }


    // HELPER METHODS
    private Book getBookForOrder(int id) {
        try {
            return jdbc.queryForObject(GET_BOOK_FOR_ORDER, new BookMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private User getUserForOrder(int id) {
        try{
            return jdbc.queryForObject(GET_USER_FOR_ORDER, new UserMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private void associateBooksAndUsers(List<Order> orders) {
        for (Order order : orders) {
            order.setBook(getBookForOrder(order.getId()));
            order.setUser(getUserForOrder(order.getId()));
        }
    }


    // ORDER MAPPER
    public static final class OrderMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("OrderId"));
            order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
            order.setTotal(rs.getString("total"));
            order.setPaid(rs.getBoolean("isPaid"));

            return order;
        }
    }
}
