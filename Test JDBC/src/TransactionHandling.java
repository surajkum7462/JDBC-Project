import java.sql.*;

public class TransactionHandling {

    public static void main(String[] args) {

        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

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
            try{
                PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = con.prepareStatement(depositQuery);
                withdrawStatement.setDouble(1, 500.00);
                withdrawStatement.setString(2,"account456");
                depositStatement.setDouble(1, 500.00);
                depositStatement.setString(2,"account123");
              int rowsAffectedWithdraw =   withdrawStatement.executeUpdate();
              int rowsAffectedDeposit= depositStatement.executeUpdate();
              if(rowsAffectedWithdraw > 0 && rowsAffectedDeposit > 0) {
                  con.commit();
                  System.out.println("Transaction successful!");
              }
              else {
                  con.rollback();
                  System.out.println("Transaction failed!");
              }

            }catch (SQLException e) {

                System.out.println(e.getMessage());
            }















        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
