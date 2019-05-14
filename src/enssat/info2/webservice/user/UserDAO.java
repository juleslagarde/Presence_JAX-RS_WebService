package enssat.info2.webservice.user;

import java.util.List;

public interface UserDAO {
    boolean create(User user);
    List<User> findAll();
    User findByLogin(String login);
    boolean update(User user);
    boolean delete(User user);
}
