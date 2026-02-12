package sistem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL =
        "jdbc:mysql://localhost:3306/sistem?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL nao encontrado", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexao: " + e.getMessage());
            }
        }
    }
}
