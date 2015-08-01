<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.gwt.decisiontree.model.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="org.abego.treelayout.demo.*"%>
<%@page import="org.abego.treelayout.util.*"%>
<%@page import="org.abego.treelayout.*"%>
<%@page import="org.abego.treelayout.demo.svg.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Decision Tree</title>
</head>
<body>
<h3>Construction of Decision Tree</h3>
<% 
//SuppressWarnings("rawtypes","unchecked")

org.abego.treelayout.TreeForTreeLayout tree = SampleTreeFactory.createSampleTree();
double gapBetweenLevels = 50D;
double gapBetweenNodes = 10D;
DefaultConfiguration configuration = new DefaultConfiguration<TextInBox>(gapBetweenLevels, gapBetweenNodes);
TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree, nodeExtentProvider, configuration);
SVGForTextInBoxTree generator = new SVGForTextInBoxTree(treeLayout);
System.out.println(generator.getSVG());

    /*
    ConnectToChildrenWithAsthmaDatabase();
            
    Hashtable htAttribute_list = dataManager_ChildrenWithAsthma.getAttributes();
   
    DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode();
    org.abego.treelayout.demo.TextInBox tbRoot = new org.abego.treelayout.demo.TextInBox("root",40,20);

    long time_begin = java.util.Calendar.getInstance().getTimeInMillis(); // timer on
    DecisionTree dtDecision_tree_Children_WithAsthma = new DecisionTree(tbRoot, dataManager_ChildrenWithAsthma, htAttribute_list, "Information gain", "abego tree");
    long time_end = java.util.Calendar.getInstance().getTimeInMillis(); // timer off
    System.out.println("time to induce decision tree Children With Asthma based on Information gain = " + String.valueOf(time_end-time_begin) + " miliseconds");
        
    org.abego.treelayout.TreeForTreeLayout<org.abego.treelayout.demo.TextInBox> tree = dtDecision_tree_Children_WithAsthma.m_abego_tree;
     
        double gapBetweenLevels = 50D;
        double gapBetweenNodes = 10D;
        org.abego.treelayout.util.DefaultConfiguration configuration = new org.abego.treelayout.util.DefaultConfiguration(gapBetweenLevels, gapBetweenNodes);
        org.abego.treelayout.demo.TextInBoxNodeExtentProvider nodeExtentProvider = new org.abego.treelayout.demo.TextInBoxNodeExtentProvider();
        org.abego.treelayout.TreeLayout treeLayout = new org.abego.treelayout.TreeLayout(tree, nodeExtentProvider, configuration);
        decision_tree.msr.children_with_asthma.TextInBoxTreePaneMultiColored panel = new decision_tree.msr.children_with_asthma.TextInBoxTreePaneMultiColored(treeLayout);
        */        
        /* decision tree in the window
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Addiction for Smoking");
        javax.swing.JScrollPane pane = new javax.swing.JScrollPane(panel, javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.setSize(1380, 640);
        frame.add(pane);
        frame.setResizable(true);
        frame.setVisible(true);
        */

 %>
</body>
</html>