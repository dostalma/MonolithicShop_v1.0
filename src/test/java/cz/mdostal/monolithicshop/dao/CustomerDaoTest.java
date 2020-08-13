package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.configuration.AppConfiguration;
import cz.mdostal.monolithicshop.configuration.PersistenceConfiguration;
import cz.mdostal.monolithicshop.model.Customer;
import cz.mdostal.monolithicshop.model.Product;
import cz.mdostal.monolithicshop.model.Order;
import cz.mdostal.monolithicshop.util.TestDataCreator;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { PersistenceConfiguration.class, AppConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
    @TestPropertySource(properties = {"classpath:persistence-test.properties", "classpath:application-test.properties"})
public class CustomerDaoTest {

    private Logger logger = LogManager.getLogger(CustomerDaoTest.class);

    @Autowired
    private CustomerDao customerDao;

    private static boolean setUpIsDone = false;

    @Before
    public  void setUp () {
        if (setUpIsDone) {
            return;
        }
        TestDataCreator creator = new TestDataCreator();
        creator.setCustomerDao(customerDao);
        creator.generateData();

        setUpIsDone = true;
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerDao.getAllCustomers();

        assertNotNull(customers);
        int size1 = customers.size();

        assertTrue(size1 > 0);

        Customer cust = new Customer("Jane", "Doe", "jane.doe@mail.com");
        customerDao.createCustomer(cust);

        customers = customerDao.getAllCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() > size1);
    }

    @Test
    public void testGetCustomerByUsername() {
        Optional<Customer> cust = customerDao.getCustomerByUsername("john.doe@mail.com");
        assertNotNull(cust);
        assertTrue(cust.isPresent());
        assertEquals("John", cust.get().getFirstName());
        assertEquals("Doe", cust.get().getLastName());
    }

    @Test
    public void testGetCustomerById() {
        Optional<Customer> cust = customerDao.getCustomerById(1l);
        assertNotNull(cust);
        assertTrue(cust.isPresent());
        assertEquals("John", cust.get().getFirstName());
        assertEquals("Doe", cust.get().getLastName());
    }

    @Test
    public void testCreateCustomer()
    {
        Product product = new Product();
        product.setName("Cucumber");
        product.setPrice(0.79);

        Order order = new Order(Date.valueOf("2020-05-05"));
        order.getProducts().add(product);

        Customer cust = new Customer("Jim", "Doe", "jim.doe@mail.com");
        cust.getOrders().add(order);

        Long key = customerDao.createCustomer(cust);

        Optional<Customer> cust2 = customerDao.getCustomerById(key);
        assertNotNull(cust2);
        assertTrue(cust2.isPresent());

        assertEquals("Jim", cust.getFirstName());
        assertEquals("Doe", cust.getLastName());
        assertEquals("jim.doe@mail.com", cust.getUsername());

        assertNotNull(cust2.get().getOrders());
        assertEquals(1, cust2.get().getOrders().size());

        Order order2 = cust2.get().getOrders().get(0);
        assertNotNull(order2);
        assertEquals("2020-05-05", order2.getDate().toString());
        assertNotNull(order2.getProducts());
        assertEquals(1, order2.getProducts().size());

        Product product2 = order2.getProducts().get(0);
        assertNotNull(product2);
        assertEquals("Cucumber", product2.getName());
        assertEquals(Double.valueOf(0.79), product2.getPrice());
    }

    @Test
    public void testUpdateCustomer() {
        Optional<Customer> cust = customerDao.getCustomerByUsername("john.doe@mail.com");
        assertNotNull(cust);
        assertTrue(cust.isPresent());
        cust.get().setFirstName("Jackson");
        customerDao.updateCustomer(cust.get());

        Optional<Customer> cust2 = customerDao.getCustomerByUsername("john.doe@mail.com");
        assertNotNull(cust2);
        assertEquals("Jackson", cust2.get().getFirstName());
    }

    @Test
    public void testDeleteCustomer() {
        Optional<Customer> cust = customerDao.getCustomerByUsername("john.doe@mail.com");
        assertNotNull(cust);
        assertTrue(cust.isPresent());
        customerDao.deleteCustomer(cust.get().getId());

        Optional<Customer> cust2 = customerDao.getCustomerByUsername("john.doe@mail.com");
        assertFalse(cust2.isPresent());
    }
}
