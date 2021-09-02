package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {

        try (SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS user " +
                    "(id INT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(50)," +
                    " lastName VARCHAR(50)," +
                    " age INT(3)," +
                    " PRIMARY KEY (`id`))";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            System.out.println("Problem Transaction Create Table");
        }
    }

    @Override
    public void dropUsersTable() {

        try (SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "drop table if exists user";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            System.out.println("Problem Transaction Drop Table");
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "insert into user(name, lastName, age) values ('"
                    + name + "','" + lastName + "','" + age + "')";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            System.out.println("RollBack Transaction Save");
        }
    }

    @Override
    public void removeUserById(long id) {

        try (SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "delete from user where id=?";
            Query query = session.createSQLQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            System.out.println("RollBack Transaction Remove");
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "select * from user";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            List<User> userList = query.list();
            transaction.commit();
            return userList;
        } catch (Exception e){
            System.out.println("Problem Transaction AllUsers");
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {

        try (SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "truncate table user";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            System.out.println("RollBack Transaction Clean Table");
        }
    }
}