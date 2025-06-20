package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;

    }


    public void viewDoctors(){
        String query = "select * from doctors";
        try{
            ResultSet rs= connection.prepareStatement(query).executeQuery();

            System.out.println("Doctors List Is:");
            System.out.println("+-----------+--------------+----------------+");
            System.out.println("| Doctor Id | Name        | Specialization  |");
            System.out.println("+-----------+--------------+----------------+");
            while(rs.next()){
                int Id = rs.getInt("id");
                String Name = rs.getString("name");
                String specialization = rs.getString("specialization");

                System.out.printf("|%-13s|%-13s|%-15s|\n",Id,Name,specialization);
                System.out.println("+-----------+--------------+---------------+");
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query = "select * from doctors where id = ?";
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
