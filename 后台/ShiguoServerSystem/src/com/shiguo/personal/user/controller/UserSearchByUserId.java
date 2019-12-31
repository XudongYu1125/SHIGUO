package com.shiguo.personal.user.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiguo.entity.Company;
import com.shiguo.entity.User;
import com.shiguo.personal.user.service.UserServiceImpl;
import com.shiguo.recruitment.company.service.CompanyServiceImpl;


/**
 * Servlet implementation class UserSearchByUserId
 */
@WebServlet("/User/SearchByUserId")
public class UserSearchByUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchByUserId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("SearchById User");
		
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
		
        Gson gson = new Gson();
		Type type = new TypeToken<List<Integer>>() {}.getType();
		List<Integer> userList = gson.fromJson(stringBuffer.toString(),type);
		
		System.out.println(userList.toString());
        
     
        List<String[]> list = new ArrayList<String[]>();
        
        for(int i:userList) {
        	
        	User user = new UserServiceImpl().searchUserById(i);
        	
	        if(user != null) {
	        	
	        	String[] temp = new String[3];
	        	
	        	temp[0] = user.getNickname();
	        	temp[1] = user.getImageurl();
	        	
	        	list.add(temp);	        		        	
	        }
        }
        
        outputStream.write(gson.toJson(list).getBytes("UTF-8"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
