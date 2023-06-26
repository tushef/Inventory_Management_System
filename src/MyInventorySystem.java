/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Hashtable;

public class  MyInventorySystem {

    public static void main(String [] args){

        Hashtable<Integer, Admin> admins = fetchAllAdmins();
        Hashtable<Integer, User> users = fetchAllUsers();
        try {
            admins.get(2).printData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static Hashtable<Integer,User> fetchAllUsers() {
        Hashtable<Integer, User> host = new Hashtable<>();
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt  = conn.prepareStatement("Select UserID From Users")){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                host.put(rs.getInt("UserID"), getUser(rs.getInt("UserID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return host;
    }

    public static Hashtable<Integer, Admin> fetchAllAdmins(){
        Hashtable<Integer, Admin> host = new Hashtable<>();
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt  = conn.prepareStatement("Select AdminID From Admin")){

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                host.put(rs.getInt("AdminID"), getAdmin(rs.getInt("AdminID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return host;
    }

    public static Admin getAdmin(int id) throws SQLException {
        return new Admin(id);
    }

    public static User getUser(int id) throws SQLException {
        return new User(id);
    }

    public static void printData(String add) throws SQLException {
        Connection connection = SQLiteJDBCDriverConnection.connect();
        PreparedStatement pst = connection.prepareStatement("SELECT * from " + add);
        ResultSet resultSet = pst.executeQuery();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
        if(connection!=null){
            connection.close();
        }
    }
}
