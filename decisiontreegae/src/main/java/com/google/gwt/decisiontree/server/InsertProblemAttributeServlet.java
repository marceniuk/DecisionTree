package com.google.gwt.decisiontree.server;

import com.google.gwt.decisiontree.model.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Form Handling Servlet
 * Most of the action for this sample is in webapp/insertattribute.jsp, which displays the
 * {@link Attribute}'s. This servlet has one method
 * {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse resp#>)} which takes the form
 * data and saves it.
 */
public class InsertProblemAttributeServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Attribute attribute;

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();  // Find out who the user is.

    String problemName = req.getParameter("problemName");
    String attribute_name = req.getParameter("attribute_name");
	String attribute_field_name = req.getParameter("attribute_field_name");
    attribute = new Attribute(ofy().load().type(Problem.class).filter("problemname", problemName).first().now(), attribute_name, attribute_field_name);

    // Use Objectify to save the greeting and now() is used to make the call synchronously as we
    // will immediately get a new page using redirect and we want the data to be present.
    ObjectifyService.ofy().save().entity(attribute).now();

    resp.sendRedirect("/insertattribute.jsp?problemName=" + problemName);
  }
}