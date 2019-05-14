package enssat.info2.webservice.user;

import java.util.List;

public interface UserService {
    boolean newUser(User user);
    List<User> getAllUsers();
    User getUserByLogin(String login);
    boolean updateUser(User user);
    boolean deleteUser(User user);
}
