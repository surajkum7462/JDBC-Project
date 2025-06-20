/*import java.sql.*;

public class Insert {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";

        String query = "INSERT INTO employees(id,name,job_title,salary) values(3,'Shivam','BusinessMan',40000.00)";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database...");
            Statement stmt = con.createStatement();
            int rowsAffected=stmt.executeUpdate(query);
            if(rowsAffected>0)
            {
                System.out.println("Inserted records successfully..."+rowsAffected);
            }else {
                System.out.println("Insert failed..."+rowsAffected);
            }



            stmt.close();
            con.close();


        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }





    }
}*/

import java.sql.*;
import java.util.Scanner;

public class Insert {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";

        String query = "INSERT INTO employees(id, name, job_title, salary) VALUES (?, ?, ?, ?)";

        Scanner sc = new Scanner(System.in);

        try {
            // Load JDBC driver (optional with modern drivers)
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Connect to DB
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database...");
            // User input
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Job Title: ");
            String jobTitle = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();


            // Prepare statement and set values
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, jobTitle);
            ps.setDouble(4, salary);

            // Execute
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully!");
            } else {
                System.out.println("Insert failed.");
            }

            // Close
            ps.close();
            con.close();
            sc.close();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}

