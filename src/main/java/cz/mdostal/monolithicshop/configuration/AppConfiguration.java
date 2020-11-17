package cz.mdostal.monolithicshop.configuration;

import cz.mdostal.monolithicshop.dao.*;
import cz.mdostal.monolithicshop.facade.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

/**
 * Base configuration for application beans
 */
@Configuration
@PropertySource("classpath:application.properties")
@TestPropertySource("classpath:application-test.properties")
public class AppConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext appContext;

    @Bean(name="productFacade")
    public ProductFacade productFacade() {
        return new ProductFacadeImpl();
    }

    @Bean(name="productDao")
    public ProductDao productDao() {
        ProductHibernateTemplateDaoImpl dao = new ProductHibernateTemplateDaoImpl();
        dao.setSessionFactory((SessionFactory) appContext.getBean("sessionFactory") );
        return dao;
    }
}
