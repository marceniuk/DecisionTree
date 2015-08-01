/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gwt.decisiontree.model;

import java.util.Hashtable;
import java.util.Enumeration;
////////// for decision tree /////////////////
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import com.google.gwt.decisiontree.model.Attribute_for_list;
//////////////////////////////////////////////

/**
 *
 * @author 1
 */
public class DecisionTree  {

private DataManager m_dataManager;
//Hashtable m_htD; 
Hashtable m_htAttribute_list;
String m_sAttribute_selection_measure;
public org.abego.treelayout.util.DefaultTreeForTreeLayout<TextInBoxWithUserObject> m_abego_tree;


public DecisionTree(TextInBoxWithUserObject tbRoot, DataManager dm, Hashtable htAttribute_list, String sAttribute_selection_measure, String tree_type) {
    //super(tbRoot);
    if (tree_type.matches("abego tree")) {
    m_abego_tree = new org.abego.treelayout.util.DefaultTreeForTreeLayout(tbRoot);
    m_dataManager = dm;
    m_htAttribute_list = htAttribute_list;
    m_sAttribute_selection_measure = sAttribute_selection_measure;
    Generate_decision_tree(m_htAttribute_list, tbRoot, "", "abego tree");
    }
}

private TextInBoxWithUserObject Generate_decision_tree(Hashtable htAttribute_list, TextInBoxWithUserObject tbSubroot, String splitting_criterion, String tree_type) {

    
if(tree_type.matches("abego tree")) {
    
    
    //////////////// 1 //////////////////////////////////////////////////////
    System.out.println("1");
    TextInBoxWithUserObject tbN; // for abego tree
    NodeObject userObject = new NodeObject();
    userObject.htAttribute_list = htAttribute_list;
    userObject.splitting_criterion = splitting_criterion;
        
    ////////////////////////////////////////////////////////////////////////
    //////////////// 2, 3  /////////////////////////////////////////////////
    System.out.println("2,3");
    String tuplesInDareAllOfTheSameClassC = m_dataManager.tuplesInDareAllOfTheSameClassC(htAttribute_list);
    if (tuplesInDareAllOfTheSameClassC != null) {
        // for abego tree
        String add_string = tuplesInDareAllOfTheSameClassC;
        String add_string2 = add_string;
        int ammount_of_raws = (add_string2.replace("\n", ";;;")).length() - add_string.length();
        tbN = new TextInBoxWithUserObject(tuplesInDareAllOfTheSameClassC, 200, 15*ammount_of_raws);
        m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
        //tbN.hasNode(false);
        return tbN;
    }
    //////////////////////////////////////////////////////////////////////////
    ///////////////// 4, 5  //////////////////////////////////////////////////
    System.out.println("4,5");
    if(Is_empty_attribute_list(htAttribute_list)) {
    	String sMajorityClassInD = m_dataManager.Majority_class_in_D(htAttribute_list);
        // for abego tree
        tbN = new TextInBoxWithUserObject(sMajorityClassInD, 200, 20);
        m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
        //tbN.setAllowsChildren(false);
        return tbN;
    }
    /////////////////////////////////////////////////////////////////////////
    ////////////////// 6 ////////////////////////////////////////////////////
    System.out.println("6");
    Attribute splitting_attribute = Attribute_selection_method(htAttribute_list);
    if (splitting_attribute == null) {
        // it's impossible to find splitting_attribute
        // one way is the majority class in D, i.e., 
        //((NodeObject)dmtnN.getUserObject()).sLabel = m_dataManager.Majority_class_in_D(htAttribute_list);
        // another way is the percentages of all classes in D
        String sClassesRatioInD = m_dataManager.Classes_ratio_in_D(htAttribute_list);
        //((NodeObject)dmtnN.getUserObject()).sLabel = "it's impossible to find splitting_attribute";
        // for abego tree
        String add_string = sClassesRatioInD;
        String add_string1 = add_string;
        String add_string2 = add_string;
        int ammount_of_raws = (add_string2.replace("\n", ";;")).length() - add_string.length();
        int ammount_of_classes = (add_string1.replace(";", ";;")).length() - add_string.length();
        add_string = add_string.replace(';', '\n');
        tbN = new TextInBoxWithUserObject(add_string, 160, 20*ammount_of_classes + 12*ammount_of_raws);
        m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
        //tbN.setAllowsChildren(false);
        return tbN;
    }
    /////////////////  7 /////////////////////////////////////////////////////
    System.out.println("7");
    // for abego tree
    String additional_str_variable = splitting_attribute.getAttributeName();
    additional_str_variable = additional_str_variable.replace("\\n", "\n");
    int number_of_lines = additional_str_variable.split("\n").length;
    tbN = new TextInBoxWithUserObject(splitting_attribute.getAttributeName(),180,15 * number_of_lines);
    m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
    /////////////////   8  //////////////////////////////////////////////////
    //   if splitting_attribute is discrete-valued and 
    //   multiway splits allowed then
    ////////////////// 9 ////////////////////////////////////////////////////
    System.out.println("9");
    Hashtable htAttribute_list_for_children = new Hashtable();
    htAttribute_list_for_children = Remove_splitting_attribute(tbN, splitting_attribute);
    ////////////////// 10 ///////////////////////////////////////////////////
    System.out.println("10");
    int i = -1;
    for (Enumeration e1 = ((com.google.gwt.decisiontree.model.Attribute_for_list)htAttribute_list.get(splitting_attribute.getId())).htSplitting_outcomes.keys(); e1.hasMoreElements();) { 
    int j = (Integer)e1.nextElement();
    i++;
    ////////////////// 11 ///////////////////////////////////////////////////
    System.out.println("11");
    Hashtable htAttribute_list_for_outcome_j = new Hashtable();
    htAttribute_list_for_outcome_j = Partition_the_tuples_in_D_satisfying_outcome_j(htAttribute_list_for_children, splitting_attribute, j);
    ////////////////// 12 ///////////////////////////////////////////////////
    System.out.println("12");
    if (m_dataManager.Dj_is_empty(htAttribute_list_for_outcome_j)) {
        // for abego tree
    	TextInBoxWithUserObject tbLeaf = new TextInBoxWithUserObject(m_dataManager.Majority_class_in_D(htAttribute_list_for_outcome_j), 160, 20);
        m_abego_tree.addChild(tbN, tbLeaf); // for abego tree
    }
    else {
    	TextInBoxWithUserObject tbSubN = Generate_decision_tree(htAttribute_list_for_outcome_j, tbN, (String)((com.google.gwt.decisiontree.model.Attribute_for_list)htAttribute_list_for_outcome_j.get(splitting_attribute.getId())).htSplitting_outcomes.get(j),tree_type);
    	//m_abego_tree.addChild(tbSubN, tbN, i);
    	//m_abego_tree.addChild(tbSubN, tbN);
    }
    }
    System.out.println("Attribute_name = " + splitting_attribute.getAttributeName());
    return tbN;
}
else return null;
}     

class NodeObject {
    Attribute attribute;
    Hashtable htAttribute_list;
    String splitting_criterion;
    String sLabel;
    public String toString() {
        if (sLabel!=null) {
        if (splitting_criterion.matches("")) {
            return sLabel;
        }
        else 
        return "if '" + splitting_criterion + "' then '" + sLabel + "'";
        }
        else return "if '" + splitting_criterion + "' then its impossible to determine class ";
    }
}


private boolean Is_empty_attribute_list(Hashtable htAttribute_list) {
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((com.google.gwt.decisiontree.model.Attribute_for_list)htAttribute_list.get(l)).included) {
            return false;
        }
        }
    return true;
}

