package cz.mdostal.monolithicshop.facade;

import cz.mdostal.monolithicshop.dao.OrderDao;
import cz.mdostal.monolithicshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderFacadeImpl implements OrderFacade {

    @Autowired
    OrderDao orderDao;

    @Override
    public Long createOrder(Order order) {
        return orderDao.createOrder(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderDao.deleteOrder(order);
    }
}
