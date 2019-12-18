package com.shiguo.recruitment.jobsearch.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.shiguo.recruitment.jobsearch.service.JobSearchServiceImpl;

/**
 * Servlet implementation class JobSearchSearchByEntity
 */
@WebServlet("/JobSearch/SearchByCondition")
public class JobSearchSearchByConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobSearchSearchByConditionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search JobSearch By Condition");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		InputStream inputStream = request.getInputStream();
		OutputStream outputStream = response.getOutputStream();
		
		byte[] buffer = new byte[2048];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = inputStream.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
		
		
		JSONObject object = new JSONObject(stringBuffer.toString());
        JSONObject res = new JSONObject();
        
        String condition = object.getString("condition");
        String lastTime = object.getString("lastTime");
        
        res.put("jobsearchList", new JobSearchServiceImpl().searchJobSearchByCondition(condition, lastTime));

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
