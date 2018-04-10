package com.harmim.icp2152;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;


/**
 * Returns HTML page with personalised welcome login message.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login-servlet"})
public class LoginServlet extends HttpServlet
{
	/**
	 * {@inheritDoc}
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		out.print(
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<meta charset=\"UTF-8\">\n" +
			"<title>Login</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"Hi user " + request.getParameter("username") + ". You are now logged into the system.\n" +
			"</body>\n" +
			"</html>\n"
		);

		out.flush();
		out.close();
	}
}
