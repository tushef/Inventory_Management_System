/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Hashtable;


public class Supplier implements DBTable{

    private int SupplierID;
    private String SupplierName;
    private String SupplierEmail;
    private String SupplierContact;
    private Hashtable<Integer, Items> items = new Hashtable<Integer, Items>();
    private String query;

    Supplier(String supplierContact, String supplierEmail, String supplierName){
        this.SupplierContact=supplierContact;
        this.SupplierEmail=supplierEmail;
        this.SupplierName=supplierName;
        this.query="Insert into Supplier (SupplierName, SupplierEmail, SupplierContact) Values('"+this.SupplierName+"', '"+this.SupplierEmail+"', '"+this.SupplierContact+"')";
        try {
            this.SupplierID=addToDb(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public String getSupplierContact() {
        return SupplierContact;
    }

    public String getSupplierEmail() {
        return SupplierEmail;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierContact(String supplierContact) {
        SupplierContact = supplierContact;
    }

    public void setSupplierEmail(String supplierEmail) {
        SupplierEmail = supplierEmail;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
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
        PreparedStatement pst = connection.prepareStatement("SELECT * from Supplier Where SupplierID="+this.SupplierID);
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
