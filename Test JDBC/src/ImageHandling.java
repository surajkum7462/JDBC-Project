import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class ImageHandling {
    public static void main(String[] args) {

        // First i have to add mysql connector j in external libraries

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Suraj@7462";
        String image_path="C:\\Users\\sskum\\Downloads\\caste.jpg";
        String query = "insert into image_table(image_data) values(?)";

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

            FileInputStream fis = new FileInputStream(image_path);
            // it converts byte form of image
            byte[]  imagedata = new byte[fis.available()];
            fis.read(imagedata);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setBytes(1, imagedata);
            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Image has been uploaded");
            }
            else {
                System.out.println("Image has not been uploaded");
            }

            pst.close();
            con.close();








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
