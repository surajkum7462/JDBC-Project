package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String user="root";
    private static final String password="Suraj@7462";
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected to database");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
       Scanner sc=new Scanner(System.in);
        try{
            Connection con= DriverManager.getConnection(url,user,password);
            Patient patient = new Patient(con,sc);
            Doctor doctor = new Doctor(con);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice");
                int choice=sc.nextInt();
                switch(choice){
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;

                    case 3:
                        // View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;

                    case 4:
                        // Book
                        bookAppointment(patient,doctor,con,sc);
                        break;

                    case 5:
                        System.out.println("Thank You");return;
                    default:
                        System.out.println("Invalid choice");


                }

            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public static void bookAppointment(Patient patient,Doctor doctor,Connection con,Scanner sc){
        System.out.println("Enter Patient ID: ");
        int patientID=sc.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorID=sc.nextInt();
        System.out.println("Enter appointment Date (YYYY-MM-DD): ");
        String appointmentDate=sc.next();
        if(patient.getPatientById(patientID) && doctor.getDoctorById(doctorID) && appointmentDate!=null){
            if(checkDoctorAvailability(doctorID,appointmentDate,con))
            {
                String query="insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                try{
                    PreparedStatement pst=con.prepareStatement(query);
                    pst.setInt(1,patientID);
                    pst.setInt(2,doctorID);
                    pst.setString(3,appointmentDate);
                    int rows=pst.executeUpdate();
                    if(rows>0)
                    {
                        System.out.println("Appointment Booked Successfully");
                    }
                    else System.out.println("Appointment Booked Failed");
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor is not available on this date");
            }
        }else {
            System.out.println("Patient or Doctor does not exist");
        }

    }
    public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection con){
          String query = "select count(*) from appointments where doctor_id=? and appointment_date=?";
          try{
              PreparedStatement pst=con.prepareStatement(query);
              pst.setInt(1,doctorID);
              pst.setString(2,appointmentDate);
              ResultSet rs=pst.executeQuery();
              if (rs.next()){
                  int count=rs.getInt(1);
                  if(count==0)
                  {
                      return true;
                  }else{
                      return false;
                  }
              }

          }catch (SQLException e)
          {
              e.printStackTrace();
          }
          return false;
    }
}
