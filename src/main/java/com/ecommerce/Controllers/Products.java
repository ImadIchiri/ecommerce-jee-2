package com.ecommerce.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ecommerce.Entity.Categorie;
import com.ecommerce.Entity.Produit;
import com.ecommerce.adminDAO.AdminDAO;
import com.ecommerce.userDAO.UserDAO;

/**
 * Servlet implementation class Products
 */
@WebServlet("/products")
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String getSubmittedFileName(Part part) {
	    String contentDispositionHeader = part.getHeader("content-disposition");
	    String[] elements = contentDispositionHeader.split(";");
	    for (String element : elements) {
	        if (element.trim().startsWith("filename")) {
	            return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Products() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Produit> productsList = new ArrayList<Produit>();
		List<Categorie> categoriesList = new ArrayList<Categorie>();
		
		if (request.getParameter("edit_id") != null) {
			int id = Integer.parseInt(request.getParameter("edit_id"));
			Optional<Produit> productToEdit = AdminDAO.getProductById(id);
			
			request.setAttribute("PRODUCT_TO_EDIT", productToEdit.get());
		} else {
			request.removeAttribute("PRODUCT_TO_EDIT");
		}
		
		/*
		 * START GETTING PRODUCTS >> Order Of (!= NULL) Followed By (== NULL) Is Important
		*/
		if (session.getAttribute("PRODUCTS_LIST") != null) {
			productsList.addAll((List<Produit>) session.getAttribute("PRODUCTS_LIST"));
		}
		
		if (session.getAttribute("PRODUCTS_LIST") == null) {
			productsList.addAll(UserDAO.getAllProducts());
			session.setAttribute("PRODUCTS_LIST", productsList);
		}
		
		/*
		 * END GETTING PRODUCTS
		*/
		
		/*
		 * START GETTING CATEGORIES >> Order Of (!= NULL) Followed By (== NULL) Is Important
		*/
		if (session.getAttribute("CATEGORIES_LIST") != null) {
			categoriesList.addAll((List<Categorie>) session.getAttribute("CATEGORIES_LIST"));
		}
		
		if (session.getAttribute("CATEGORIES_LIST") == null) {
			categoriesList.addAll(UserDAO.getAllCategories());
			session.setAttribute("CATEGORIES_LIST", categoriesList);
		}
		/*
		 * END GETTING CATEGORIES
		*/
		
		request.setAttribute("CATEGORIES_LIST", categoriesList);
		
		if (request.getParameter("category") != null) {
			request.setAttribute("PRODUCTS_LIST", productsList.stream()
					.filter(item -> item.getCategorie()
							.getCategorie()
							.equalsIgnoreCase(request.getParameter("category"))
					).collect(Collectors.toList()));
		}
		
		if (request.getParameter("category") == null) {			
			request.setAttribute("PRODUCTS_LIST", productsList);
		}
		
		request.getRequestDispatcher("View/AdminSide/products.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getParameter("delete_id") != null) {
			int id = Integer.parseInt(request.getParameter("delete_id"));
			List<Produit> productsList = new ArrayList<Produit>();
			
			AdminDAO.deleteProduct(id);
			
			if (session.getAttribute("PRODUCTS_LIST") != null) {
				productsList.addAll((List<Produit>) session.getAttribute("PRODUCTS_LIST"));
				productsList.removeIf(product -> product.getId() == id);
				
				session.setAttribute("PRODUCTS_LIST", productsList);
			}
		}
		
		if (request.getParameter("id") != null && request.getParameter("delete_id") == null) {
			int id = Integer.parseInt(request.getParameter("id"));
			String titre = request.getParameter("titre");
			double prix = Double.parseDouble(request.getParameter("prix"));
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			String imageName = request.getParameter("image");
			
			int categoryId = Integer.parseInt(request.getParameter("categorie"));
			Optional<Categorie> optCategory = UserDAO.getCategorieById(categoryId);
			
			Categorie categorie = optCategory.get();
			
			AdminDAO.addProduct(new Produit(id, titre, prix, quantite, imageName, categorie));
		}
		
		if (request.getParameter("id") == null && request.getParameter("delete_id") == null) {
			List<Produit> productsList = new ArrayList<Produit>();
			
			String titre = request.getParameter("titre");
			double prix = Double.parseDouble(request.getParameter("prix"));
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			
			int categoryId = Integer.parseInt(request.getParameter("categorie"));
			Optional<Categorie> optCategory = UserDAO.getCategorieById(categoryId);
			
			Categorie categorie = optCategory.get();
			
			// Insert Product Without Image
			AdminDAO.addProduct(new Produit(titre, prix, quantite, "", categorie));
			
			/*
			 * START IMAGE BLOCK
			 */
			
			// Get The Product In Order To Get Its ID
			Produit insertedProduct = AdminDAO.getProductToSetImage().get();
			// SET THE IMAGE
			insertedProduct.setImageName("image-" + insertedProduct.getId());
			
		    Part filePart = request.getPart("image");
		    
		    String fileName = insertedProduct.getImageName() + ".webp";
		    String filePath = "C:\\Users\\pc\\eclipse-workspace\\ecommerce-jee-2\\src\\main\\webapp\\images\\products\\" + fileName;
		    
		    // Copy the content of the uploaded part to the new file
		    try (InputStream fileContent = filePart.getInputStream()) {
		        Files.copy(fileContent, Paths.get(filePath));
		        System.out.println("File saved successfully.");
		    } catch (IOException e) {
		        e.printStackTrace(); // Handle the exception appropriately
		    }
		    
		    // UPDATE THE PRODUCT IN ORDER TO HAVE THE IMAGE's PATH
		    AdminDAO.updateProduct(insertedProduct);
		    request.removeAttribute("PRODUCT_TO_EDIT");
			/*
			 * END IMAGE BLOCK
			*/
		    
		    // Be Sure That The Filter Exists After This Request
		    if (request.getParameter("category") != null) {
				request.setAttribute("category", request.getParameter("category"));
			}
		    
		    if (session.getAttribute("PRODUCTS_LIST") != null) {
				productsList.addAll((List<Produit>) session.getAttribute("PRODUCTS_LIST"));
				productsList.add(insertedProduct);
				
				session.setAttribute("PRODUCTS_LIST", productsList);
			}
		}
		
		response.sendRedirect("products");
	}

}
