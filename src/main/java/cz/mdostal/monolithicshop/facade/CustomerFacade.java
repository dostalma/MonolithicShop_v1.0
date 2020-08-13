package cz.mdostal.monolithicshop.facade;

import cz.mdostal.monolithicshop.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerFacade {

    public Customer createCustomer(Customer customer);

    public Optional<Customer> getCustomerById(Long id);

    public Optional<Customer> getCustomerByUsername(String username);

    public List getAllCustomers();

    public void updateCustomer(Customer customer);

    public void deleteCustomerById(Long id);

}

