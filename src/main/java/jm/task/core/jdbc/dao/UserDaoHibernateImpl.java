package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = UtilHibernate.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
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
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "drop table if exists user";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            System.out.println("Problem Transaction Drop Table");
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "insert into user(name, lastName, age) values ('"
                    + name + "','" + lastName + "','" + age + "')";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            System.out.println("RollBack Transaction Save");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "delete from user where id=?";
            Query query = session.createSQLQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            System.out.println("RollBack Transaction Remove");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "select * from user";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            List<User> userList = query.list();
            transaction.commit();
            return userList;
        } catch (Exception e){
            System.out.println("Problem Transaction AllUsers");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "truncate table user";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            System.out.println("RollBack Transaction Clean Table");
            e.printStackTrace();
        }
    }
}