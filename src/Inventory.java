/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class Inventory implements DBTable{
    private int inventoryID;
    private String inventoryName;
    private int UserID;
    private Hashtable<Integer, Items> items = new Hashtable<Integer, Items>();
    private String query;

    public Inventory(int inventoryID){
        /**constructor to get object from db*/
        this.inventoryID=inventoryID;
        query = "Select * From Inventory Where InventoryID="+inventoryID;
        this.items=this.fetchAllItems();
        Connection conn = SQLiteJDBCDriverConnection.connect();

        try {
            PreparedStatement pstm = null;
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            this.inventoryName=rs.getString("InventoryName");
            this.UserID=rs.getInt("UserID");
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

    Inventory(String inventoryName, int UserID){
        this.inventoryName=inventoryName;
        this.UserID=UserID;
        this.query="Insert Into Inventory (InventoryName, UserID) Values('" + this.inventoryName + "', " + this.UserID + ")";
        try {
            this.inventoryID=addToDb(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.items=this.fetchAllItems();
    }

    private Hashtable<Integer, Items> fetchAllItems(){
        Hashtable<Integer, Items> host = new Hashtable<>();
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt  = conn.prepareStatement("Select * From Items Where InventoryID="+this.inventoryID)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                host.put(rs.getInt("ItemID"), new Items(rs.getInt("ItemID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return host;
    }

    public int getUserID() {
        return UserID;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public Hashtable<Integer, Items> getItems() {
        return items;
    }

    public void setUserID(int id, int userID) {
        UserID = userID;
        String sql = "UPDATE Inventory SET "
                + "UserID = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, UserID);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setInventoryName(int id, String inventoryName) {
        this.inventoryName = inventoryName;
        String sql = "UPDATE Inventory SET "
                + "InventoryName = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, inventoryName);
            pstmt.setInt(2, id);
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
        if(connection!=null){
            connection.close();
        }
        return id;
    }


    public void removeFromDB(int id){
        String sql = "DELETE FROM Items WHERE id = ?";

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
        PreparedStatement pst = connection.prepareStatement("SELECT * from Inventory Where InventoryID="+this.inventoryID);
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

    public void addItems(Items item){
        this.items.put(item.getItemID(), item);
    }

    public void removeItem(int id){
        this.removeFromDB(id);
        this.items.remove(id);
    }
    public void editItemInfo(int id, String itemName, int supplierID, int categoryID, int unitPrice, int unitOnStock, int totalProducedUnits, int unitsOnPreorder, int reorderLevel, boolean discontinued){
        String sql = "UPDATE Items SET "
                + "ItemName = ? "
                + "SupplierID = ?"
                + "CategoryID = ?"
                + "UnitPrice = ?"
                + "UnitOnStock = ?"
                + "TotalProducedUnits = ?"
                + "UnitsOnPreorder = ?"
                + "Discontinued = ?"
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, itemName);
            pstmt.setInt(2, supplierID);
            pstmt.setInt(3, categoryID);
            pstmt.setDouble(4, unitPrice);
            pstmt.setInt(5, unitOnStock);
            pstmt.setInt(6, totalProducedUnits);
            pstmt.setInt(7, unitsOnPreorder);
            pstmt.setInt(8, reorderLevel);
            pstmt.setBoolean(9, discontinued);
            pstmt.setInt(10, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printItems(){
        Enumeration itemsid = this.items.keys();
        while(itemsid.hasMoreElements()){
            int id = (Integer) itemsid.nextElement();
            try {
                this.items.get(id).printData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}