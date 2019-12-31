package com.shiguo.message.conversations.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shiguo.entity.Conversations;
import com.shiguo.message.conversations.service.ConversationsServiceImpl;

/**
 * Servlet implementation class ChatRecordSeachServlet
 */
@WebServlet("/Conversations/SearchByUUID")
public class ConversationsSearchByUUIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationsSearchByUUIDServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Search Conversations By UUID");
		
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
		
        Conversations conversations = new Conversations();
		
		Gson gson = new Gson();		
		conversations = gson.fromJson(stringBuffer.toString(), Conversations.class);
		
		Conversations result = new ConversationsServiceImpl().searchConversationsByUUID(conversations.getUUID());
		
		if(result != null) {
			outputStream.write(gson.toJson(result).getBytes("UTF-8"));
		}else {
			outputStream.write("false".getBytes("UTF-8"));
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
