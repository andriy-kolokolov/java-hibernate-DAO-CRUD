package org.firstproject.dao;

import org.firstproject.model.hibernate.User;
import org.firstproject.util.HibernateUtil;
import org.firstproject.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        // create session factory
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
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
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
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
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            returnList = session.createQuery("select a from User a", User.class).getResultList();
        }
        return returnList;
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users(
                    id BIGINT NOT NULL AUTO_INCREMENT KEY,
                    userName VARCHAR(50),
                    userLastName VARCHAR(50),
                    userAge TINYINT);""");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // console log
        System.out.println("Users table created...");
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS users;");
            // console log
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Connection connection = Util.dbConnection()){
            connection.createStatement().executeUpdate("DELETE FROM users;");
            // console log
            System.out.println("Users table deleted...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
