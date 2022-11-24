package org.firstproject.dao;

import org.firstproject.model.UserModel;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<? extends UserModel> getAllUsers();

    void cleanUsersTable();
}
