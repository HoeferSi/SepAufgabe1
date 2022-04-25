package simon.hoefer.Aufgabe1.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import simon.hoefer.Aufgabe1.UserBean;

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



    // "public" wasn't forgot. Connections should only be made inside this package.
    Connection getConnection() {
        Connection conn = null;

        try {
            System.out.println("Loading JDBC Driver");
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found");
            return conn;
        }

        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        props.setProperty("ssl", "true");

        props.setProperty("sslfactory",
                "org.postgresql.ssl.DefaultJavaSSLFactory");

        try {
            System.out.println("Opening Database Connection");
            conn = DriverManager.getConnection("jdbc:postgresql://"
                    + DB_HOST + "/" + DB_NAME, props);
        } catch (SQLException e )
        {
            // TODO: log
        }
        return conn;
    }

    public void close(Connection conn) {
        try {
            conn.close();
        }
        catch (SQLException e) {
            // TODO:log
        }
    }
}



