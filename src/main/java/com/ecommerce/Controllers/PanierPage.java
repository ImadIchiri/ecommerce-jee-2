package com.ecommerce.Controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.Entity.Panier;

/**
 * Servlet implementation class Panier
 */
@WebServlet("/panier")
public class PanierPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("View/ClientSide/panier.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			Panier.removeFromListLignePanier(id);

			session.setAttribute("PANIER", Panier.getListLignePanier());			
		}
		
		response.sendRedirect("panier");
	}
	
}
