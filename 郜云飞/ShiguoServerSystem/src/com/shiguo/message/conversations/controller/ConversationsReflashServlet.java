package com.shiguo.message.conversations.controller;

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

import com.shiguo.entity.Conversations;
import com.shiguo.message.conversations.service.ConversationsServiceImpl;

/**
 * Servlet implementation class ConversationsFlashServlet
 */
@WebServlet("/Conversations/Reflash")
public class ConversationsReflashServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationsReflashServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Reflash Conversations");
		
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
	        
	        JSONObject object = new JSONObject(stringBuffer.toString());
	        
	        int userid = object.getInt("userid");
	        String lastTime = object.getString("lastTime");
			
			JSONObject res = new JSONObject();
			
			List<Conversations> newList = new ConversationsServiceImpl().reflashConversations(userid, lastTime);
			
			if(newList != null) {
				
				res.put("newList", newList);
		
			}else {
				
			}
			
			outputStream.write(res.toString().getBytes("UTF-8"));
			
			
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
