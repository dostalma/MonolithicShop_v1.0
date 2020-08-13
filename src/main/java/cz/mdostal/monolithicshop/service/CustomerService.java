package cz.mdostal.monolithicshop.service;

import cz.mdostal.monolithicshop.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public Customer create(Customer customer);

    public Optional<Customer> findById(Long id);

    public Optional<Customer> findByUsername(String username);

    public List findAll();

    public void update(Customer customer);

    public void delete(Customer customer);

    public void deleteById(Long id);
}
