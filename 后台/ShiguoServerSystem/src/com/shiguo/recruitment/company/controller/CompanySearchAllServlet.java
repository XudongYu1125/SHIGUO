package com.shiguo.recruitment.company.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.shiguo.entity.Company;
import com.shiguo.recruitment.company.service.CompanyServiceImpl;

/**
 * Servlet implementation class CompanySearchAllServlet
 */
@WebServlet("/Company/SearchAll")
public class CompanySearchAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanySearchAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search Company");
		
		response.setCharacterEncoding("UTF-8");

		OutputStream outputStream = response.getOutputStream();
		
		JSONObject res = new JSONObject();
		
		List<Company> list = new CompanyServiceImpl().companyList();
		
		res.put("companyList", list);
		
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
