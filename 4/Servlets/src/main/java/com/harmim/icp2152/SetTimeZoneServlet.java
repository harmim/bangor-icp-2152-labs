package com.harmim.icp2152;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import javax.servlet.annotation.WebServlet;


/**
 * Returns HTML page with current time by time zone.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
@WebServlet(name = "SetTimeZoneServlet", urlPatterns = {"/set-time-zone-servlet"})
public class SetTimeZoneServlet extends HttpServlet
{
	/**
	 * {@inheritDoc}
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String result;
		TimeZone timeZone = parseTimeZone(request.getParameter("timeZone"));
		if (timeZone != null) {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			dateFormat.setTimeZone(timeZone);
			result = String.format("The current time in %s is %s.", timeZone.getID(), dateFormat.format(new Date()));
		} else {
			result = String.format("Sorry, no information is available for %s.", request.getParameter("timeZone"));
		}

		out.print(
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<meta charset=\"utf-8\">\n" +
			"<title>Testing doPost</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"<p>" + result + "</p>\n" +
			"<button onclick=\"document.location.href='set-time-zone.html'\">Back</button>\n" +
			"</body>\n" +
			"</html>\n"
		);

		out.flush();
		out.close();
	}


	/**
	 * Parses time zone string and returns appropriate time zone object.
	 *
	 * @param input time zone in string to be parsed
	 * @return appropriate time zone object or null if parsing fails
	 */
	private TimeZone parseTimeZone(String input)
	{
		TimeZone timeZone = TimeZone.getTimeZone(input);
		if (input.equalsIgnoreCase("GMT") || !timeZone.getID().equals("GMT")) {
			return timeZone;
		}

		for (String timeZoneId : TimeZone.getAvailableIDs()) {
			if (timeZoneId.substring(timeZoneId.indexOf('/') + 1).replace('_', ' ').equalsIgnoreCase(input)) {
				return TimeZone.getTimeZone(timeZoneId);
			}
		}

		return null;
	}
}
