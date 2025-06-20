import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {
    public static void main(String[] args) {

        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";



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

            con.setAutoCommit(false);
            // Using Simple Statement
            /*Statement stmt = con.createStatement();
            stmt.addBatch("INSERT INTO employees(id,name,job_title,salary) values(10,'RAhul','Backend',45000.00)");
            stmt.addBatch("INSERT INTO employees(id,name,job_title,salary) values(11,'Shivam','AWS',41000.00)");
            stmt.addBatch("INSERT INTO employees(id,name,job_title,salary) values(12,'Aarti','DevOps',49000.00)");
            int[] batchresult= stmt.executeBatch();
            con.commit();
            System.out.println("Batch complete.");*/
            String query = "insert into employees(id,name,job_title,salary) values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print("Enter employee id: ");
                int id = sc.nextInt();
                System.out.print("Enter employee name: ");
                String name = sc.next();
                System.out.print("Enter employee job title: ");
                String jobTitle = sc.next();
                System.out.println("Enter employee salary: ");
                double salary = sc.nextDouble();
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, jobTitle);
                ps.setDouble(4, salary);
                ps.addBatch();
                System.out.println("Add more employees... Y/N");
                String answer = sc.next();
                if(answer.equalsIgnoreCase("N"))
                {
                    break;
                }

            }
            int[] batchResult=ps.executeBatch();
            System.out.println(batchResult[0]+" "+batchResult[1]+" "+batchResult[2]);
            con.commit();
            System.out.println("Batch complete.");

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
