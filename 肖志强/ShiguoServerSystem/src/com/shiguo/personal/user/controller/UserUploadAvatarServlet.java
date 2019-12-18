package com.shiguo.personal.user.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserUploadAvatarServlet
 */
@WebServlet("/User/UploadAvatar")
public class UserUploadAvatarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUploadAvatarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("UploadAvatar User");
		String imgname = request.getParameter("imgname");
		String str = getServletContext().getRealPath("/avatarimg");
		System.out.println(str);
		if(!(imgname==null)) {
			InputStream input =  request.getInputStream();
			String realPath = getServletContext().getRealPath("/avatarimg");
			File file = new File(realPath + "/" + imgname);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] a = new byte[1024];
			int len;
			while ((len = input.read(a)) != -1) {
				fileOutputStream.write(a,0,len);
			}
			input.close();
			fileOutputStream.flush();
			fileOutputStream.close();
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
