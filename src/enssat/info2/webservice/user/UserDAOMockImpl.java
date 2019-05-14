package enssat.info2.webservice.user;

import java.util.*;

public class UserDAOMockImpl implements UserDAO {

    Map<String, User> users = new HashMap<>();

    @Override
    public boolean create(User user) {
        if(users.containsKey(user.login)) return false;
        users.put(user.login, user);
        return true;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findByLogin(String login) {
        return users.get(login);
    }

    @Override
    public boolean update(User user) {
        if(!users.containsKey(user.login)) return false;
        users.put(user.login, user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        if(!users.containsKey(user.login)) return false;
        users.remove(user.login);
        return true;
    }
}
