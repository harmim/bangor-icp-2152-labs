package com.harmim.icp2152;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Random;
import javax.servlet.annotation.WebServlet;


/**
 * Returns HTML page with random fact.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
@WebServlet(name = "RandomFactServlet", urlPatterns = {"/random-fact-servlet"})
public class RandomFactServlet extends HttpServlet
{
	/**
	 * Computer science facts.
	 */
	private static final String[] facts = {
		"Computer scientists apply their knowledge of information theory and computation to computer systems.",
		"Computer scientists usually focus on theory while computer engineers focus on hardware.",
		"Areas of computer science include programming, software engineering, information theory, algorithms, " +
			"databases and graphics.",
		"Important subjects related to computer science include physics, algebra, calculus and English.",
		"Good computer scientists have strong analytical, problem solving and logic skills.",
		"Communication skills are also an important part of computer science.",
		"A bachelor’s degree is sufficient for a number of jobs related to computer science but higher level " +
			"roles often require a PhD.",
		"Our increasing reliance on computer systems makes computer science a growing field with computer " +
			"scientists typically being sought after and receiving high salaries.",
		"A job as a computer scientist may involve creating software, teaching at a university, research and " +
			"development, consulting or programming.",
		"As a computer scientist you could end up animating movies, testing software, designing video games, " +
			"evaluating computer systems, working on websites or creating applications for mobile phones.",
	};


	/**
	 * Random generator instance.
	 */
	private Random random = new Random();

	/**
	 * Site hit count.
	 */
	private int hitCount = 0;


	/**
	 * {@inheritDoc}
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		out.print(
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<meta charset=\"UTF-8\">\n" +
			"<title>Testing doPost</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"<h1>Fact of the day</h1>\n" +
			"<p>" + facts[random.nextInt(facts.length)]  + "</p>\n" +
			"Site hits = " + ++hitCount + "\n" +
			"</body>\n" +
			"</html>\n"
		);

		out.flush();
		out.close();
	}
}
