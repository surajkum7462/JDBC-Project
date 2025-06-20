import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
    public static void main(String[] args) {
        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        String query = "update employees set job_title='Full Stack Developer' where id=1";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


        try{
            // here Connection is an interface
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database...");

            Statement stmt = con.createStatement();
            int rowsafftected=stmt.executeUpdate(query);
            System.out.println("Updated Successfully "+rowsafftected+" rows");


            stmt.close();
            con.close();
            System.out.println("Connection closed.");




        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
