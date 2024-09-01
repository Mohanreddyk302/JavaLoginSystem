import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginSystem {

    public static void main(String[] args) {
        // Database credentials
        String jdbcURL = "jdbc:mysql://localhost:3306/login_db"; // Your database URL
        String dbUsername = "root";  // Database username
        String dbPassword = "mohan";  // Database password

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        // Validate login
        if (validateLogin(jdbcURL, dbUsername, dbPassword, username, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed!");
        }
        
        scanner.close();
    }

    private static boolean validateLogin(String jdbcURL, String dbUsername, String dbPassword, String username, String password) {
        boolean isValid = false;

        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);

            // SQL query to check if the user exists
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";

            // Prepare statement to protect against SQL injection
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                isValid = true;
            }

            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
