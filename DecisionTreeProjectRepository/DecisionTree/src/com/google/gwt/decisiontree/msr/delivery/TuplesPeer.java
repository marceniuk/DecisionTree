/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decision_tree.msr.children_with_asthma;
import decision_tree.model.*;
import java.sql.*;
import java.io.*;
import java.util.Hashtable;

/**
 *
 * @author 1
 */
public class TuplesPeer {
    
    public static Hashtable getTuples(DataManager dataManager) {

    Connection connection = dataManager.getConnection();
    Hashtable tuples = new Hashtable();
    CategorisedData tuple = new CategorisedData();

    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sql = "SELECT * FROM categorised_data";

    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     tuple = new CategorisedData(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
     tuples.put(Integer.parseInt(rs.getString(1)),tuple);
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table of tuples: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return tuples;
 }    
}
