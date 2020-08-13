package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.model.Customer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED)
public interface CustomerDao {

    public Long createCustomer(Customer customer);

    public Optional<Customer> getCustomerById(Long id);

    public Optional<Customer> getCustomerByUsername(String username);

    public List getAllCustomers();

    public void updateCustomer(Customer customer);

    public void deleteCustomer(Long id);
}
