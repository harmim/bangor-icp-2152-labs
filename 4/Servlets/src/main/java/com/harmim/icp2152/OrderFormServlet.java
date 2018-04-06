package com.harmim.icp2152;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import javax.servlet.annotation.WebServlet;


/**
 * Returns HTML page with testing POST of order form.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
@WebServlet(name = "OrderFormServlet", urlPatterns = {"/order-form-servlet"})
public class OrderFormServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		StringBuilder parameters = new StringBuilder();
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String) parameterNames.nextElement();
			parameters.append(parameterName).append(" = ").append(request.getParameter(parameterName)).append("<br>\n");
		}

		out.print(
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<meta charset=\"utf-8\">\n" +
			"<title>Testing doPost</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"<h1>Testing doPost</h1>\n" +
			"<p>\n" + parameters.toString() + "</p>\n" +
			"</body>\n" +
			"</html>\n"
		);

		out.flush();
		out.close();
	}
}