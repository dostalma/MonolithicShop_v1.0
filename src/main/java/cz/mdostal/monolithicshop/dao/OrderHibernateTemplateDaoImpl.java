package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.model.Order;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderHibernateTemplateDaoImpl extends HibernateDaoSupport implements OrderDao {

    @Transactional
    @Override
    public Long createOrder(Order order) {
        getHibernateTemplate().persist(order);
        return order.getId();
    }

    @Transactional
    @Override
    public Order getOrderById(Long id) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List results = session.createQuery("select distinct ord " +
                        "               from Order ord " +
                        "               left join fetch ord.products it " +
                        "               where ord.id = " + id,
                Order.class).getResultList();

        return results.size() > 0 ? (Order) results.get(0) : null;
    }

    @Transactional
    @Override
    public List getAllOrders() {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List results = session.createQuery("select distinct ord " +
                        "               from Order ord " +
                        "               left join fetch ord.products it",
                Order.class).getResultList();
        return results;
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        getHibernateTemplate().update(order);
    }

    @Transactional
    @Override
    public void deleteOrder(Order order) {
        getHibernateTemplate().delete(order);
    }
}
