/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class Category implements DBTable{
    private int categoryID;
    private String categoryName;
    private String description;
    private Hashtable<Integer, Items> items = new Hashtable<Integer, Items>();
    private String query;

    Category(int categoryID){
        this.categoryID=categoryID;
        this.query="Select * from Items Where CategoryID="+Integer.toString(categoryID);
        try(Connection conn = SQLiteJDBCDriverConnection.connect()){
            PreparedStatement pstm = conn.prepareStatement(this.query);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                this.items.put(rs.getInt("ItemID"), new Items(rs.getInt("ItemID")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Hashtable<Integer, Items> getItems() {
        return items;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

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
        PreparedStatement pst = connection.prepareStatement("SELECT * from Category Where CategoryID = "+this.categoryID);
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
