/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decision_tree.msr.children_with_asthma;
import java.util.Properties;
import java.sql.*;
import java.util.Hashtable;

/**
 *
 * @author 1
 */
public class DataManager extends decision_tree.model.DataManager {
    
private String m_dbUrl, m_dbUserName, m_dbPassword, m_jdbcDriver;

public void setDbUrl(String dbUrl) {m_dbUrl = dbUrl;}
public void setDbUserName(String dbUserName) {m_dbUserName = dbUserName;}
public void setDbPassword(String dbPassword) {m_dbPassword = dbPassword;}
public void setJdbcDriver(String jdbcDriver) {m_jdbcDriver = jdbcDriver;}



public Connection getConnection() {
    Properties properties = new Properties();
    properties.setProperty("user",m_dbUserName);
    properties.setProperty("password",m_dbPassword);
    loadDriver();
    Connection conn = null;
  try {
    conn = DriverManager.getConnection (m_dbUrl,properties);
    }
  catch (SQLException e) {
    System.err.print("Cann't connect to database " + e.toString() + m_dbUrl);
    }
    return conn;
}

private void loadDriver() {
    try { // load the database JDBC driver
	Class.forName(m_jdbcDriver);
	}
    catch (ClassNotFoundException e) {
	System.err.print(e.toString());
	}
}

public void putConnection(Connection connection) {
    try {
    connection.close();
    }
    catch (SQLException sqle) { 
         System.err.print("Error SQL : " + sqle); 
      }
}

public Hashtable getTuples() {
   return TuplesPeer.getTuples(this);
}

public Hashtable getAttributes() {
   return decision_tree.model.AttributeListPeer.getAttributes(this);
}

public double getInfo$D$(Hashtable htAttribute_list) {
    return decision_tree.model.AttributeListPeer.getInfo$D$(this, htAttribute_list);
}

public double getInfo_A$D$(int A, Hashtable htAttribute_list) {
    return decision_tree.model.AttributeListPeer.getInfo_A$D$(this, A, htAttribute_list);
}

public double getSplitInfo_A$D$(int A, Hashtable htAttribute_list) {
    return decision_tree.model.AttributeListPeer.getSplitInfo_A$D$(this, A, htAttribute_list);
}


public String tuplesInDareAllOfTheSameClassC(Hashtable htAttribute_list) {
    return decision_tree.model.AttributeListPeer.tuplesInDareAllOfTheSameClassC(this, htAttribute_list);
}

public String Majority_class_in_D(Hashtable htAttribute_list) {
    return decision_tree.model.AttributeListPeer.Majority_class_in_D(this, htAttribute_list);
}

public String Classes_ratio_in_D(Hashtable htAttribute_list) {
    return decision_tree.model.AttributeListPeer.Classes_ratio_in_D(this, htAttribute_list);
}

public boolean Dj_is_empty(Hashtable htAttribute_list_for_outcome_j) {
    return decision_tree.model.AttributeListPeer.Dj_is_empty(this, htAttribute_list_for_outcome_j);
}
        
}
