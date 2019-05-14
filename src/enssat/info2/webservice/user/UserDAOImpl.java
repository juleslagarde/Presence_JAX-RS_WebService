//package enssat.info2.webservice.user;
//
//import enssat.info2.webservice.DAOImpl;
//
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public class UserDAOImpl extends DAOImpl<User> implements UserDAO {
//
//    @Override
//    public boolean create(User user) {
//        String query = "insert into user.User (login, pwdHash, question, answerHash, rights) values (?, ?, ?, ?, ?)";
//        return executeUniqueUpdateQuery(query,
//                preparedStatement -> { buildUserStatement(user, preparedStatement); },
//                generatedKeys -> {
//                    if (generatedKeys.next()) {
//                        user.setID(generatedKeys.getInt(1));
//                        System.out.println("Successfully created " + user);
//                    } else {
//                        throw new SQLException("No ID generated for " + user);
//                    }
//                });
//    }
//
//    @Override
//    public List<User> findAll() {
//        String query = "select * from user.User";
//        return getEntriesFromQuery(query, preparedStatement -> {});
//    }
//
//    @Override
//    public User findByLogin(int ID) {
//        String query = "select * from user.User where ID_user=?";
//        return getUniqueEntryFromQuery(query, preparedStatement -> {
//            preparedStatement.setInt(1, ID);
//        });
//    }
//
//    @Override
//    public User findByLogin(String login) {
//        String query = "select * from user.User where login=?";
//        return getUniqueEntryFromQuery(query, preparedStatement -> {
//            preparedStatement.setString(1, login);
//        });
//    }
//
//    @Override
//    public boolean update(User user) {
//        String query = "update user.User set " +
//                "login=?," +
//                "pwdHash=?," +
//                "question=?," +
//                "answerHash=?," +
//                "rights=? " +
//                "where ID_user=?";
//        boolean updated = executeUniqueUpdateQuery(query, preparedStatement -> {
//            buildUserStatement(user, preparedStatement);
//            preparedStatement.setInt(6, user.getID());
//        });
//        if (updated)  {
//            System.out.println("Successfully updated " + user);
//        }
//        return updated;
//    }
//
//    @Override
//    public boolean delete(User user) {
//        String query = "delete from user.User where ID_user=?";
//        boolean deleted = executeUniqueUpdateQuery(query, preparedStatement -> {
//            preparedStatement.setInt(1, user.getID());
//        });
//        if (deleted) {
//            System.out.println("Successfully deleted " + user);
//        }
//        return deleted;
//    }
//
//    @Override
//    public User entryFromResultSet(ResultSet resultSet) throws SQLException {
//        int ID = resultSet.getInt("ID_user");
//        String login = resultSet.getString("login");
//        String pwdHash = resultSet.getString("pwdHash");
//        String question = resultSet.getString("question");
//        String answerHash = resultSet.getString("answerHash");
//        User.Rights rights = User.Rights.valueOf(resultSet.getString("rights"));
//        User user = new User(login, pwdHash, question, answerHash, rights);
//        user.setID(ID);
//        return user;
//    }
//
//    private void buildUserStatement(User user, PreparedStatement preparedStatement) throws SQLException {
//        preparedStatement.setString(1, user.getLogin());
//        preparedStatement.setString(2, user.getPwdHash());
//        preparedStatement.setString(3, user.getQuestion());
//        preparedStatement.setString(4, user.getAnswerHash());
//        preparedStatement.setString(5, user.getRights().toString());
//    }
//}
