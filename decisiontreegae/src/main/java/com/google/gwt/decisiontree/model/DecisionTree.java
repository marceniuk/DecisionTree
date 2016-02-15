/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gwt.decisiontree.model;

import java.util.Hashtable;
import java.util.Enumeration;
////////// for decision tree /////////////////
//import decision_tree.model.Attribute_for_list;
//////////////////////////////////////////////
/**
  * for abego.treelayout
 **/
import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.util.DefaultTreeForTreeLayout; 

/**
 *
 * @author Vasyl Martsenyuk
 */
public class DecisionTree {

private DataManager m_dataManager;
//Hashtable m_htD; 
Hashtable m_htAttribute_list;
String m_sAttribute_selection_measure;
public DefaultTreeForTreeLayout<TextInBoxWithUserObject> m_abego_tree;

public DecisionTree(TextInBoxWithUserObject tbRoot, DataManager dm, Hashtable htAttribute_list, String sAttribute_selection_measure, String tree_type) {
    if (tree_type.matches("abego tree")) {
    m_abego_tree = new DefaultTreeForTreeLayout(tbRoot);
    m_dataManager = dm;
    m_htAttribute_list = htAttribute_list;
    m_sAttribute_selection_measure = sAttribute_selection_measure;
    Generate_decision_tree(m_htAttribute_list, tbRoot, "", "abego tree");
    }
}
    
	
private void Generate_decision_tree(Hashtable htAttribute_list, TextInBoxWithUserObject tbSubroot, String splitting_criterion, String tree_type) {

    
if(tree_type.matches("abego tree")) {
    
    
    //////////////// 1 //////////////////////////////////////////////////////
    System.out.println("1");
    TextInBoxWithUserObject tbN; // for abego tree
    NodeObject userObject = new NodeObject("");// "" - !!! it was empty argument list
    userObject.htAttribute_list = htAttribute_list;
    userObject.splitting_criterion = splitting_criterion;
    //tbN.setUserObject(userObject);
    //insertNodeInto(tbN, tbSubroot, tbSubroot.getChildCount()); 
    
    ////////////////////////////////////////////////////////////////////////
    //////////////// 2, 3  /////////////////////////////////////////////////
    System.out.println("2,3");
    String tuplesInDareAllOfTheSameClassC = m_dataManager.tuplesInDareAllOfTheSameClassC(htAttribute_list);
    if (tuplesInDareAllOfTheSameClassC != null) {
        System.out.println("All in the same class " + tuplesInDareAllOfTheSameClassC); 
        userObject.sLabel = tuplesInDareAllOfTheSameClassC;
        // for abego tree
        String add_string = userObject.toString();
        String add_string2 = add_string;
        int ammount_of_raws = (add_string2.replace("\n", ";;;")).length() - add_string.length();
        tbN = new TextInBoxWithUserObject(userObject, 200, 15*ammount_of_raws);
        m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
        //dmtnN.setAllowsChildren(false);
        //return dmtnN;
    }
    //////////////////////////////////////////////////////////////////////////
    ///////////////// 4, 5  //////////////////////////////////////////////////
    System.out.println("4,5");
    if(Is_empty_attribute_list(htAttribute_list)) {
        userObject.sLabel = m_dataManager.Majority_class_in_D(htAttribute_list);
        // for abego tree
        tbN = new TextInBoxWithUserObject(userObject, 200, 20);
        m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
        System.out.println("Attribute list is empty. Majority class in D is " + ((NodeObject)tbN.getUserObject()).sLabel);
        //dmtnN.setAllowsChildren(false);
        //return dmtnN;
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
        userObject.sLabel = m_dataManager.Classes_ratio_in_D(htAttribute_list);
        //((NodeObject)dmtnN.getUserObject()).sLabel = "it's impossible to find splitting_attribute";
        // for abego tree
        String add_string = userObject.toString();
        String add_string1 = add_string;
        String add_string2 = add_string;
        int ammount_of_raws = (add_string2.replace("\n", ";;")).length() - add_string.length();
        int ammount_of_classes = (add_string1.replace(";", ";;")).length() - add_string.length();
        add_string = add_string.replace(';', '\n');
        tbN = new TextInBoxWithUserObject(add_string, 160, 20*ammount_of_classes + 12*ammount_of_raws);
        m_abego_tree.addChild(tbSubroot, tbN); // for abego tree
        //dmtnN.setAllowsChildren(false);
        //return dmtnN;
    }
    /////////////////  7 /////////////////////////////////////////////////////
    System.out.println("7");
    userObject.sLabel = splitting_attribute.getAttributeName();
    // for abego tree
    String additional_str_variable = userObject.toString();
    additional_str_variable = additional_str_variable.replace("\\n", "\n");
    int number_of_lines = additional_str_variable.split("\n").length;
    tbN = new TextInBoxWithUserObject(userObject,180,15 * number_of_lines);
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
    for (Enumeration e1 = ((Attribute_for_list)htAttribute_list.get(splitting_attribute.getId())).htSplitting_outcomes.keys(); e1.hasMoreElements();) { 
    int j = (Integer)e1.nextElement();
    i++;
    ////////////////// 11 ///////////////////////////////////////////////////
    System.out.println("11");
    Hashtable htAttribute_list_for_outcome_j = new Hashtable();
    htAttribute_list_for_outcome_j = Partition_the_tuples_in_D_satisfying_outcome_j(htAttribute_list_for_children, splitting_attribute, j);
    ////////////////// 12 ///////////////////////////////////////////////////
    System.out.println("12");
    if (m_dataManager.Dj_is_empty(htAttribute_list_for_outcome_j)) {
        //DefaultMutableTreeNode dmtnLeaf = new DefaultMutableTreeNode();
        NodeObject child_userObject = new NodeObject("");
        child_userObject.htAttribute_list = htAttribute_list_for_outcome_j;
        //dmtnLeaf.setUserObject(child_userObject); // it was for dmtnLeaf
        //dmtnLeaf.setAllowsChildren(false);
        //insertNodeInto(dmtnLeaf, dmtnN, i);
        child_userObject.splitting_criterion = (String)((Attribute_for_list)htAttribute_list_for_outcome_j.get(splitting_attribute.getId())).htSplitting_outcomes.get(j);
        child_userObject.sLabel = m_dataManager.Majority_class_in_D(htAttribute_list_for_outcome_j);
        // for abego tree
        TextInBoxWithUserObject tbLeaf = new TextInBoxWithUserObject(child_userObject, 160, 20);
        m_abego_tree.addChild(tbN, tbLeaf); // for abego tree
    }
    else {
        Generate_decision_tree(htAttribute_list_for_outcome_j, tbN, (String)((Attribute_for_list)htAttribute_list_for_outcome_j.get(splitting_attribute.getId())).htSplitting_outcomes.get(j),tree_type);
    }
    }
    System.out.println("Attribute_name = " + splitting_attribute.getAttributeName());
    //return dmtnN;
}

}     

class NodeObject {
    Attribute attribute;
    Hashtable htAttribute_list;
    String splitting_criterion;
    String sLabel;
	
