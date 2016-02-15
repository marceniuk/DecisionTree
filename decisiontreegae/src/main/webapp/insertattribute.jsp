<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.gwt.decisiontree.model.Problem" %>
<%@ page import="com.google.gwt.decisiontree.model.Attribute" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

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
    </p>
<%
    }
%>

<%
    //if(ObjectifyService.ofy().load().type(Problem.class).filter("problemname", problemName).first().now() == null) 
	
        // Create Problem entity
	       ObjectifyService.ofy().save().entity(new Problem(problemName)).now();
	    System.out.println("PROBLEM SAVED!!!");
		
        // Create the correct Ancestor key
           Key<Problem> theProblem = Key.create(Problem.class, problemName);
    
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Problem Attributes belonging to the selected Problem.
      List<Attribute> attributes = ObjectifyService.ofy()
          .load()
          .type(Attribute.class) // We want only Attributes
          .ancestor(theProblem)    // Anyone in this problem
          .order("-attributeFieldName") // ordering is according to attribute names: -attributeName is indexed.
          .limit(15)             // Only show 15 of them.
          .list();

    if (attributes.isEmpty()) {
%>
<p>Problem '${fn:escapeXml(problemName)}' has no attributes.</p>
<%
    } else {
%>
<p>Attributes in Problem '${fn:escapeXml(problemName)}':</p>
<%
      // Look at all of our attributes
        for (Attribute attribute : attributes) {
            pageContext.setAttribute("attribute_name", attribute.attributeName);
			pageContext.setAttribute("attribute_field_name", attribute.attributeFieldName);
%>
<p><b>${fn:escapeXml(attribute_field_name)}</b> means:</p>
<blockquote>${fn:escapeXml(attribute_name)}</blockquote>
<%
        }
    }
%>

<form action="/insertproblemattribute" method="post">
    <div>Attribute name<textarea name="attribute_field_name" rows="1" cols="20"></textarea>
	<br/>
	Attribute content<textarea name="attribute_name" rows="3" cols="60"></textarea>
	<br/>
    <input type="submit" value="Insert New Attribute for Problem"/></div>
    <input type="hidden" name="problemName" value="${fn:escapeXml(problemName)}"/>
</form>

<form action="/insertattribute.jsp" method="get">
    <div> Problem name: 
	<input type="text" name="problemName" value="${fn:escapeXml(problemName)}"/>
    <br/>
	<input type="submit" value="Switch Problem"/></div>
</form>

<a href="/welcomepage.jsp"> Home </a> <br/>
<a href="/insertattributevalues.jsp"> Insert attribute values </a>
<p>


</body>
</html>