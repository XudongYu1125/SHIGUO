package com.shiguo.questionbank.paper.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shiguo.entity.Paper;
import com.shiguo.questionbank.paper.service.PaperServiceImpl;

/**
 * Servlet implementation class PaperSearchByTypeServlet
 */
@WebServlet("/Paper/SearchByType")
public class PaperSearchByTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaperSearchByTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("SearchByType Paper");
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
			List<Paper> paperlist = new PaperServiceImpl().searchPaperByType(Integer.parseInt(resStr));
			Gson gson = new Gson();
			String pl = gson.toJson(paperlist);
			response.getWriter().append(pl);
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
