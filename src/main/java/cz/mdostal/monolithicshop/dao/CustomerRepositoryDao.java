package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.Repository.CustomerRepository;
import cz.mdostal.monolithicshop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CustomerRepositoryDao implements CustomerDao {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    @Override
    public Long createCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public List getAllCustomers() {
        return (List) customerRepository.findAll();
    }

    @Transactional
    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
