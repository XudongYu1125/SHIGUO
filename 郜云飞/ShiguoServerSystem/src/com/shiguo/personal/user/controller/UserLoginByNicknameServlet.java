package com.shiguo.personal.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shiguo.entity.User;
import com.shiguo.entity.UserStatus;
import com.shiguo.personal.user.service.UserServiceImpl;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/User/LoginByNickname")
public class UserLoginByNicknameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginByNicknameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("LoginByNickname User");
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
		UserStatus userStatus = new UserStatus();
		String us ;
		if(!resStr.equals("")) {
			
			User user = gson.fromJson(resStr, User.class);
			
			User userT  =new UserServiceImpl().searchUserByNickname(user.getNickname().toString());
			if(userT!=null){
				if(user.getPassword().equals(userT.getPassword())) {
					userStatus.setUser(userT);
					userStatus.setStatus("0");
					System.out.println("0");
					us = gson.toJson(userStatus);
					response.getWriter().append(us);
				}else {
					userStatus.setStatus("1");
					us = gson.toJson(userStatus);
					response.getWriter().append(us);
					System.out.println("1");
				}
			}else {
				userStatus.setStatus("2");
				us = gson.toJson(userStatus);
				response.getWriter().append(us);
				System.out.println("2");
			}
		}else {
			userStatus.setStatus("3");
			us = gson.toJson(userStatus);
			response.getWriter().append(us);
			System.out.println("3");
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
