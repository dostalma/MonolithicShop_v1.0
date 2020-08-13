package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.model.Product;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductHibernateTemplateDaoImpl extends HibernateDaoSupport implements ProductDao {

    @Transactional
    @Override
    public Long createProduct(Product product) {
        getHibernateTemplate().persist(product);
        return product.getId();
    }

    @Transactional
    @Override
    public Product getProductById(Long id) {
        return getHibernateTemplate().get(Product.class, id);
    }

    @Transactional
    @Override
    public List getAllProducts() {
        return getHibernateTemplate().loadAll(Product.class);
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        getHibernateTemplate().update(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Product product) {
        getHibernateTemplate().delete(product);
    }
}
