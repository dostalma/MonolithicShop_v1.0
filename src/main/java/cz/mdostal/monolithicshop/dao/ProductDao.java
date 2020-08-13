package cz.mdostal.monolithicshop.dao;

import cz.mdostal.monolithicshop.model.Product;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public interface ProductDao {

    public Long createProduct(Product product);

    public Product getProductById(Long id);

    public List getAllProducts();

    public void updateProduct(Product product);

    public void deleteProduct(Product product);
}