package com.harmim.icp2152;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;


/**
 * Returns Hello World! HTML page.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/hello-world-servlet"})
public class HelloWorldServlet extends HttpServlet
{
	/**
	 * {@inheritDoc}
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.print(
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<meta charset=\"utf-8\">\n" +
			"<title>Hello World!</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"<h1>Hello World!</h1>\n" +
			"</body>\n" +
			"</html>\n"
		);

		out.flush();
		out.close();
	}
}
