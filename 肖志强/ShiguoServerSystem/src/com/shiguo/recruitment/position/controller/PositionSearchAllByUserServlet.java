package com.shiguo.recruitment.position.controller;

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

import com.google.gson.Gson;
import com.shiguo.entity.Position;
import com.shiguo.recruitment.position.service.PositionServiceImpl;

/**
 * Servlet implementation class PositionSearchAllByUserServlet
 */
@WebServlet("/Position/SearchAllByUser")
public class PositionSearchAllByUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PositionSearchAllByUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search All Position By User");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		InputStream inputStream = request.getInputStream();
		
		byte[] buffer = new byte[2048];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = inputStream.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
        String str = stringBuffer.toString();
        if(!str.equals("")) {
        	Gson gson = new Gson();
        	List<Position> list = new PositionServiceImpl().searchPositionByUser(Integer.parseInt(str));
        	if(list != null) {
        		response.getWriter().append(gson.toJson(list));
        	}
        }else {
        	response.getWriter().append("");
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
