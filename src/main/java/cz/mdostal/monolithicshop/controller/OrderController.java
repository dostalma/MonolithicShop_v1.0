package cz.mdostal.monolithicshop.controller;

import com.google.common.base.Preconditions;
import cz.mdostal.monolithicshop.facade.OrderFacade;
import cz.mdostal.monolithicshop.model.Order;
import cz.mdostal.monolithicshop.util.RestPreconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    OrderFacade orderFacade;

    /**
     * Request for base url returns a list of orders
     *
     * @return List of orders
     */
    @GetMapping
    public List<Order> findAll() {
        logger.info("Request to retrieve all orders");
        List<Order> list = orderFacade.getAllOrders();
        return list;
    }

    /**
     * GET Request with an ID parameter returns a single order if existing
     *
     * @param id of the order to retrieve
     * @return Found order if existing
     */
    @GetMapping(value = "/{id}")
    public Order findById(@PathVariable("id") Long id) {
        logger.info("Request to retrieve an order with id: " + id);
        return orderFacade.getOrderById(id);
    }

    /**
     * POST Request to create an order
     *
     * @param order payload of an Order object
     * @return List of customers
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Order order) {
        Preconditions.checkNotNull(order);
        logger.info("Request to create an order: " + order.toString());
        return orderFacade.createOrder(order);
    }

    /**
     * PUT Request to update an order
     *
     * @param id of Order to be updated
     * @param order payload of an Order object
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Order order) {
        Preconditions.checkNotNull(order);
        logger.info("Request to update an order: " + order.toString() + " with id: " + id);

        Order existingOrder = orderFacade.getOrderById(id);
        RestPreconditions.checkNotNull(existingOrder);

        existingOrder.setDate(order.getDate());
        existingOrder.setProducts(order.getProducts());

        orderFacade.updateOrder(existingOrder);
    }

    /**
     * DELETE Request to delete an order
     *
     * @param id of order to delete
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        logger.info("Request to delete an order with id: " + id);
        orderFacade.deleteOrder(orderFacade.getOrderById(id));
    }
}
