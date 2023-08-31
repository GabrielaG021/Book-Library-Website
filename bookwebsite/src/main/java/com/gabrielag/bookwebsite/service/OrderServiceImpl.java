package com.gabrielag.bookwebsite.service;

import com.gabrielag.bookwebsite.dao.OrderDao;
import com.gabrielag.bookwebsite.dto.Order;
import com.gabrielag.bookwebsite.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Service
public class OrderServiceImpl implements OrderServiceLayer {
    @Autowired
    OrderDao orderDao;

    @Override
    public Order addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public Order getOrderByID(int id) {
        return orderDao.getOrderByID(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrderByID(int id) {
        orderDao.deleteOrderByID(id);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderDao.getOrdersByUser(user);
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime date) {
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public List<Order> getOrdersByPaidStatus(boolean isPaid) {
        return orderDao.getOrdersByPaidStatus(isPaid);
    }

    @Override
    public void validateOrder(Order order) throws InvalidDataException {
        if (order == null) {
            throw new InvalidDataException("Order cannot be null.");
        }

        if (order.getOrderDate() == null) {
            throw new InvalidDataException("Order date cannot be null.");
        }

        if (order.getTotal() == null || order.getTotal().isEmpty()) {
            throw new InvalidDataException("Order total cannot be null or empty.");
        }

        if (order.getUser() == null || order.getUser().getId() <= 0) {
            throw new InvalidDataException("Invalid user associated with the order.");
        }

        if (order.getBook() == null || order.getBook().getId() <= 0) {
            throw new InvalidDataException("Invalid book associated with the order.");
        }
    }
}
