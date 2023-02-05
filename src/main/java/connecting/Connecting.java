package connecting;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connecting {
     public Connection connection() {
        Connection conn = null;
        String url = "jdbc:postgresql://localhost:5432/ticketplace";
        String user = "postgres";
        String password = "postgres";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
