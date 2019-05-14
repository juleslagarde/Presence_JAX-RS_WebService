package enssat.info2.webservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for DAOs, handles database connection, query execution and result sets.
 * @param <T> class on which the DOA operates
 */
public abstract class DAOImpl<T> {

    DBManager dbManager = DBManager.getInstance();

    /**
     * Returns a list of objects from the database
     * @param query The query to execute
     * @pre The query must be a select statement.
     * @param statementBuilder See StatementBuilder
     * @return The list of objects fetched from that query
     */
    protected List<T> getEntriesFromQuery(String query, StatementBuilder statementBuilder) {
        try {
            Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            statementBuilder.build(preparedStatement);
            /*
            executeQuery method execute statements that returns a result set
            by fetching some data from the database.
            It executes only select statements.
             */
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entries = entriesFromResultSet(resultSet);
            //connection.close();
            return entries;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

    /**
     * Executes a query on the database
     * @param query The query to execute
     * @pre The query must be a non-select statement
     * @param statementBuilder See StatementBuilder
     * @param generatedKeysChecker See GeneratedKeysChecker
     * @return Number of records affected by that query
     */
    protected int executeUpdateQuery(String query, StatementBuilder statementBuilder, GeneratedKeysChecker generatedKeysChecker) {
        try {
            Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statementBuilder.build(preparedStatement);
            /*
           executeUpdate method execute sql statements that insert/update/delete data at the database.
           This method return int value representing number of records affected.
           Returns 0 if the query returns nothing.
           The method accepts only non-select statements.
             */
            int records_affected = preparedStatement.executeUpdate();
            if (generatedKeysChecker != null) {
                generatedKeysChecker.check(preparedStatement.getGeneratedKeys());
            }
            //connection.close();
            return records_affected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Executes a query on the database
     * @param query The query to execute
     * @param statementBuilder See StatementBuilder
     * @return Number of records affected by that query
     */
    protected int executeUpdateQuery(String query, StatementBuilder statementBuilder) {
        return executeUpdateQuery(query, statementBuilder, null);
    }

    /**
     * Returns a unique object from the database
     * @param query The query to execute
     * @param statementBuilder See StatementBuilder
     * @return The first object fetched by that query (null if none)
     */
    protected T getUniqueEntryFromQuery(String query, StatementBuilder statementBuilder) {
        List<T> entries = getEntriesFromQuery(query, statementBuilder);
        return entries.isEmpty() ? null : entries.get(0);
    }

    /**
     * Executes a query on the database
     * @param query The query to execute
     * @pre The query must be a non-select statement
     * @param statementBuilder See StatementBuilder
     * @param generatedKeysChecker See GeneratedKeysChecker
     * @return The query was a success
     */
    protected boolean executeUniqueUpdateQuery(String query, StatementBuilder statementBuilder, GeneratedKeysChecker generatedKeysChecker) {
        return executeUpdateQuery(query, statementBuilder, generatedKeysChecker) == 1;
    }

    /**
     * Executes a query on the database
     * @param query The query to execute
     * @pre The query must be a non-select statement
     * @param statementBuilder See StatementBuilder
     * @return The query was a success
     */
    protected boolean executeUniqueUpdateQuery(String query, StatementBuilder statementBuilder) {
        return executeUpdateQuery(query, statementBuilder) == 1;
    }

    /**
     * Constructs a list of objects from a ResultSet
     * @param resultSet A ResultSet from a query
     * @return The list of objects constructed from that resultSet
     * @throws SQLException
     */
    private List<T> entriesFromResultSet(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();
        // Iterates over that resultSet calling entryFromResultSet to construct a list of objects
        while(resultSet.next()) {
            list.add(this.entryFromResultSet(resultSet));
        }
        return list;
    }

    /**
     * Constructs an object from a ResultSet
     * @param resultSet A ResultSet from a query
     * @return An object constructed from that resultSet
     * @throws SQLException
     * That function must be implemented in child classes
     */
    public abstract T entryFromResultSet(ResultSet resultSet) throws SQLException;

    /**
     * Interface for a lambda function
     */
    public interface StatementBuilder {
        /**
         * Handles preparedStatement building
         * @param preparedStatement The preparedStatement created from a query
         * @throws SQLException
         */
        void build(PreparedStatement preparedStatement) throws SQLException;
    }

    public interface GeneratedKeysChecker {
        /**
         * Handles keys generated from a statement execution
         * @param generatedKeys Keys generated by a query
         * @throws SQLException
         */
        void check(ResultSet generatedKeys) throws SQLException;
    }
}
