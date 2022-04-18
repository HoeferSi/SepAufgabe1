package simon.hoefer.Aufgabe1;

import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

@ApplicationScoped
public class SQLService {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_HOST = "bueno.fim.uni-passau.de";

    private static final String DB_NAME = "hoefers";
    private static final String DB_USER = "hoefers";
    private static final String DB_PASSWORD = "";

    private <R> R executeQuery(Function<Connection, R> toDo) {
        Connection conn = null;

        R toReturn = null;

        try {
            System.out.println("Loading JDBC Driver");
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found");
            return toReturn;
        }

        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        props.setProperty("ssl", "true");

        props.setProperty("sslfactory",
                "org.postgresql.ssl.DefaultJavaSSLFactory");

        // insecure: omitting server cert validation, e.g., if root ca is not
        // contained in jvm cert store (OracleJDK and OpenJDK do contain
        // it automatically)
        // props.setProperty("sslfactory",
        //    "org.postgresql.ssl.NonValidatingFactory");

        try {
            System.out.println("Opening Database Connection");
            conn = DriverManager.getConnection("jdbc:postgresql://"
                    + DB_HOST + "/" + DB_NAME, props);

            toReturn = toDo.apply(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close DB connection even if we have an exception
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(
                        "Exception while closing Database Connection:");
                e.printStackTrace();
            }
            return toReturn;
        }
    }

    public List<UserBean> getUsers() {
        String queryString = "SELECT * FROM a1.users;";
        return executeQuery((conn) -> {
            List<UserBean> users = new ArrayList<>();
            try {
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(queryString);


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
                stmt.close();
            } catch (Exception e) {

            }
            return users;
        });
    }

    public void updateUser(UserBean user) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String queryString = "Update a1.users" +
                " SET name = '" + user.getName() + "' ," +
                "firstname = '" + user.getFirstname() + "'," +
                " address = '" + user.getAddress() + "'," +
                " password = '" + user.getPassword() + "'," +
                " birthdate = '" + dateFormatter.format(user.getBirthdate()) +"'"+
                " WHERE username = '" + user.getUsername() + "';";
        executeQuery(connection -> {
            try {
                Statement statement = connection.createStatement();
                statement.execute(queryString);
                statement.close();
            } catch (Exception e) {
            }
            return null;
        });
    }
}



