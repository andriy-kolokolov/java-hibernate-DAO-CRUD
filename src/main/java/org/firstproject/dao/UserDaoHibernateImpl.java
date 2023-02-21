package org.firstproject.dao;

import org.firstproject.model.hibernate.User;
import org.firstproject.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        // create session factory
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // create user
            User user = new User();
            user.setUserName(name);
            user.setUserLastName(lastName);
            user.setUserAge(age);
            // prepare object to commit
            session.persist(user);
            // commit/save changes and then transaction closes
            transaction.commit();
            // session auto closes because try catch with resources
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start the transaction
            Transaction transaction = session.beginTransaction();
            // delete an object by id
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                System.out.printf("User with id " + id + " was deleted.");
            }
            // commit transaction
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> returnList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            returnList = session.createQuery("select a from User a", User.class).getResultList();
            transaction.commit();
        }
        return returnList;
    }

    @Override
    public void createUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS usersdb.users" +
                    " (id bigint not null auto_increment primary key, " +
                    "userName VARCHAR(50), " +
                    "userLastName VARCHAR(50), " +
                    "userAge tinyint)").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS usersdb.users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE usersdb.users").executeUpdate();
            transaction.commit();
            System.out.println("Users table deleted...");
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
