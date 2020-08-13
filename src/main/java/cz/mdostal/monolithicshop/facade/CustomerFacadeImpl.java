package cz.mdostal.monolithicshop.facade;

import cz.mdostal.monolithicshop.model.Customer;
import cz.mdostal.monolithicshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    CustomerService customerService;

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerService.create(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerService.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return customerService.findByUsername(username);
    }

    @Override
    public List getAllCustomers() {
        return customerService.findAll();
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerService.update(customer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerService.deleteById(id);
    }
}