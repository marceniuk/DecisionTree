<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page import="org.abego.treelayout.demo.*"%>
<%@page import="org.abego.treelayout.util.*"%>
<%@page import="org.abego.treelayout.*"%>
<%@page import="org.abego.treelayout.demo.svg.*"%>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

<%
    String problemName = request.getParameter("problemName");
    if (problemName == null) {
        problemName = "default";
    }
    pageContext.setAttribute("problemName", problemName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>

<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
} else {
%>
<p>Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to include your name with greetings you post.</p>
<%
    }
%>
<h3>Decision Tree for Problem '${fn:escapeXml(problemName)}' </h3>
<% 
org.abego.treelayout.TreeForTreeLayout tree = SampleTreeFactory.createSampleTree();
double gapBetweenLevels = 50D;
double gapBetweenNodes = 10D;
DefaultConfiguration configuration = new DefaultConfiguration<TextInBox>(gapBetweenLevels, gapBetweenNodes);
TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree, nodeExtentProvider, configuration);
SVGForTextInBoxTree generator = new SVGForTextInBoxTree(treeLayout);
System.out.println(generator.getSVG());
String svg = generator.getSVG();
%>
<%=svg%>
<p>
<form action="/inducedecisiontree.jsp" method="get">
    <div> Problem name: 
	<input type="text" name="problemName" value="${fn:escapeXml(problemName)}"/>
    <br/>
	<input type="submit" value="Switch Problem"/></div>
</form>
<p>
<a href="/welcomepage.jsp"> Home </a>

</body>
</html>