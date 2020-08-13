package cz.mdostal.monolithicshop.controller;

import com.google.common.base.Preconditions;
import cz.mdostal.monolithicshop.facade.ProductFacade;
import cz.mdostal.monolithicshop.model.Product;
import cz.mdostal.monolithicshop.util.RestPreconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    ProductFacade productFacade;

    /**
     * Request for base url returns a list of products
     *
     * @return List of products
     */
    @GetMapping
    public List<Product> findAll() {
        logger.info("Request to retrieve all products");
        List<Product> list = productFacade.getAllProducts();
        return list;
    }

    /**
     * GET Request with an ID parameter returns a single product if existing
     *
     * @param id of the product to retrieve
     * @return Found product if existing
     */
    @GetMapping(value = "/{id}")
    public Product findById(@PathVariable("id") Long id) {
        logger.info("Request to retrieve a product with id: " + id);
        return productFacade.getProductById(id);
    }

    /**
     * POST Request to create a product
     *
     * @param product payload of a Product object
     * @return List of products
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Product product) {
        Preconditions.checkNotNull(product);
        logger.info("Request to create an product: " + product.toString());
        return productFacade.createProduct(product);
    }


    /**
     * PUT Request to update a product
     *
     * @param id of Product to be updated
     * @param product payload of a Product object
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Product product) {
        Preconditions.checkNotNull(product);
        logger.info("Request to update an product: " + product.toString() + "with id: " + id);

        Product existingProduct = productFacade.getProductById(id);
        RestPreconditions.checkNotNull(existingProduct);

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        productFacade.updateProduct(existingProduct);
    }

    /**
     * DELETE Request to delete an product
     *
     * @param id of product to delete
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        logger.info("Request to delete a product with id: " + id);
        productFacade.deleteProduct(productFacade.getProductById(id));
    }
}
