package com.shiguo.recruitment.position.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.shiguo.personal.user.service.UserServiceImpl;
import com.shiguo.recruitment.company.service.CompanyServiceImpl;
import com.shiguo.recruitment.position.service.PositionServiceImpl;

/**
 * Servlet implementation class PositionSearchAllServlet
 */
@WebServlet("/Position/SearchAll")
public class PositionSearchAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PositionSearchAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search All Position");
		
		response.setCharacterEncoding("UTF-8");

		OutputStream outputStream = response.getOutputStream();
		
		JSONObject res = new JSONObject();
		
		res.put("positionList", new PositionServiceImpl().positionList());
		
		outputStream.write(res.toString().getBytes("UTF-8"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
