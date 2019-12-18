package com.shiguo.recruitment.jobsearch.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.shiguo.entity.JobSearch;
import com.shiguo.recruitment.jobsearch.service.JobSearchServiceImpl;

/**
 * Servlet implementation class JobSearchSearchAllServlet
 */
@WebServlet("/JobSearch/SearchAll")
public class JobSearchSearchAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobSearchSearchAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search All JobSearch");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		OutputStream outputStream = response.getOutputStream();
		
		List<JobSearch> list = new JobSearchServiceImpl().jobsearchList();
		
		JSONObject res = new JSONObject();
		
		res.put("jobsearchList", list);
		
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