private Attribute Attribute_selection_method(Hashtable htAttribute_list) {
    // calculation of expected information needed to clasify a tuple in D ///
    double Info$D$; //, Info_A$D$;
    Info$D$ = m_dataManager.getInfo$D$(htAttribute_list);
    System.out.println("Info$D$ = " + Info$D$);
    double maxGain = 0.;
    
    int iSplitting_attribute = 0;
    
    if(m_sAttribute_selection_measure.matches("Information gain")) {
      
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
        double Info_A$D$ = m_dataManager.getInfo_A$D$(l, htAttribute_list);
        System.out.println("Info_A" + l + "$D$ = " + Info_A$D$);
        double Gain_A = Info$D$ - Info_A$D$;
        System.out.println("Gain_A" + l + " = " + Gain_A);
        if (Gain_A > maxGain) {
            maxGain = Gain_A;
            iSplitting_attribute = l;
        }
        }
    }
    
    }
    else if(m_sAttribute_selection_measure.matches("Gain ratio")) {
    
    double maxGainRatio = 0.;
    Info$D$ = m_dataManager.getInfo$D$(htAttribute_list);
    System.out.println("Info$D$ = " + Info$D$);
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
        double Info_A$D$ = m_dataManager.getInfo_A$D$(l, htAttribute_list);
        System.out.println("Info_A" + l + "$D$ = " + Info_A$D$);
        double SplitInfo_A$D$ = m_dataManager.getSplitInfo_A$D$(l, htAttribute_list);
        double Gain_A = Info$D$ - Info_A$D$;
        
        double GainRatio$A$ = Gain_A / SplitInfo_A$D$;
        System.out.println("GainRatio_A" + l + " = " + GainRatio$A$);
        if (GainRatio$A$ > maxGainRatio) {
            maxGainRatio = GainRatio$A$;
            iSplitting_attribute = l;
        }
        }
    }
    
    }
    if(iSplitting_attribute != 0) {
    System.out.println("splitting_attribute = " + ((com.google.gwt.decisiontree.model.Attribute_for_list)htAttribute_list.get(iSplitting_attribute)).attribute.getAttributeName());
    return ((com.google.gwt.decisiontree.model.Attribute_for_list)htAttribute_list.get(iSplitting_attribute)).attribute;
    }
    else {
        System.out.println("splitting_attribute doesn't exsist");
        return null;
    }
}   

private Hashtable Remove_splitting_attribute (TextInBoxWithUserObject node, Attribute splitting_attribute) {
    Hashtable htNewAttributeList = new Hashtable();
    htNewAttributeList = ((NodeObject)node.getUserObject()).htAttribute_list;
    ((com.google.gwt.decisiontree.model.Attribute_for_list)htNewAttributeList.get(splitting_attribute.getId())).included = false;
    return htNewAttributeList;
}

private Hashtable Partition_the_tuples_in_D_satisfying_outcome_j(Hashtable htAttribute_list_for_children, Attribute splitting_attribute, int j) {
    Hashtable htNewAttributeList = new Hashtable();
    htNewAttributeList = htAttribute_list_for_children;
    ((com.google.gwt.decisiontree.model.Attribute_for_list)htNewAttributeList.get(splitting_attribute.getId())).splitting_criterion = (String)((com.google.gwt.decisiontree.model.Attribute_for_list)htNewAttributeList.get(splitting_attribute.getId())).htSplitting_outcomes.get(j);
    return htNewAttributeList;
}

class TextInBoxWithUserObject extends org.abego.treelayout.demo.TextInBox {
	NodeObject userObject;
	
	public TextInBoxWithUserObject (String text, int width, int height) {
		super(text, width, height);
	}
	
	NodeObject getUserObject() {
		return userObject;
	}
}

}

