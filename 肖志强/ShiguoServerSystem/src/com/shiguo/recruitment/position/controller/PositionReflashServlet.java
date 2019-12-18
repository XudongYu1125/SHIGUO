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
 * Servlet implementation class PositionReflashServlet
 */
@WebServlet("/Position/Reflash")
public class PositionReflashServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PositionReflashServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Reflash Position");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			
			InputStream inputStream = request.getInputStream();
			OutputStream outputStream = response.getOutputStream();
			
			byte[] buffer = new byte[2048];
	        int len;
	        StringBuffer stringBuffer = new StringBuffer();
	        while ((len = inputStream.read(buffer)) != -1) {
	            stringBuffer.append(new String(buffer, 0, len));
	        }
	        
	        Gson gson = new Gson();
	       
	        String lastTime = stringBuffer.toString();
									
			List<Position> reflashList = new PositionServiceImpl().reflashPosition(lastTime);
				
			outputStream.write(gson.toJson(reflashList).getBytes("UTF-8"));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
