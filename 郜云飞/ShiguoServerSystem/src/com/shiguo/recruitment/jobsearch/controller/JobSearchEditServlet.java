package com.shiguo.recruitment.jobsearch.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shiguo.entity.JobSearch;
import com.shiguo.recruitment.jobsearch.service.JobSearchServiceImpl;

/**
 * Servlet implementation class ChatRecordEditServlet
 */
@WebServlet("/JobSearch/Edit")
public class JobSearchEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobSearchEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("Edit JobSearch");
		String resStr = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer sb=new StringBuffer("");
		String line;
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		resStr=sb.toString();
		Gson gson = new Gson();
		JobSearch JobSearch = gson.fromJson(resStr, JobSearch.class);
		if(JobSearch == null) {
			response.getWriter().append("false");
		}else {
			if(new JobSearchServiceImpl().editJobSearch(JobSearch)) {
				response.getWriter().append("true");
			}else {
				response.getWriter().append("false");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
