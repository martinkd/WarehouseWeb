package com.martin.warehouse.servlet;

import java.io.IOException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.security.auth.login.LoginContextFactory;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getUserPrincipal() != null) {
				// Logout user
				logout();
			}
		} catch (Exception e) {
			// Logout operation failed
			response.getWriter().println("Logout operation failed with reason: " + e.getMessage());
		}
	}

	private void logout() throws LoginException {
		LoginContext loginContext = LoginContextFactory.createLoginContext();
		loginContext.logout();
	}

}
