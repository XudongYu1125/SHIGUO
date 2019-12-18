package com.shiguo.questionbank.problem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shiguo.entity.Paper;
import com.shiguo.questionbank.handbook.service.HandBookServiceImpl;
import com.shiguo.questionbank.problem.service.ProblemServiceImpl;

/**
 * Servlet implementation class ProblemSearchByPaperIdServlet
 */
@WebServlet("/Problem/SearchByPaperId")
public class ProblemSearchByPaperIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProblemSearchByPaperIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("SearchByPaperId HandBook");
		String resStr = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer sb=new StringBuffer("");
		String line;
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		resStr=sb.toString();		
		if(!resStr.equals("")) {
			Gson gson = new Gson();
			Paper paper = gson.fromJson(resStr, Paper.class);
			String prolist = gson.toJson(new ProblemServiceImpl().searchProblemListByPaperId(paper.getPaperid()));
			response.getWriter().append(prolist);
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
