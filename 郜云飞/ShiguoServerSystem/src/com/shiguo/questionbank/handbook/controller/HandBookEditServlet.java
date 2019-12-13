package com.shiguo.questionbank.handbook.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shiguo.entity.HandBook;
import com.shiguo.questionbank.handbook.service.HandBookServiceImpl;

/**
 * Servlet implementation class ChatRecordEditServlet
 */
@WebServlet("/HandBook/Edit")
public class HandBookEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandBookEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("Edit HandBook");
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
		HandBook HandBook = gson.fromJson(resStr, HandBook.class);
		if(HandBook == null) {
			response.getWriter().append("false");
		}else {
			if(new HandBookServiceImpl().editHandBook(HandBook)) {
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
