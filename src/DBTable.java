/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */
import java.sql.SQLException;
/**
 * The interface which makes it easier to understand
 * which java class is connected with the DataBase Table
 * in case it is needed to work with other sources of data
 * such as files
 * */
public interface DBTable {
    /**
     * The Basic Methods of interacting with the DBTable
     * */
    int addToDb(String query) throws SQLException;
    void printData() throws SQLException;
}
