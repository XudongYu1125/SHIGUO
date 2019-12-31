package com.shiguo.personal.resume.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.shiguo.entity.Application;
import com.shiguo.personal.resume.service.ResumeServiceImpl;

/**
 * Servlet implementation class ResumeAddBySRRIdServlet
 */
@WebServlet("/ResumeApplication/AddBySRRId")
public class ResumeApplicationAddBySRRIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResumeApplicationAddBySRRIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("AddBySRRId ResumeApplication");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		InputStream inputStream = request.getInputStream();
		
		byte[] buffer = new byte[2048];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = inputStream.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
		if(!stringBuffer.toString().equals("")) {
			
			System.out.println(stringBuffer.toString());
			
			JSONObject object = new JSONObject(stringBuffer.toString());
			
	        JSONObject res = new JSONObject();
	        
	        Application application = new Application();
	        String senderid = object.getString("senderid");
	        String receiverid = object.getString("receiverid");
	        String resumeid = object.getString("resumeid");
	        application.setSenderid(Integer.parseInt(senderid));
	        application.setReceiverid(Integer.parseInt(receiverid));
	        application.setResumeid(Integer.parseInt(resumeid));
	        
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        application.setDate(simpleDateFormat.format(new Date().getTime()));
	        
	        if(new ResumeServiceImpl().addResumeApplication(application)) {
	        	
		        response.getWriter().append("true");
		        System.out.println("true");
		        
	        }else {
	        	response.getWriter().append("false");
				System.out.println("false");
	        }
		}else {
			response.getWriter().append("false");
			System.out.println("false");
		}
		inputStream.close();
		
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
