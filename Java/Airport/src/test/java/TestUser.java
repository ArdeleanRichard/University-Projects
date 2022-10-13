import dao.UserDAO;
import model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestUser {
    UserDAO userDAO;

    @Before
    public void init()
    {
        userDAO=new UserDAO();
    }

    @Test
    public void testInsert() {
        User user = new User("a", "a", "a", false);
        User user1 = userDAO.findById(Long.valueOf(1));
        assertEquals(user.getPassword(), user1.getPassword());
        //assertNotEquals(null, userDAO.insert(user));
    }
}
