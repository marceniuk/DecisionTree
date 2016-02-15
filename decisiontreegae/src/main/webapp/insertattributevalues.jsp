<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.gwt.decisiontree.model.Problem" %>
<%@ page import="com.google.gwt.decisiontree.model.Attribute" %>
<%@ page import="com.google.gwt.decisiontree.model.Tuple" %>
<%@ page import="com.google.gwt.decisiontree.model.CategorizedData" %>
<%@ page import="com.google.gwt.decisiontree.model.ClassAttributeValue" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.Ref" %>
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
	
	String tupleId = request.getParameter("tupleId"); 
	
    if (tupleId != null) {
		System.out.println("tupleId at the begining = " + tupleId);
		
    pageContext.setAttribute("tupleId", tupleId);
	
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
	<p>Problem '${fn:escapeXml(problemName)}' </p>
<%
    }
%>

<%
	Long id = ObjectifyService.ofy().load().type(Tuple.class).filter("tuple_id",tupleId).first().now().id;

    // Create the correct Ancestor key
      Key<Tuple> theTuple = Key.create(Tuple.class, id);
	  System.out.println("ID of tuple = " + id);
	  

    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Tuple CategorizedDatas belonging to the selected Tuple.
      List<CategorizedData> tuple = ObjectifyService.ofy()
          .load()
          .type(CategorizedData.class) // We want only CategorizedData
          .ancestor(theTuple)    // Anyone in this tuple
          .limit(15)             // Only show 15 of them.
          .list();

	  ClassAttributeValue tupleclass = ObjectifyService.ofy()
	      .load()
		  .type(ClassAttributeValue.class)
		  .ancestor(theTuple)
		  .order("-date")
		  .first()
		  .now();
		  
    if (tuple.isEmpty()) {
%>
<p>Tuple <b>'${fn:escapeXml(tupleId)}'</b> has no attribute values.</p>
<%
    } else {
%>
<p>Attribute values in Tuple <b>'${fn:escapeXml(tupleId)}'</b>:</p>
<%
      // Look at all of tuple attribute values
        for (CategorizedData categorizeddata : tuple) {
			Attribute attribute = categorizeddata.theAttribute.get();
            pageContext.setAttribute("attribute_field_name", attribute.attributeFieldName);
			pageContext.setAttribute("attribute_value", categorizeddata.attributeValue);
%>
<p><b>${fn:escapeXml(attribute_field_name)}</b> equals:</p>
<blockquote>${fn:escapeXml(attribute_value)}</blockquote>
<%
        }
    }

	if(tupleclass == null) {
%>
	<p>Tuple <b>'${fn:escapeXml(tupleId)}'</b> has no class attribute value</p>
<%
    } else { String cav = tupleclass.classAttributeValue;
	pageContext.setAttribute("cav", cav);
	System.out.println("CAV.id = " + tupleclass.id);
	System.out.println("CLASS ATTRIBUTE VALUE = " + cav);
%>
<p>Class attribute value in Tuple <b>'${fn:escapeXml(tupleId)}'</b>:</p>
<p> <b> '${fn:escapeXml(cav)}' </b> </p>
<% } %>

<form action="/insertcategorizeddata" method="post">
    <div>Attribute name  <textarea name="attribute_field_name" rows="1" cols="20"></textarea>
	<br/>
	Attribute value  <textarea name="attribute_value" rows="1" cols="60"></textarea>
	<br/>
    <input type="submit" value="Insert Value for Attribute"/></div>
    <input type="hidden" name="tupleId" value="${fn:escapeXml(tupleId)}"/>
	<input type="hidden" name="problemName" value="${fn:escapeXml(problemName)}"/>
</form>
<br/>
<form action="/insertclassattributevalue" method="post">
    <div>Class attribute Value
	<br/> 
	<textarea name="class_attribute_value" rows="1" cols="60"></textarea>
    <input type="hidden" name="tupleId" value="${fn:escapeXml(tupleId)}"/>
	<br/>
	<input type="submit" value="Insert Class Attribute Value for Tuple"/></div>
</form>
<br/>
<%
}
%>
<form action="/inserttuple" method="get">
    <div><input type="text" name="tupleId" value="${fn:escapeXml(tupleId)}"/>
	<input type="hidden" name="problemName" value="${fn:escapeXml(problemName)}"/>
    <br/>
	<input type="submit" value="Switch Tuple"/></div>
</form>

<form action="/insertattributevalues.jsp" method="get">
    <div><input type="text" name="problemName" value="${fn:escapeXml(problemName)}"/>
	<br/>
    <input type="submit" value="Switch Problem"/></div>
</form>

<a href="/welcomepage.jsp"> Home </a> <br/>
<a href="/insertattribute.jsp"> Insert attribute information </a>
<p>


</body>
</html>