package cz.mdostal.monolithicshop.facade;

import cz.mdostal.monolithicshop.dao.CustomerDao;
import cz.mdostal.monolithicshop.dao.ProductDao;
import cz.mdostal.monolithicshop.model.Product;
import cz.mdostal.monolithicshop.util.TestDataCreator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductFacadeImpl implements ProductFacade {

    @Autowired
    ProductDao productDao;

    @Autowired
    CustomerDao customerDao;

    @Override
    public Long createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    @Override
    public List getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productDao.deleteProduct(product);
    }

    @Override
    public void initializeTestData() {
        TestDataCreator creator = new TestDataCreator();
        creator.setCustomerDao(customerDao);
        creator.generateData();
    }
}
