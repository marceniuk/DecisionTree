/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gwt.decisiontree.model;
import java.util.Properties;
import java.util.Hashtable;

/**
 *
 * @author Vasyl Martsenyuk
 */
public class DataManager {
    
private String m_dbUrl, m_dbUserName, m_dbPassword, m_jdbcDriver;

public void setDbUrl(String dbUrl) {m_dbUrl = dbUrl;}
public void setDbUserName(String dbUserName) {m_dbUserName = dbUserName;}
public void setDbPassword(String dbPassword) {m_dbPassword = dbPassword;}
public void setJdbcDriver(String jdbcDriver) {m_jdbcDriver = jdbcDriver;}

public Hashtable getTuples() {
   //return TuplesPeer.getTuples(this);
	return null;
}

public Hashtable getAttributes() {
   //return AttributeListPeer.getAttributes(this);
   return null;
}

public double getInfo$D$(Hashtable htAttribute_list) {
    return AttributeListPeer.getInfo$D$(this, htAttribute_list);
}

public double getInfo_A$D$(int A, Hashtable htAttribute_list) {
    return AttributeListPeer.getInfo_A$D$(this, A, htAttribute_list);
}

public double getSplitInfo_A$D$(int A, Hashtable htAttribute_list) {
    return AttributeListPeer.getSplitInfo_A$D$(this, A, htAttribute_list);
}


public String tuplesInDareAllOfTheSameClassC(Hashtable htAttribute_list) {
    return AttributeListPeer.tuplesInDareAllOfTheSameClassC(this, htAttribute_list);
}

public String Majority_class_in_D(Hashtable htAttribute_list) {
    return AttributeListPeer.Majority_class_in_D(this, htAttribute_list);
}

public String Classes_ratio_in_D(Hashtable htAttribute_list) {
    return AttributeListPeer.Classes_ratio_in_D(this, htAttribute_list);
}

public boolean Dj_is_empty(Hashtable htAttribute_list_for_outcome_j) {
    return AttributeListPeer.Dj_is_empty(this, htAttribute_list_for_outcome_j);
}
        
}
