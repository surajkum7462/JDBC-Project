import java.sql.*;
import java.util.Scanner;

public class PreparedStatement1 {

    public static void main(String[] args) {


        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        //String query = "select * from employees where name = ? AND job_title = ?";
        String query = "insert into employees(id,name,job_title,salary) values(?,?,?,?)";

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

          //  Statement stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the employee ID: ");
            int id = sc.nextInt();
            System.out.println("Enter the employee name: ");
            String name = sc.next();
            System.out.println("Enter the employee job title: ");
            String jobTitle = sc.next();
            System.out.println("Enter the employee salary: ");
            double salary = sc.nextInt();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2,name);
            ps.setString(3,jobTitle);
            ps.setDouble(4, salary);
            int rowsAffected= ps.executeUpdate();

            if(rowsAffected>0){
                System.out.println("Rows affected: "+rowsAffected);
            }
            else {
                System.out.println("No rows affected");
            }




            /*while(rs.next()){
                System.out.println("Id:"+rs.getInt(1));
                System.out.println("Name:"+rs.getString("name"));
                System.out.println(rs.getString("salary"));

                System.out.println("Job Title:"+rs.getString("job_title"));
            }*/

            ps.close();
            con.close();



        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }


    }
}
