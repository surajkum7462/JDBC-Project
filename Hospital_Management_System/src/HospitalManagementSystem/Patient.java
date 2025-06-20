package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
   private Scanner scanner;
   public Patient(Connection connection,Scanner scanner) {
       this.connection = connection;
       this.scanner = scanner;
   }

   public void addPatient(){
       System.out.print("Enter the name of the patient");
       String name = scanner.next();
       scanner.nextLine();
       System.out.print("Enter the age of the patient");
       int age = scanner.nextInt();
       System.out.print("Enter the gender of the patient");
       String gender = scanner.next();


       try{
           String query = "insert into patients(name,age,gender) values(?,?,?)";
           PreparedStatement ps = connection.prepareStatement(query);
           ps.setString(1,name);
           ps.setInt(2,age);
           ps.setString(3,gender);
           int rowsAffected =ps.executeUpdate();
           if(rowsAffected>0){
               System.out.println("Patient added successfully");
           }
           else{
               System.out.println("Patient not added");
           }
       }catch (SQLException e)
       {
           e.printStackTrace();
       }
   }
   public void viewPatient(){
       String query = "select * from patients";
       try{
          ResultSet rs= connection.prepareStatement(query).executeQuery();

           System.out.println("Patients List Is:");
           System.out.println("+-------------+---------------+-------------------+------------+");
           System.out.println("| Patient Id  | Name          | Age               | Gender     |");
           System.out.println("+-------------+---------------+-------------------+------------+");

           while (rs.next()) {
               int patientId = rs.getInt("id");
               String patientName = rs.getString("name");
               String age = rs.getString("age");
               String gender = rs.getString("gender");

               System.out.printf("| %-11s | %-13s | %-17s | %-10s |\n", patientId, patientName, age, gender);
           }

           System.out.println("+-------------+---------------+-------------------+------------+");


       }catch (SQLException e)
       {
           e.printStackTrace();
       }
   }

   public boolean getPatientById(int id){
       String query = "select * from patients where id = ?";
       try{
           PreparedStatement ps = connection.prepareStatement(query);
           ps.setInt(1,id);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
               return true;
           }
           else{
               return false;
           }
       }catch (SQLException e)
       {
           e.printStackTrace();
       }
       return false;
   }

}
