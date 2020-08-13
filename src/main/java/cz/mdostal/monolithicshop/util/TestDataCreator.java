package cz.mdostal.monolithicshop.util;

import cz.mdostal.monolithicshop.dao.CustomerDao;
import cz.mdostal.monolithicshop.model.Customer;
import cz.mdostal.monolithicshop.model.Product;
import cz.mdostal.monolithicshop.model.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;

public class TestDataCreator {

    private Logger logger = LogManager.getLogger(TestDataCreator.class);

    private CustomerDao customerDao;

    /**
     * Generates some sample test data
     * First requires a CustomerDao to be set in the instance
     */
    public void generateData() {
        Order order = new Order(Date.valueOf("2020-01-01"));
        Customer customer = new Customer("John", "Doe", "john.doe@mail.com");
        customer.getOrders().add(order);

        Product product1 = new Product("Red apple", 1.99);
        Product product2 = new Product("Green apple", 1.49);
        Product product3 = new Product("Carrot", 1.19);
        Product product4 = new Product("Banana", 2.39);
        Product product5 = new Product("Orange", 3.49);

        order.getProducts().add(product1);
        order.getProducts().add(product2);
        order.getProducts().add(product3);
        order.getProducts().add(product4);
        order.getProducts().add(product5);

        customerDao.createCustomer(customer);
        logger.info("Test data imported");
        System.out.println("Finished");
    }


    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
}