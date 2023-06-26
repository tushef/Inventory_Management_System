/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.*;


public class Items implements DBTable{
    private int ItemID;
    private int InventoryID;
    private String ItemName;
    private int SupplierID;
    private int CategoryID;
    private double UnitPrice;
    private int UnitOnStock;
    private int TotalProducedUnits;
    private int UnitsOnPreorder;
    private int ReorderLevel;
    private boolean Discontinued;
    private String query;

    Items(int itemId){
        /**constructor to get object from db*/
        this.ItemID=itemId;
        query = "Select * From Items Where ItemID="+ItemID;
        Connection conn = SQLiteJDBCDriverConnection.connect();

        try {
            PreparedStatement pstm = null;
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            this.InventoryID=rs.getInt("InventoryID");
            this.Discontinued = rs.getBoolean("Discontinued");
            this.CategoryID = rs.getInt("CategoryID");
            this.ItemName=rs.getString("ItemName");
            this.ReorderLevel=rs.getInt("ReorderLevel");
            this.SupplierID=rs.getInt("SupplierID");
            this.UnitOnStock=rs.getInt("UnitOnStock");
            this.TotalProducedUnits=rs.getInt("TotalProducedUnits");
            this.UnitPrice=rs.getInt("UnitPrice");
            this.UnitsOnPreorder=rs.getInt("UnitsOnPreorder");
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

    public int getTotalProducedUnits() {
        return TotalProducedUnits;
    }


    Items(String itemName, int supplierID, int categoryID, int inventoryID, double unitPrice, int unitOnStock, int TotalProducedUnits, int unitsOnPreorder, int reorderLevel, boolean discontinued){
        this.InventoryID=inventoryID;
        this.Discontinued = discontinued;
        this.CategoryID = categoryID;
        this.ItemName=itemName;
        this.ReorderLevel=reorderLevel;

        this.SupplierID=supplierID;
        this.UnitOnStock=unitOnStock;
        this.TotalProducedUnits=TotalProducedUnits;
        this.UnitPrice=unitPrice;
        this.UnitsOnPreorder=unitsOnPreorder;
        query = "Insert Into Items (ItemName, InventoryID, SupplierID, CategoryID, UnitPrice, UnitOnStock, TotalProducedUnits, UnitsOnPreorder, ReorderLevel, Discontinued) "+
                "Values('"+this.ItemName+"', "+this.InventoryID+", "+this.SupplierID+", "+this.CategoryID+", "+this.UnitPrice+","+this.UnitOnStock+", "+this.TotalProducedUnits+", "+this.UnitsOnPreorder+", "+this.ReorderLevel+","+this.Discontinued+")";
        try {
            this.ItemID=addToDb(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInventoryID() {
        return InventoryID;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public boolean isDiscontinued() {
        return Discontinued;
    }

    public int getItemID() {
        return ItemID;
    }

    public int getReorderLevel() {
        return ReorderLevel;
    }

    public int getUnitOnStock() {
        return UnitOnStock;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getUnitsOnPreorder() {
        return UnitsOnPreorder;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
        String sql = "UPDATE Items SET "
                + "CategoryID = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, categoryID);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setDiscontinued(boolean discontinued) {
        Discontinued = discontinued;
        String sql = "UPDATE Items SET "
                + "Discontinued = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setBoolean(1, discontinued);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
        String sql = "UPDATE Items SET "
                + "ItemName = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, itemName);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setReorderLevel(int reorderLevel) {
        ReorderLevel = reorderLevel;
        String sql = "UPDATE Items SET "
                + "ReorderLevel = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, reorderLevel);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
        String sql = "UPDATE Items SET "
                + "SupplierID = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, supplierID);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUnitOnStock(int unitOnStock) {
        UnitOnStock = unitOnStock;
        String sql = "UPDATE Items SET "
                + "UnitOnStock = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, unitOnStock);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
        String sql = "UPDATE Items SET "
                + "UnitsOnPreorder = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setDouble(1, unitPrice);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setTotalProducedUnits(int totalProducedUnits) {
        TotalProducedUnits = totalProducedUnits;
        String sql = "UPDATE Items SET "
                + "TotalProducedUnits = ?"
                + "WHERE id = ?";

        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, TotalProducedUnits);
            pstmt.setInt(2, this.ItemID);

            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void setUnitsOnPreorder(int unitsOnPreorder) {
        UnitsOnPreorder = unitsOnPreorder;
        String sql = "UPDATE Items SET "
                + "UnitsOnPreorder = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, unitsOnPreorder);
            pstmt.setInt(2, this.ItemID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setInventoryID(int inventoryID) {
        InventoryID = inventoryID;
        String sql = "UPDATE Items SET "
                + "InventoryID = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, inventoryID);
            pstmt.setInt(2, this.ItemID);
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

    @Override
    public void printData() throws SQLException {
        Connection connection = SQLiteJDBCDriverConnection.connect();
        PreparedStatement pst = connection.prepareStatement("SELECT * from Items Where ItemID="+this.getItemID());
        ResultSet resultSet = pst.executeQuery();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");

                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
                if(i == 3){
                    String sql = "Select * From Category Where CategoryID = "+this.getCategoryID();
                    PreparedStatement pstm = connection.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();
                    System.out.println(rs.getString("CategoryName")
                    +rs.getString("CategoryDesc"));

                }else if(i == 4){
                    String sql = "Select * From Supplier Where supplierID = "+this.getCategoryID();
                    PreparedStatement pstm = connection.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();
                    System.out.println(rs.getString("SupplierName")
                            +rs.getString("SupplierEmail")
                            +rs.getString("SupplierContact")
                    );
                }
            }
            System.out.println("");
        }
        if(connection!=null){
            connection.close();
        }
    }



}
