/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;


public class User implements DBTable {

    private int UserID;
    private String UserName;
    private String UserRole;
    private Hashtable<Integer, Inventory> inventories = new Hashtable<Integer, Inventory>();
    private String query;

    public User(int UserId){
        /**constructor to get object from db*/
        this.UserID=UserId;
        query = "Select * From Users Where UserID="+UserId;
        this.inventories=this.fetchInventories();
        Connection conn = SQLiteJDBCDriverConnection.connect();

        try {
            PreparedStatement pstm = null;
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            this.UserName=rs.getString("UserName");
            this.UserRole=rs.getString("UserRole");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    User(String userName, String userRole){

        this.UserName=userName;
        this.UserRole=userRole;
        query="Insert Into Users (UserName, UserRole) Values('"+this.getUserName()+"', '"+this.getUserRole()+"')";
        try {
            this.UserID=addToDb(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.inventories=this.fetchInventories();
    }

    private Hashtable<Integer, Inventory> fetchInventories(){
        Hashtable<Integer, Inventory> host = new Hashtable<>();
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt  = conn.prepareStatement("Select * From Inventory Where UserID="+this.UserID)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                host.put(rs.getInt("InventoryID"), new Inventory(rs.getInt("InventoryID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return host;
    }


    public int getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserName(String userName) {
        UserName = userName;
        String sql = "UPDATE Users SET UserName = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, userName);
            pstmt.setInt(2, this.getUserID());
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
        String sql = "UPDATE Users SET "
                + "UserRole = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, userRole);
            pstmt.setInt(2, this.getUserID());
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int addToDb(String query) throws SQLException {
        int id = -1;
        Connection connection = SQLiteJDBCDriverConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(this.query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet != null && resultSet.next()){
            id = resultSet.getInt(1);
        }
        return id;
    }

    public void removeFromDB(int id) {

        String sql = "DELETE FROM Inventory WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void printData() throws SQLException {
        Connection connection = SQLiteJDBCDriverConnection.connect();
        PreparedStatement pst = connection.prepareStatement("SELECT * from Users Where UserID="+this.UserID);
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

    public Hashtable<Integer, Inventory> getInventories() {
        return inventories;
    }

    public void addInventory(String name){
        Inventory inventory = new Inventory(name, this.getUserID());
        inventories.put(inventory.getInventoryID(), inventory);
    }

    public void removeInventory(int id){
        this.removeFromDB(id);
        this.inventories.remove(id);
    }

    public void editInventory(int id, String inventoryName){
        inventories.get(id).setInventoryName(id, inventoryName);
    }

    public void printInventories(){
        Enumeration itemsid = this.inventories.keys();
        while(itemsid.hasMoreElements()){
            int id = (Integer) itemsid.nextElement();
            System.out.println(this.inventories.get(id));
        }
    }

}
