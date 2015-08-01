/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gwt.decisiontree.model;
import java.sql.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 *
 * @author 1
 */
public class AttributeListPeer {
 public static Hashtable getAttributes(DataManager dataManager) {

    Connection connection = dataManager.getConnection();
    Hashtable attribute_list = new Hashtable();
    Attribute_for_list attribute_for_list = new Attribute_for_list();
    Attribute  attribute = new Attribute();

    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sql = "SELECT * FROM attribute";

    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     attribute = new Attribute(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3));
     attribute_for_list = new Attribute_for_list();
     attribute_for_list.attribute = attribute;
     attribute_for_list.htSplitting_outcomes = Get_splitting_outcomes(dataManager, rs.getString(3));
     attribute_for_list.included = true;
     attribute_list.put(Integer.parseInt(rs.getString(1)),attribute_for_list);
 }
 }
 finally { s.close(); } 
 }
 catch (SQLException e) {
 System.out.println("Could not get table of attributes: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return attribute_list;
 }    
 
 public static Hashtable Get_splitting_outcomes (DataManager dataManager, String sAttributeFieldName) {
    Connection connection = dataManager.getConnection();
    Hashtable outcome_list = new Hashtable();
    
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sql = "SELECT DISTINCT(" + sAttributeFieldName + ") FROM categorised_data";

    try {
    ResultSet rs = s.executeQuery(sql);
    int i = 1;
    while (rs.next()) {
     outcome_list.put(i, rs.getString(1));
     i++;
 }
 }
 finally { s.close(); } 
 }
 catch (SQLException e) {
 System.out.println("Could not get table of tuples for outcomes: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return outcome_list;    
 }
 
 public static double getInfo$D$(DataManager dataManager, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    double Info$D$ = 0.;;
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "='" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    /* 
    /// for all attributes
    String sql = "select -SUM((Alias1.Ci/Alias2.D)*(LOG(Alias1.Ci/Alias2.D)/LOG(2))) from " + 
                 "(select SUM(1) as Ci from categorised_data group by categorised_data.class)Alias1, " + 
                 "(select COUNT(id) as D from categorised_data)Alias2";
     */
    
    String sql = "select -SUM((Alias1.Ci/Alias2.D)*(LOG(Alias1.Ci/Alias2.D)/LOG(2))) from " + 
                 "(select SUM(1) as Ci from (select " + sAttribute_list + ",class from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) +  ")Alias3 group by Alias3.class)Alias1, " + 
                 "(select SUM(1) as D from (select " + sAttribute_list + " from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias4)Alias2";
    
    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     Info$D$ = Double.valueOf(rs.getString(1));
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to calculate Info$D$: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return Info$D$;     
     
 }

public static double getInfo_A$D$(DataManager dataManager, int A, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    double Info_A$D$ = 0.;;
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "='" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    
    String sql = "select SUM((Alias1.Dj/Alias2.D)*Alias3.Info$Dj$) from " +   
                 "(select SUM(1) as Dj from (select * from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias6 group by Alias6." + ((Attribute_for_list)htAttribute_list.get(A)).attribute.getAttributeFieldName() + ")Alias1, " +  
                 "(select SUM(1) as D from (select " + sAttribute_list + " from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias7)Alias2, " + 
                 "(select -SUM((Alias4.Ci/Alias5.D)*(LOG(Alias4.Ci/Alias5.D)/LOG(2))) as Info$Dj$ from  " + 
                 "(select SUM(1) as Ci from (select " + sAttribute_list + ",class from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias8 group by Alias8.class)Alias4, " + 
                 "(select SUM(1) as D from (select " + sAttribute_list + " from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias9)Alias5)Alias3";
    //System.out.println(sql);
    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     Info_A$D$ = Double.valueOf(rs.getString(1));
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to calculate Info_A$D$: "  + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return Info_A$D$;     
     
 } 

public static double getSplitInfo_A$D$(DataManager dataManager, int A, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    double Info_A$D$ = 0.;;
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "='" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    
    String sql = "select -SUM((Alias1.Dj/Alias2.D)*(LOG(Alias1.Dj/Alias2.D)/LOG(2))) from " +   
                 "(select SUM(1) as Dj from (select * from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias6 group by Alias6." + ((Attribute_for_list)htAttribute_list.get(A)).attribute.getAttributeFieldName() + ")Alias1, " +  
                 "(select SUM(1) as D from (select " + sAttribute_list + " from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + ")Alias7)Alias2";
    //System.out.println(sql);
    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     Info_A$D$ = Double.valueOf(rs.getString(1));
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to calculate Info_A$D$: "  + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return Info_A$D$;     
     
 } 


