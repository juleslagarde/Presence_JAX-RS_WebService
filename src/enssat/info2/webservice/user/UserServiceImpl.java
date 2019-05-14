package enssat.info2.webservice.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private UserDAO userDAO = new UserDAOMockImpl();

    /**
     * Returns the user.UserServiceImpl singleton
     * @return user.UserServiceImpl instance
     */
    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                instance = new UserServiceImpl();
            }
            for (int i = 0; i < 8; i++) {
                instance.newUser(new User("login"+i, "status"+i));
            }
        }
        return instance;
    }

    /**
     * Private constructor since it's a singleton
     */
    private UserServiceImpl() {}

    /**
     * Creates a new user
     * @param user user.User to create
     * @pre user.getID() == -1
     * @post user.getID() != -1
     * @return The user has been successfully created
     */
    @Override
    public boolean newUser(User user) {
        //hash password and answer
        return userDAO.create(user);
    }

    /**
     * Returns all users
     * @return A list of users
     */
    @Override
    public List<User> getAllUsers() { return userDAO.findAll(); }

    /**
     * Returns a user by ID
     * @param login user.User's login
     * @return The user with that login or null if nonexistent
     */
    @Override
    public User getUserByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    /**
     * Updates a user
     * @param user user.User to update
     * @return The user has been successfully updated
     */
    @Override
    public boolean updateUser(User user) { return userDAO.update(user); }

    /**
     * Deletes a user
     * @param user user.User to delete
     * @return The user has been successfully deleted
     */
    @Override
    public boolean deleteUser(User user) { return userDAO.delete(user); }

    /**
     * Hashes a string with SHA-256 in base 64
     * @param str String to hash
     * @return hashed string
     */
    public String hash(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            String hashStr = Base64.getEncoder().encodeToString(hash);
            return hashStr;
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }
}
