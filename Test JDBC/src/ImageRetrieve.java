import java.io.*;
import java.sql.*;

public class ImageRetrieve {
    public static void main(String[] args) {


        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        String folder_path="S:\\JDBC Tutorial\\Test JDBC\\";
        String query = "select image_data from image_table where image_id=(?)";

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

           PreparedStatement ps = con.prepareStatement(query);
           ps.setInt(1, 1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()) {
               byte[] image_data =rs.getBytes("image_data");
               String image_path=folder_path+"extractedImage.jpg";
               OutputStream output = new FileOutputStream(image_path);
               output.write(image_data);
           }
           else {
               System.out.println("Image not found");
           }








            System.out.println("Connection closed.");




        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
