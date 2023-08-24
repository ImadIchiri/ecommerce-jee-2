package com.ecommerce.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.Entity.LignePanier;
import com.ecommerce.Entity.Panier;
import com.ecommerce.Entity.Produit;
import com.ecommerce.clientDAO.ClientDAO;
import com.ecommerce.userDAO.UserDAO;

/**
 * Servlet implementation class ShowProducts
 */
@WebServlet("/homepage")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		List<Produit> productList = new ArrayList<Produit>();
		
		if (session.getAttribute("PANIER") != null) {
			Panier tempPanier =(Panier) session.getAttribute("PANIER");
		}
		
		
		if (request.getParameter("productName") != null || request.getParameter("category") != null) {
			
			if (request.getParameter("productName") != null) {
				String productName = request.getParameter("productName");
				request.setAttribute("productName", productName);
				request.removeAttribute("category");
				
				if (session.getAttribute("PRODUCTS_LIST") != null) {
					List<Produit> tempProductsList =(List<Produit>) session.getAttribute("PRODUCTS_LIST");
					
					productList.addAll(
							tempProductsList.stream()
								.filter(p -> p.getTitre().contains(productName))
								.collect(Collectors.toList())
					);
				}
				
				if (session.getAttribute("PRODUCTS_LIST") == null) {
					productList.addAll(UserDAO.getProductsByName(productName));
				}
			}
			
			if (request.getParameter("category") != null) {
				String category = request.getParameter("category");
				request.setAttribute("category", category);
				request.removeAttribute("productName");
				
				if (session.getAttribute("PRODUCTS_LIST") != null) {
					List<Produit> tempProductsList =(List<Produit>) session.getAttribute("PRODUCTS_LIST");
					
					
					productList.addAll(
							tempProductsList.stream()
								.filter(p -> p.getCategorie().getCategorie().equalsIgnoreCase(category))
								.collect(Collectors.toList())
					);
				}
				
				if (session.getAttribute("PRODUCTS_LIST") == null) {
					productList.addAll(UserDAO.getProductsByCategorie(category));
				}
			}
		}
		
		if (request.getParameter("productName") == null && request.getParameter("category") == null) {
			request.removeAttribute("category");
			request.removeAttribute("productName");
			
			if (session.getAttribute("PRODUCTS_LIST") == null) {
				productList.addAll(UserDAO.getAllProducts());
				session.setAttribute("PRODUCTS_LIST", productList);
			}
			
			if (session.getAttribute("PRODUCTS_LIST") != null) {
				productList.addAll((List<Produit>) session.getAttribute("PRODUCTS_LIST"));
			}
		}
		
		request.setAttribute("ProductsList", productList);
		request.getRequestDispatcher("View/ClientSide/homePage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Produit produit = ClientDAO.getProductById(Integer.parseInt(request.getParameter("id")));
		Panier panier = null;

		if (session.getAttribute("PANIER") != null) {
			panier =(Panier) session.getAttribute("PANIER");
		}
		
		if (session.getAttribute("PANIER") == null) {
			panier = new Panier();
		}
				
		Optional<LignePanier> optLignePanier = ClientDAO.addProductToPanier(panier, produit, Integer.parseInt(request.getParameter("quantite")));
		
		if (optLignePanier.isPresent()) {			
			panier.insertIntoListLignePanier(optLignePanier.get());

			session.setAttribute("PANIER", panier);
		}
		
		// request.getRequestDispatcher("View/ClientSide/homePage.jsp").forward(request, response);
		response.sendRedirect("homepage");
	}

}
