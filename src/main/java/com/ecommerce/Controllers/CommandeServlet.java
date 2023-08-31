package com.ecommerce.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.Entity.Commande;
import com.ecommerce.Entity.LignePanier;
import com.ecommerce.Entity.Panier;
import com.ecommerce.Entity.Role;
import com.ecommerce.Entity.User;
import com.ecommerce.adminDAO.AdminDAO;
import com.ecommerce.clientDAO.ClientDAO;
import com.ecommerce.userDAO.UserDAO;

/**
 * Servlet implementation class Commande
 */
@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommandeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		 User user =(User) session.getAttribute("USER");
		 
		 if (user != null) {
			 
			 // Create Date Of The Order Format: 'dd/MM/yyyy'  
			 LocalDate today = LocalDate.now();
			 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			 String formatedDate = today.format(dateTimeFormatter); 
			 
			 Optional<Commande> optCommande = ClientDAO.createCommande(user, formatedDate);
			 
			 if (optCommande.isPresent()) {
				 System.out.println("Commane Created");
				 Commande commande = optCommande.get();
				 List<LignePanier> lignePanier =(List<LignePanier>) session.getAttribute("LIST_LIGNE_PANIER");
				 
				 for (LignePanier lp : lignePanier) {
					 // Insert Into LigneCommande
					 if (lp.getProduit().getQuantityDispo() > lp.getQuantite()) {
						 System.out.println("Product To LigneCmd: " + lp.getProduit().getTitre());
						 int insertCount = ClientDAO.createLigneCommande(commande, lp.getProduit(), lp.getQuantite());
						 
						 if (insertCount > 0) {
							 // Decrease The QUANTITE
							 AdminDAO.decreaseQuantity(lp.getProduit(), lp.getQuantite());
						 }
					 }
				 }
				 
				 // Empty The Panier
				 Panier.setListLignePanier(new ArrayList<LignePanier>());
				 session.removeAttribute("LIST_LIGNES_PANIER");
			 }
			 
			 response.sendRedirect("ThankYouPage");
			 
		 }
		
	}

}
