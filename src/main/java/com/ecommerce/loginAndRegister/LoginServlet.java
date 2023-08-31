package com.ecommerce.loginAndRegister;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.Entity.User;
import com.ecommerce.userDAO.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("View/LoginAndRegister/loginPage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = "";
		
		if (request.getParameter("email") != null && request.getParameter("password") != null) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			Optional<User> optUser = UserDAO.userLogin(email, password);
			
			if (optUser.isPresent()) {
				session.setAttribute("USER", optUser.get());
				session.removeAttribute("ERROR");
				
				if (optUser.get().getRole().getRole().equalsIgnoreCase("admin")) {
					path = "categories";
				} else {
					path = "homepage";					
				}
			} else {
				session.setAttribute("ERROR", "Email Or Password Incorrect");
				path = "login";
			}
			
			
			response.sendRedirect(path);
		}
	}

}
