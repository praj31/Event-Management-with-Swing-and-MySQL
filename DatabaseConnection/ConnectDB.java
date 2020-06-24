package DatabaseConnection;
import java.sql.*;
public class ConnectDB{
    public Connection getConnection(){ 
        String url = "jdbc:mysql://localhost:3306/database-name";
        try{
            return DriverManager.getConnection(url,"username","password");
        }
        catch(SQLException e){ throw new RuntimeException(e); }
    }
}
