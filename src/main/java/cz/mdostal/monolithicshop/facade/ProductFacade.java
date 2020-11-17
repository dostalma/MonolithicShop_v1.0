package cz.mdostal.monolithicshop.facade;

import cz.mdostal.monolithicshop.model.Product;

import java.util.List;

public interface ProductFacade {

    public Long createProduct(Product product);

    public Product getProductById(Long id);

    public List getAllProducts();

    public void updateProduct(Product product);

    public void deleteProduct(Product product);

}
