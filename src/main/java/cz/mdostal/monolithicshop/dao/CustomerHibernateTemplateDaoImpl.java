package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class CustomerHibernateTemplateDaoImpl extends HibernateDaoSupport implements CustomerDao {

    @Autowired
    private ApplicationContext appContext;

    public CustomerHibernateTemplateDaoImpl() {
        this.setSessionFactory((SessionFactory) appContext.getBean("sessionFactory") );
    }

    @Transactional
    @Override
    public Long createCustomer(Customer customer) {
        getHibernateTemplate().persist(customer);
        return customer.getId();
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerById(Long id) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List results = session.createQuery("select distinct cust " +
                        "               from Customer cust " +
                        "               left join fetch cust.orders ord " +
                        "               left join fetch ord.products it " +
                        "               where cust.id = " + id,
                Customer.class).getResultList();

        return Optional.ofNullable(results.size() > 0 ? (Customer) results.get(0) : null);
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List results = session.createQuery("select distinct cust " +
                        "               from Customer cust " +
                        "               left join fetch cust.orders ord " +
                        "               left join fetch ord.products it " +
                        "               where cust.username = '" + username + "'",
                Customer.class).getResultList();

        return Optional.ofNullable(results.size() > 0 ? (Customer) results.get(0) : null);
    }

    @Transactional
    @Override
    public List getAllCustomers() {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List results = session.createQuery("select distinct cust " +
                "               from Customer cust " +
                "               left join fetch cust.orders ord " +
                "               left join fetch ord.products it",
                Customer.class).getResultList();
        return results;
    }

    @Transactional
    @Override
    public void updateCustomer(Customer customer) {
        getHibernateTemplate().update(customer);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        getHibernateTemplate().delete(getCustomerById(id));
    }
}