import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HotelReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Suraj@7462";





    public static void main(String[] args) throws ClassNotFoundException {

        // Hotel Reservation System
        /*
        * New Reservation
        * Check Reservation
        * Get Room No
        * Update Reservation
        * Delete Reservation
        * Exit
        *
        * Schema
        * reservation_id -> int auto incre
        * guest_name -> varchar not null
        * room_number -> int not null
        * contact number int not null
        * reservation__date -> timestamp default
        *
        *
        *
        * */

        // First i have to add mysql connector j in external libraries




        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


        try{
            // here Connection is an interface
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database...");
            Statement stmt = connection.createStatement();
            while(true){
                System.out.println();
                System.out.println("Hotel Management System");
                Scanner sc = new Scanner(System.in);
                System.out.println("1 Reserve a room");
                System.out.println("2 View all reservations");
                System.out.println("3 Get room number");
                System.out.println("4 Update Reservation");
                System.out.println("5 Delete Reservation");
                System.out.println("0 Exit");
                System.out.println("Enter your choice:");
                int choice = sc.nextInt();
                switch(choice){
                    case 1:
                        reserveRoom(connection,sc,stmt);
                        break;
                    case 2:
                        viewReservation(connection,sc,stmt);
                        break;
                    case 3:
                        getRoomNumber(connection,sc,stmt);
                        break;
                    case 4:
                        updateReservation(connection,sc,stmt);
                        break;
                    case 5:deleteReservation(connection,sc,stmt);
                       break;
                    case 0:
                        existSystem();
                         sc.close();
                         return;
                    default:
                        System.out.println("Invalid choice");
                }

            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

    }
    public static void reserveRoom(Connection connection,Scanner sc,Statement stmt) throws SQLException{
        try{
            System.out.println("Enter Guest Name");
            String guestName = sc.next();
            sc.nextLine();
            System.out.println("Enter Room Number");
            int roomNumber = sc.nextInt();
            System.out.println("Enter Contact Number");
            String contactNumber = sc.next();
            String sql = "INSERT INTO reservation(guest_name, contact_number, room_number) " +
                    "VALUES ('" + guestName + "', '" + contactNumber + "', " + roomNumber + ")";



            /* execueteUpdate() -> Insert,Update,Delete(return int values
            *  executeQuery() -> Retrieve(return data
            * */



            try{
                stmt=connection.createStatement();
                int rowsAffected=stmt.executeUpdate(sql);
                if(rowsAffected>0){
                    System.out.println("Reservation has been reserved.");
                }else {
                    System.out.println("Reservation has not been reserved.");
                }

                stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void viewReservation(Connection connection, Scanner sc, Statement stmt) throws SQLException {
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservation";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\nCurrent Reservations:");
            System.out.println("+------------------+----------------------+--------------+------------------+-------------------+");
            System.out.println("| Reservation ID   | Guest Name           | Room Number  | Contact Number   | Reservation Date  |");
            System.out.println("+------------------+----------------------+--------------+------------------+-------------------+");

            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                Date reservationDate = rs.getDate("reservation_date");

                System.out.printf("| %-16d | %-20s | %-12d | %-16s | %-17s |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate.toString());
            }

            System.out.println("+------------------+----------------------+--------------+------------------+-------------------+");

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getRoomNumber(Connection connection, Scanner sc, Statement stmt) throws SQLException {
        try {
            System.out.println("Enter Reservation Id");
            int reservationId = sc.nextInt();
            System.out.println("Enter Guest Name");
            String guestName = sc.next();

            String sql = "SELECT room_number FROM reservation" + " WHERE reservation_id=" + reservationId + " AND guest_name='" + guestName + "'";
            try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    int roomNumber = rs.getInt("room_number");
                    System.out.println("Roome number for Reservation ID:" + reservationId + " and Guest " + guestName + " is " + roomNumber);
                } else {
                    System.out.println("Room number for Reservation ID:" + reservationId + " is not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
    }
    public static void updateReservation(Connection connection, Scanner sc, Statement stmt) throws SQLException {
        try{
            System.out.println("Enter Reservation Id for Update");
            int reservationId = sc.nextInt();
            sc.nextLine();
            if(!reservationExist(connection,reservationId))
            {
                System.out.println("Reservation does not exist");
                return;
            }
            System.out.println("Enter New Guest Name");
            String guestName = sc.nextLine();
            System.out.println("Enter New Room Number");
            int roomNumber = sc.nextInt();
            System.out.println("Enter New Contact Number");
            sc.nextLine();
            String contactNumber = sc.nextLine();

            String sql = "UPDATE reservation SET guest_name = '" + guestName +
                    "', room_number = " + roomNumber +
                    ", contact_number = '" + contactNumber +

                    "' WHERE reservation_id = " + reservationId;


            try{
                int affectedRows  =connection.createStatement().executeUpdate(sql);
                 if(affectedRows>0){
                     System.out.println("Reservation has been reserved.");
                 }
                 else {
                     System.out.println("Reservation has not been reserved.");
                 }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc, Statement stmt) throws SQLException {

        try{
            System.out.println("Enter Reservation Id to delete: ");
            int reservationId = sc.nextInt();
            if(!reservationExist(connection,reservationId))
            {
                System.out.println("Reservation does not exist");
                return;
            }
            String sql = "DELETE FROM reservation WHERE reservation_id=" + reservationId;
            try {
                int affectedRows = connection.createStatement().executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation has been deleted.");
                } else {
                    System.out.println("Reservation has not been deleted.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static boolean reservationExist(Connection connection, int reservationId) throws SQLException {
        try {
            String sql = "SELECT * FROM reservation WHERE reservation_id=" + reservationId;

            try {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                return rs.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private  static void existSystem() throws InterruptedException {
        System.out.print("Existing System");
        int i=5;
        while(i!=0)
        {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel Reservation System");
    }
}