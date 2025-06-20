import java.sql.*;

public class Delete {
    public static void main(String[] args) {
        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        String query = "delete from employees where id=3";


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
            System.out.println("Deleted "+rowsafftected+" rows");


            stmt.close();
            con.close();
            System.out.println("Connection closed.");




        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
