package com.harmim.icp2152;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;


/**
 * Returns HTML page with Hello message to user from get parameter.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
@WebServlet(name = "PersonalHelloServlet", urlPatterns = {"/personal-hello-servlet"})
public class PersonalHelloServlet extends HttpServlet
{
	/**
	 * {@inheritDoc}
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String body;
		String name = request.getParameter("name");
		if (name == null || name.equals("")) {
			body = "<h1>Hello user</h1>\n<h1>Don't forget to enter your name!</h1>\n";
		} else {
			body = "<h1>Hello " + name + "</h1>\n";
		}

		out.print(
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<meta charset=\"UTF-8\">\n" +
			"<title>Personal Hello</title>\n" +
			"</head>\n" +
			"<body>\n" +
			body +
			"</body>\n" +
			"</html>\n"
		);

		out.flush();
		out.close();
	}
}
