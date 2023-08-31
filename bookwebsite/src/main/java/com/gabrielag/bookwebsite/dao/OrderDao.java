package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Order;
import com.gabrielag.bookwebsite.dto.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

public interface OrderDao {
    Order addOrder(Order order);
    Order getOrderByID(int id);
    List<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrderByID(int id);

    List<Order> getOrdersByUser(User user);
    List<Order> getOrdersByDate(LocalDateTime date);
    List<Order> getOrdersByPaidStatus(boolean isPaid);
}
