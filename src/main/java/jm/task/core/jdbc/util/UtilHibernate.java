package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public class UtilHibernate {

    private static UtilHibernate instance = new UtilHibernate();
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/test";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private SessionFactory sessionFactory;

    private UtilHibernate(){
    }

    public static UtilHibernate getInstance(){
        if (instance==null){
            instance = new UtilHibernate();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {

        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                Configuration configuration = new Configuration();

                Properties setting = new Properties();

                setting.put(Environment.DRIVER, DRIVER);
                setting.put(Environment.URL, URL);
                setting.put(Environment.USER, USERNAME);
                setting.put(Environment.PASS, PASSWORD);
                setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                setting.put(Environment.SHOW_SQL, "true");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                setting.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Problem session factory");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }




}