public static String tuplesInDareAllOfTheSameClassC(DataManager dataManager, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    int ammount_of_classes = 0;
    String classC = null;
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "='" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    String sql = "select SUM(1),class from " + 
                 "(select " + sAttribute_list + ", class from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + " group by class)Alias1";
    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     ammount_of_classes = Integer.parseInt(rs.getString(1));
     classC = rs.getString(2);
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to calculate tuplesInDareAllOfTheSameClassC: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 if (ammount_of_classes == 1) return classC;
 else return null;  
         
}

public static String Majority_class_in_D (DataManager dataManager, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    String sMajorityClass = null;
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            // Pay attention! As a rule Attribute_list in this situation is empty
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "=" + "'" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    if(!sAttribute_list.matches("")) {
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    }
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    
    String sql = "select class from (select class,MAX(f) from (select class, COUNT(*)f from (select * from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + 
                 ")Alias3 group by class)Alias1)Alias2";
    /*String sql = "select class from (select class,MAX(f) from (select class, COUNT(*)f from (select " + 
                 sAttribute_list + " from categorised_data " + (sConditions.matches("")?"": " where " + sConditions) + 
                 ")Alias3 group by class)Alias1)Alias2";*/
    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     sMajorityClass = rs.getString(1);
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to calculate majority class: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return sMajorityClass;      
}

public static String Classes_ratio_in_D (DataManager dataManager, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    String sClassesRatio = "";
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            // Pay attention! As a rule Attribute_list in this situation is empty
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "=" + "'" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    if(!sAttribute_list.matches("")) {
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    }
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    
    String sql = "select Alias1.class_name, (Alias1.count_class / Alias2.count_tuples)*100 from " + 
                 "(select DISTINCT(class) as class_name,SUM(1) as count_class from (select * from categorised_data " + 
                 (sConditions.matches("")?"": " where " + sConditions) + 
                 ")Alias3 group by class) Alias1, " + 
                 "(select SUM(1) as count_tuples from (select * from categorised_data " + 
                 (sConditions.matches("")?"": " where " + sConditions) + 
                 ")Alias4) Alias2";
    System.out.println(sql);
    try {
    ResultSet rs = s.executeQuery(sql);
    while (rs.next()) {
     sClassesRatio = sClassesRatio + " " + Math.round(Double.valueOf(rs.getString(2))*100.)/100. + "% - " + rs.getString(1) + "; ";
 }
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to calculate classes ratio : " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return sClassesRatio;      
}


public static boolean Dj_is_empty(DataManager dataManager, Hashtable htAttribute_list) {
    Connection connection = dataManager.getConnection();
    boolean bFlag = false;
    if (connection != null) {

    try {

    Statement s = connection.createStatement();
    String sAttribute_list = "";
    String sConditions = "";
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
            sAttribute_list = sAttribute_list + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + ",";
        }
        else {
            sConditions = sConditions + ((Attribute_for_list)htAttribute_list.get(l)).attribute.getAttributeFieldName() + "= '" + ((Attribute_for_list)htAttribute_list.get(l)).splitting_criterion + "' AND ";
        }
    }
    if(!sAttribute_list.matches("")) {
    sAttribute_list = sAttribute_list.substring(0, sAttribute_list.length()-1); // cut last comma
    }
    else return true;
    if(!sConditions.matches("")) {
        sConditions = sConditions.substring(0, sConditions.length()-4);} // cut last AND
    
    String sql = "select " + sAttribute_list + " from categorised_data " + (sConditions.matches("")?"": " where " + sConditions);
    try {
    ResultSet rs = s.executeQuery(sql);
    if (!rs.next()) bFlag = true;
 }
 finally { s.close(); }
 }
 catch (SQLException e) {
 System.out.println("Could not get table to check is it empty: " + e.getMessage());
 }
 finally {
 dataManager.putConnection(connection);
 }
 }  
 return bFlag;      
}

}
