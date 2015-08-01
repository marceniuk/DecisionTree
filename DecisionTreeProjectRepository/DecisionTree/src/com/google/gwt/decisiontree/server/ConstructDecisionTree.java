package com.google.gwt.decisiontree.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gwt.decisiontree.model.*;

import org.abego.treelayout.demo.*;
import org.abego.treelayout.util.*;
import org.abego.treelayout.*;
import org.abego.treelayout.demo.svg.*;


public class ConstructDecisionTree extends HttpServlet {
	
	 @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {

		 doPost(req, resp);
	 }

	 @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doPost(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
			      
			 HttpSession session = request.getSession(false);

			 String base = "/jsp/";
			 String url = base + "index.jsp";
			 String action = request.getParameter("action");
			 String teacher_to_investigate = request.getParameter("teacher_to_investigate");

			 processing: {
			 if (action != null) {
			 if (action.equals("loginAction"))
			   {

			      //Get the old session, but don't create a session if
			      //one didn't already exist (passing true would allow
			      //creation of a new one).
			     HttpSession oldSess = request.getSession(false);

			      //If there was an old session, invalidate it.
			     if (oldSess != null)
			     {
			          oldSess.invalidate();
			      }
			      //Now create a fresh new session.
			     session = request.getSession(true);
/*
			      String login_ = request.getParameter("log_in");
			      String password_ = request.getParameter("pass");
			      //Integer id = dataManager.getUser_idByLoginPassword(login_, password_);
			      Integer session_user_id = (Integer)session.getAttribute("user_id");
			      if((id == null) & (session_user_id == null)) {
			      response.setContentType("text/html;charset=\"UTF-8\"");
			      PrintWriter out = response.getWriter();
			      String title = "Login failed";
			      out.println("<BODY>\n" +
			                 "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
			                 "<UL>\n" +
			                 "  <LI>login: "
			                 + request.getParameter("log_in") + "\n" +
			                 "  <LI>password: "
			                 + request.getParameter("pass") + "\n" +
			                 "</BODY></HTML>"); 
			      break processing;}
			      else {
			            if(session_user_id != null)
			                {id = session_user_id;}
			            if(dataManager.checkAdministratorStatus(login_)) {session.setAttribute("isAdministrator",true);}
			                 else {session.setAttribute("isAdministrator",false);}
			            session.setAttribute("user_id",id);
			            session.setAttribute("indices_validated", true);
			            url = base + "login.jsp";
			      }
			      */
			   }
			 else if (action.equals("construct_decision_tree")) {
				 url = base + "constructDecisionTree.jsp";
				 
			 }
			 else if (action.equals("construct_svg_decision_tree")) {
				 org.abego.treelayout.TreeForTreeLayout tree = SampleTreeFactory.createSampleTree();
				 double gapBetweenLevels = 50D;
				 double gapBetweenNodes = 10D;
				 DefaultConfiguration configuration = new DefaultConfiguration(gapBetweenLevels, gapBetweenNodes);
				 TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
				 TreeLayout treeLayout = new TreeLayout(tree, nodeExtentProvider, configuration);
				 SVGForTextInBoxTree generator = new SVGForTextInBoxTree(treeLayout);
				 String sSVG = generator.getSVG();
				 PrintWriter out = response.getWriter();
				 out.println("<BODY>\n" +
			                 "<H1 ALIGN=CENTER>construct_svg_decision_tree</H1>\n" +
						     sSVG + 
			                 "</BODY></HTML>");
				 break processing;
			 }
			 else if (action.equals("construct_svg_decision_tree_2")) {
				 org.abego.treelayout.TreeForTreeLayout tree = SampleTreeFactory.createSampleTree2();
				 double gapBetweenLevels = 50D;
				 double gapBetweenNodes = 10D;
				 DefaultConfiguration configuration = new DefaultConfiguration(gapBetweenLevels, gapBetweenNodes);
				 TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
				 TreeLayout treeLayout = new TreeLayout(tree, nodeExtentProvider, configuration);
				 SVGForTextInBoxTree generator = new SVGForTextInBoxTree(treeLayout);
				 String sSVG = generator.getSVG();
				 PrintWriter out = response.getWriter();
				 out.println("<BODY>\n" +
			                 "<H1 ALIGN=CENTER>construct_svg_decision_tree 2</H1>\n" +
						     sSVG + 
			                 "</BODY></HTML>");
				 break processing;
			 }
			 }
			 System.out.println(url);
			 RequestDispatcher requestDispatcher =
			 getServletContext().getRequestDispatcher(url);
			 requestDispatcher.forward(request, response);
			 }// end processing
			 }
	 
}
