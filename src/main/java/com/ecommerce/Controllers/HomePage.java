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
		
		
		if (request.getParameter("productName") != null || request.getParameter("category") != null) {
			
			if (request.getParameter("productName") != null) {
				String productName = request.getParameter("productName");
				request.setAttribute("productName", productName);
				request.removeAttribute("category");
				
				if (session.getAttribute("PRODUCTS_LIST") != null) {
					List<Produit> tempProductsList =(List<Produit>) session.getAttribute("PRODUCTS_LIST");
					
					productList.addAll(
							tempProductsList.stream()
								.filter(p -> p.getTitre().toLowerCase().contains(productName.toLowerCase()))
								.collect(Collectors.toList())
					);
				}
				
				if (session.getAttribute("PRODUCTS_LIST") == null) {
					// Filtred Products By Name
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
					// Filtred Products By Category
					productList.addAll(UserDAO.getProductsByCategorie(category));
				}
			}
		}
		
		if (request.getParameter("productName") == null && request.getParameter("category") == null) {
			request.removeAttribute("category");
			request.removeAttribute("productName");
			
			
			// Order Is So Important ('!= NULL' THEN '== NULL')
			if (session.getAttribute("PRODUCTS_LIST") != null) {
				productList.addAll((List<Produit>) session.getAttribute("PRODUCTS_LIST"));
			}
			
			if (session.getAttribute("PRODUCTS_LIST") == null) {
				productList.addAll(UserDAO.getAllProducts());
				session.setAttribute("PRODUCTS_LIST", productList);
			}
		}
		
		request.setAttribute("PRODUCTS_LIST", productList);
		request.getRequestDispatcher("View/ClientSide/homePage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		System.out.println("START DO POST");
		
		Produit produit = ClientDAO.getProductById(Integer.parseInt(request.getParameter("id")));
		Panier panier = null;
		List<LignePanier> listLignePanier = null;
		
		System.out.println(produit);

		if (session.getAttribute("LIST_LIGNE_PANIER") != null) {
			System.out.println("PANIER_NOT_NULL: " + session.getAttribute("LIST_LIGNE_PANIER"));
			listLignePanier =(List<LignePanier>) session.getAttribute("LIST_LIGNE_PANIER");
			panier = listLignePanier.get(0).getPanier();
			System.out.println("END_PANIER_NOT_NULL: " + panier);
		}
		
		if (session.getAttribute("LIST_LIGNE_PANIER") == null) {
			System.out.println("PANIER_NULL");
			panier = new Panier();
		}
		
		System.out.println("PANIER(1) ==> " + panier);
				
		Optional<LignePanier> optLignePanier = ClientDAO.addProductToPanier(panier, produit, Integer.parseInt(request.getParameter("quantite")));
		
		System.out.println("OPT_LIGNE_PANIER ==> " + optLignePanier);
		
		if (optLignePanier.isPresent()) {
			if (panier != null) {
				Panier.insertIntoListLignePanier(optLignePanier.get());
				session.setAttribute("LIST_LIGNE_PANIER", Panier.getListLignePanier());
			}
			
			if (panier == null) {
				listLignePanier.add(optLignePanier.get());
				session.setAttribute("LIST_LIGNE_PANIER", listLignePanier);
			}

			
			System.out.println("PANIER(2) ==> " + session.getAttribute("LIST_LIGNE_PANIER"));
		}
		
		response.sendRedirect("homepage");
	}

}
