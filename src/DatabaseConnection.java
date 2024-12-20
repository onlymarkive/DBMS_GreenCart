import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/greencartdb";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "Mawrkive01192005"; // Your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database.", e);
        }
    }
}
