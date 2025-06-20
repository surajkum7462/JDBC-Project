//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.*;
public class Main {
    public static void main(String[] args) throws  ClassNotFoundException{

        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        String query = "select * from employees";


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
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");

                String name = rs.getString("name");
                String job_title = rs.getString("job_title");
                double salary = rs.getDouble("salary");
                System.out.println();
                System.out.println("Employee Details:");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Job Title: " + job_title);
                System.out.println("Salary: " + salary);

            }
            rs.close();
            stmt.close();
            con.close();
            System.out.println("Connection closed.");




        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }


    }
}