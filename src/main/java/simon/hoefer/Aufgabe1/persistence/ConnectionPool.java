package simon.hoefer.Aufgabe1.persistence;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.Optional;

@ApplicationScoped
public class ConnectionPool {

    private Connection connection;

    private boolean isAvailable;
    private boolean isDone;

    @Inject
    private SQLService sqlService;


    @PostConstruct
    public void ínit() {
        this.connection = sqlService.getConnection();
        this.isAvailable = true;
        this.isDone = false;
    }

    public synchronized Optional<ResultSet> executeSelectQuery(String query, PreparedStatementParameters queryParams) {
        ResultSet resultSet = null;
        checkAvailability();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            resultSet = stmt.executeQuery();
            stmt.close();
        } catch (SQLException e) {
            // Todo: log
        }
        return Optional.of(resultSet);
    }

    public synchronized void executeUpdateQuery(String query, PreparedStatementParameters queryParams) {
        checkAvailability();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            queryParams.setParameter(statement).executeUpdate();
            statement.close();
        } catch (SQLException e) {
            // TODO: log
        }
    }

    private void checkAvailability() {
        try {
            if (isDone) {
                throw new RuntimeException("its done");
            }
            while (!isAvailable) {
                wait();
            }
        } catch (InterruptedException e) {
            // Todo: log
        }
    }

    public synchronized void close() {
        try {
            while (!isAvailable) {
                wait();
            }
        } catch (InterruptedException e) {
            // TODO: log.
        }
        sqlService.close(connection);
        this.isDone = true;
    }
}
