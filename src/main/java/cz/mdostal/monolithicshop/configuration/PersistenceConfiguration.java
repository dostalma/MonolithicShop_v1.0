package cz.mdostal.monolithicshop.configuration;

import cz.mdostal.monolithicshop.Constants;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for persistence
 */
@Configuration
@EnableJpaRepositories(basePackages = "cz.mdostal.monolithicshop")
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
public class PersistenceConfiguration {

    private final static String BASE_PACKAGE_NAME = "cz.mdostal.monolithicshop";

    @Autowired
    private Environment env;

    /*                                     */
    /* Previously working on Hibernate Dao */
    /*                                     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(driverDataSource());
        sessionFactory.setPackagesToScan(
                new String[]{BASE_PACKAGE_NAME});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource driverDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(driverDataSource());
        em.setPackagesToScan(new String[]{BASE_PACKAGE_NAME + ".model"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    @Autowired
    public TransactionManager transactionManager(
            SessionFactory sessionFactory) {
        TransactionManager txManager;
        String persistenceType =  env.getProperty("persistence.type");
        if (Constants.REPOSITORY.equalsIgnoreCase(persistenceType)) {
            txManager = new JpaTransactionManager();
            ((JpaTransactionManager)txManager).setEntityManagerFactory(entityManagerFactory().getObject());
        } else {
            txManager = new HibernateTransactionManager();
            ((HibernateTransactionManager)txManager).setSessionFactory(sessionFactory);
        }

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        env.getProperty("hibernate.hbm2ddl.auto"));
                /*setProperty("spring.jpa.hibernate.ddl-auto",
                        env.getProperty("spring.jpa.hibernate.ddl-auto"));*/
                setProperty("hibernate.dialect",
                        env.getProperty("hibernate.dialect"));
                setProperty("hibernate.globally_quoted_identifiers",
                        "true");
                // @TODO fix import
                /*
                setProperty("hibernate.hbm2ddl.import_files",
                        env.getProperty("hibernate.hbm2ddl.import_files"));
                */
            }
        };
    }

    /*                                     */
    /*             JPA Config              */
    /*                                     */
    @Bean
    public DataSource dataSource() {
        return driverDataSource();
    }
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setDatabase(Database.MYSQL);
        bean.setGenerateDdl(true);
        bean.setShowSql(true);
        return bean;
    }


    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:project_model.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    /*
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    */
}
