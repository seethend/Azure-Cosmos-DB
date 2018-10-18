package com.college.servelts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.college.controller.ColumnsController;
import com.google.gson.Gson;


@WebServlet("/getcolleges")
public class MainAppServlet extends HttpServlet{
	
	public static final String MESSAGE_ERROR_INVALID_METHOD = "{'error': 'Invalid method'}";
	
	private static final long serialVersionUID = 1L;
	private static final Gson gson = new Gson();
	
	public void service(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String loc = request.getParameter("loc");
		int tut = Integer.parseInt(request.getParameter("tut"));
		
		System.out.println("In Servlet \n loc: "+loc+"-tut: "+tut);
		
		String apiResponse = MESSAGE_ERROR_INVALID_METHOD;
		
		ColumnsController columnsController = ColumnsController.getInstance();
		
		apiResponse = gson.toJson(columnsController.UpdateCollegesList(loc, tut));
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(apiResponse);
		
	}
}
