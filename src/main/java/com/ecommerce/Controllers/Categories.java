package com.ecommerce.Controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.Entity.Categorie;
import com.ecommerce.adminDAO.AdminDAO;
import com.ecommerce.userDAO.UserDAO;

/**
 * Servlet implementation class Categories
 */
@WebServlet("/categories")
public class Categories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Categories() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("edit_id") != null) {
			Optional<Categorie> optCategorie = UserDAO.getCategorieById(Integer.parseInt(request.getParameter("edit_id")));
			
			if (optCategorie.isPresent()) {				
				request.setAttribute("categorieToEdit", optCategorie.get());
			}
		}
		
		request.setAttribute("CATEGORIES_LIST", UserDAO.getAllCategories());
		request.getRequestDispatcher("View/AdminSide/categories.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DELETE CATEGORY
		if (request.getParameter("delete_id") != null) {
			int id = Integer.parseInt(request.getParameter("delete_id"));
			AdminDAO.deleteCategory(id);
		}
		
		// ADD CATEGORY
		if (request.getParameter("categorie") != null && request.getParameter("id") == null) {
			AdminDAO.addCategory(new Categorie(request.getParameter("categorie")));
		}
		
		// UPDATE CATEGORY
		if (request.getParameter("id") != null && request.getParameter("categorie") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			String categorie = request.getParameter("categorie");
			AdminDAO.updateCategory(new Categorie(id, categorie));
		}
		
		response.sendRedirect("categories");
	}

}
