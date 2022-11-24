import org.firstproject.dao.UserDaoJdbcImpl;
import org.firstproject.model.jdbc.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserDaoJdbcTest {
    // JDBC TESTS
    private final UserDaoJdbcImpl userService = new UserDaoJdbcImpl();
    private final String testName = "Marco";
    private final String testLastName = "Rossi";
    private final byte testAge = 36;

    @Test
    public void dropUsersTable() {
        try {
            userService.dropUsersTable();
            userService.dropUsersTable();
        } catch (Exception e) {
            Assert.fail("Error dropping users table. Exception thrown\n" + e);
        }
    }

    @Test
    public void createUsersTable() {
        try {
            userService.dropUsersTable();
            userService.createUsersTable();
        } catch (Exception e) {
            Assert.fail("Error creating users table. Exception thrown.\n" + e);
        }
    }

    @Test
    public void saveUser() {
        try {
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);

            User user = userService.getAllUsers().get(0);

            if (!testName.equals(user.getName())
                    || !testLastName.equals(user.getLastName())
                    || testAge != user.getAge()
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
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);
            userService.removeUserById(1L);
        } catch (Exception e) {
            Assert.fail("Error removing user by id. Exception thrown.\n" + e);
        }
    }

    @Test
    public void getAllUsers() {
        try {
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);
            List<User> userList = userService.getAllUsers();

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
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);
            userService.cleanUsersTable();

            if (userService.getAllUsers().size() != 0) {
                Assert.fail("Method cleanUsersTable is incorrectly implemented");
            }
        } catch (Exception e) {
            Assert.fail("Error cleaning users table. Exception thrown\n" + e);
        }
    }

}
