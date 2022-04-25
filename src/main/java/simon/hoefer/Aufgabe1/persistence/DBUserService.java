package simon.hoefer.Aufgabe1.persistence;

import jakarta.inject.Inject;
import simon.hoefer.Aufgabe1.SessionService;
import simon.hoefer.Aufgabe1.UserBean;
import simon.hoefer.Aufgabe1.UserServiceInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBUserService implements UserServiceInterface {

    @Inject
    private ConnectionPool connectionPool;

    @Inject
    SQLService sqlService;

    @Inject
    private SessionService sessionService;

    @Override
    public boolean isUserLoggedIn() {
        return sessionService.getUsername() != null;
    }

    @Override
    public UserBean getCurrentUser() {
        String currentUsername = sessionService.getUsername();
        if (currentUsername == null)
        {
            throw new RuntimeException("No user is in session"); // TODO: better exceptions
        }

        return getUser(currentUsername).get();
    }

    @Override
    public boolean isAuthenticateUser(final String username, String password) {
        return !getUser(username).isEmpty() && getUser(username).get().getPassword().equals(password);
    }

    @Override
    public void updateUser(UserBean userBean) {
        String queryString = "Update a1.users" +
                " SET name = ? ," +
                "firstname = ?," +
                " address = ?," +
                " password = ?," +
                " birthdate = ?"+
                " WHERE username = ?;";
        PreparedStatementParameters params = new PreparedStatementParameters();
        params.add(userBean.getName());
        params.add(userBean.getFirstname());
        params.add(userBean.getAddress());
        params.add(userBean.getPassword());
        params.add(userBean.getBirthdate());
        params.add(userBean.getUsername());
        connectionPool.executeUpdateQuery(queryString, params);
    }

    private Optional<UserBean> getUser(String username) {
        PreparedStatementParameters params = new PreparedStatementParameters();
        params.add(username);
        ResultSet result = connectionPool.executeSelectQuery("SELECT * FROM a1.users where usename = ?;", params).get(); // TODO other cases
        return Optional.of(ResultToUsers(result).get().stream().findFirst().orElse(null));
    }

    private Optional<List<UserBean>> ResultToUsers(ResultSet resultSet) {
        List<UserBean> users;
        try {
            users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new UserBean(
                        resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("firstname"),
                        resultSet.getString("address"),
                        resultSet.getString("password"),
                        resultSet.getDate("birthdate")
                ));
            }
            return Optional.of(users);
        } catch (SQLException e) {
            // TODO: log
            return Optional.empty();
        }
    }
}
