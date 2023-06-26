import java.sql.*;
import java.util.Hashtable;

public class Admin implements DBTable {

    private int AdminId;
    private String AdminName;
    private String AdminEmail;
    private String query;

    public Admin(int AdminId){
        /**constructor to get object from db*/
        this.AdminId=AdminId;
        query = "Select * From Admin Where AdminID="+AdminId;
        Connection conn = SQLiteJDBCDriverConnection.connect();

        try {
            PreparedStatement pstm = null;
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            this.AdminName=rs.getString("AdminName");
            this.AdminEmail=rs.getString("AdminEmail");
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

    public Admin(String adminName, String adminEmail) {
        /**constructor to automatically add object to db on creation*/
        this.AdminEmail = adminEmail;
        this.AdminName = adminName;
        query = "Insert Into Admin (AdminName, AdminEmail) Values('" + this.AdminName + "', '" + this.AdminEmail + "')";
        try {
            this.AdminId = addToDb(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getAdminId() {
        return AdminId;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminEmail(String adminEmail) {
        AdminEmail = adminEmail;
        String sql = "UPDATE Admin SET AdminEmail = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, adminEmail);
            pstmt.setInt(2, this.AdminId);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
        String sql = "UPDATE Admin SET AdminName = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, adminName);
            pstmt.setInt(2, this.AdminId);
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
        if (resultSet != null && resultSet.next()) {
            id = resultSet.getInt(1);
        }
        if(connection!=null){
            connection.close();
        }
        return id;
    }


    public void removeUsersFromDB(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";

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

    public void removeSupplierFromDB(int id){
        String sql = "DELETE FROM Supplier WHERE id = ?";

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

    public void removeCategoryFromDB(int id){
        String sql = "DELETE FROM Category WHERE id = ?";

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
        PreparedStatement pst = connection.prepareStatement("SELECT * from Admin Where AdminID = "+this.AdminId);
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

    public void editUser(int id, String userName, String userRole) {
        String sql = "UPDATE Users SET UserName = ? , "
                + "UserRole = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, userName);
            pstmt.setString(2, userRole);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void editUserName(int id, String userName){
        String sql = "UPDATE Users SET UserName = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, userName);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editUserRole(int id, String userRole){
        String sql = "UPDATE Users SET "
                + "UserRole = ? "
                + "WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, userRole);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