	public NodeObject(String label) {
		sLabel = label;
	}
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

class TextInBoxWithUserObject extends TextInBox {
	NodeObject m_userObject;
	
	TextInBoxWithUserObject (NodeObject nodeObject, int width, int height) {
		super(nodeObject.toString(), width, height);
		m_userObject = nodeObject;
	}
	
	//constructor unchecked
	TextInBoxWithUserObject (String label, int width, int height) {
		super(label, width, height);
		m_userObject = new NodeObject(label);
	}
	/////////////////////////
	
	void setUserObject(NodeObject nodeObject) {
		m_userObject = nodeObject;
	}
	
	NodeObject getUserObject() {
		return m_userObject;
	}
}


private boolean Is_empty_attribute_list(Hashtable htAttribute_list) {
    for (Enumeration e1 = htAttribute_list.keys(); e1.hasMoreElements();) { 
        int l = (Integer)e1.nextElement();
        if(((Attribute_for_list)htAttribute_list.get(l)).included) {
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
    System.out.println("splitting_attribute = " + ((Attribute_for_list)htAttribute_list.get(iSplitting_attribute)).attribute.getAttributeName());
    return ((Attribute_for_list)htAttribute_list.get(iSplitting_attribute)).attribute;
    }
    else {
        System.out.println("splitting_attribute doesn't exsist");
        return null;
    }
}   

private Hashtable Remove_splitting_attribute (TextInBoxWithUserObject node, Attribute splitting_attribute) {
    Hashtable htNewAttributeList = new Hashtable();
    htNewAttributeList = ((NodeObject)node.getUserObject()).htAttribute_list;
    ((Attribute_for_list)htNewAttributeList.get(splitting_attribute.getId())).included = false;
    return htNewAttributeList;
}

private Hashtable Partition_the_tuples_in_D_satisfying_outcome_j(Hashtable htAttribute_list_for_children, Attribute splitting_attribute, int j) {
    Hashtable htNewAttributeList = new Hashtable();
    htNewAttributeList = htAttribute_list_for_children;
    ((Attribute_for_list)htNewAttributeList.get(splitting_attribute.getId())).splitting_criterion = (String)((Attribute_for_list)htNewAttributeList.get(splitting_attribute.getId())).htSplitting_outcomes.get(j);
    return htNewAttributeList;
}

}

