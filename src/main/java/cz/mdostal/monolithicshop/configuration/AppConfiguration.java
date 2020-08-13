package cz.mdostal.monolithicshop.configuration;

import cz.mdostal.monolithicshop.dao.*;
import cz.mdostal.monolithicshop.facade.*;
import cz.mdostal.monolithicshop.factory.CustomerDaoFactory;
import cz.mdostal.monolithicshop.service.CustomerService;
import cz.mdostal.monolithicshop.service.CustomerServiceRepositoryImpl;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Base configuration for application beans
 */
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext appContext;

    @Bean(name="productFacade")
    public ProductFacade productFacade() {
        return new ProductFacadeImpl();
    }

    @Bean(name="orderFacade")
    public OrderFacade orderFacade() {
        return new OrderFacadeImpl();
    }

    @Bean(name="customerFacade")
    public CustomerFacade customerFacade() {
        return new CustomerFacadeImpl();
    }

    @Bean(name="customerService")
    public CustomerService customerService() {
        return new CustomerServiceRepositoryImpl();
    }

    @Bean(name="productDao")
    public ProductDao productDao() {
        ProductHibernateTemplateDaoImpl dao = new ProductHibernateTemplateDaoImpl();
        dao.setSessionFactory((SessionFactory) appContext.getBean("sessionFactory") );
        return dao;
    }

    @Bean(name="orderDao")
    public OrderDao orderDao() {
        OrderHibernateTemplateDaoImpl dao = new OrderHibernateTemplateDaoImpl();
        dao.setSessionFactory((SessionFactory) appContext.getBean("sessionFactory") );
        return dao;
    }

    @Bean(name="customerDao")
    public CustomerDao customerDao() throws Exception {
        return CustomerDaoFactory.getInstance().getObject(appContext, env);
    }

    // @TODO fix liquibase integration
    //@Bean
    /*
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("project_model.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }
    */
}
