package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.model.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public interface OrderDao {

    public Long createOrder(Order order);

    public Order getOrderById(Long id);

    public List getAllOrders();

    public void updateOrder(Order order);

    public void deleteOrder(Order order);
}

