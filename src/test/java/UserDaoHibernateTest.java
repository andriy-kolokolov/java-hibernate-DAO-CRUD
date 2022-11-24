import org.firstproject.dao.UserDaoHibernateImpl;

import org.firstproject.model.hibernate.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserDaoHibernateTest {
    // HIBERNATE TESTS
    private final UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
    private final String testName = "Marco";
    private final String testLastName = "Rossi";
    private final byte testAge = 36;

    @Test
    public void dropUsersTable() {
        try {
            userDaoHibernate.dropUsersTable();
            userDaoHibernate.dropUsersTable();
        } catch (Exception e) {
            Assert.fail("Error dropping users table. Exception thrown\n" + e);
        }
    }

    @Test
    public void createUsersTable() {
        try {
            userDaoHibernate.dropUsersTable();
            userDaoHibernate.createUsersTable();
        } catch (Exception e) {
            Assert.fail("Error creating users table. Exception thrown.\n" + e);
        }
    }

    @Test
    public void saveUser() {
        try {
            userDaoHibernate.dropUsersTable();
            userDaoHibernate.createUsersTable();
            userDaoHibernate.saveUser(testName, testLastName, testAge);

            User user = userDaoHibernate.getAllUsers().get(0);

            if (!testName.equals(user.getUserName())
                    || !testLastName.equals(user.getUserLastName())
                    || testAge != user.getUserAge()
            ) {
                Assert.fail("User was incorrectly added to database");
            }
        } catch (Exception e) {
            Assert.fail("Error saving user into a users table. Exception thrown.\n" + e);
        }
    }

    @Test
    public void removeUserById() {
        try {
            userDaoHibernate.dropUsersTable();
            userDaoHibernate.createUsersTable();
            userDaoHibernate.saveUser(testName, testLastName, testAge);
            userDaoHibernate.removeUserById(1L);
        } catch (Exception e) {
            Assert.fail("Error removing user by id. Exception thrown.\n" + e);
        }
    }

    @Test
    public void getAllUsers() {
        try {
            userDaoHibernate.dropUsersTable();
            userDaoHibernate.createUsersTable();
            userDaoHibernate.saveUser(testName, testLastName, testAge);
            List<User> userList = userDaoHibernate.getAllUsers();

            if (userList.size() != 1) {
                Assert.fail("Check method 'saveUser'/'removeUserById' or 'createUsersTable'");
            }
        } catch (Exception e) {
            Assert.fail("Error getting all users from table. Exception thrown.\n" + e);
        }
    }

    @Test
    public void cleanUsersTable() {
        try {
            userDaoHibernate.dropUsersTable();
            userDaoHibernate.createUsersTable();
            userDaoHibernate.saveUser(testName, testLastName, testAge);
            userDaoHibernate.cleanUsersTable();

            if (userDaoHibernate.getAllUsers().size() != 0) {
                Assert.fail("Method cleanUsersTable is incorrectly implemented");
            }
        } catch (Exception e) {
            Assert.fail("Error cleaning users table. Exception thrown\n" + e);
        }
    }

}
