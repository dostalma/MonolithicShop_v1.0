package cz.mdostal.monolithicshop.factory;

import cz.mdostal.monolithicshop.Constants;
import cz.mdostal.monolithicshop.dao.CustomerDao;
import cz.mdostal.monolithicshop.dao.CustomerHibernateTemplateDaoImpl;
import cz.mdostal.monolithicshop.dao.CustomerRepositoryDao;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class CustomerDaoFactory implements FactoryBean<CustomerDao> {

    private Environment env;

    private ApplicationContext appContext;

    private static CustomerDaoFactory instance;

    protected CustomerDaoFactory() {
    }

    public static CustomerDaoFactory getInstance() {
        if (instance == null) {
            instance = new CustomerDaoFactory();
        }
        return instance;
    }

    @Override
    public CustomerDao getObject() throws Exception {
        if (env != null && Constants.REPOSITORY.equalsIgnoreCase(getEnv().getProperty("persistence.type"))) {
            return new CustomerRepositoryDao();
        }
        return new CustomerHibernateTemplateDaoImpl();
    }

    public CustomerDao getObject(ApplicationContext ctx, Environment env) throws Exception {
        this.env = env;
        this.appContext = ctx;
        return getObject();
    }

    @Override
    public Class<?> getObjectType() {
        return CustomerDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }
}
