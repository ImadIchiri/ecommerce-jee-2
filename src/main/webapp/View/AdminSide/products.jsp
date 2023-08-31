<%@page import="com.ecommerce.Entity.Categorie"%>
<%@page import="com.ecommerce.Entity.Produit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
    	List<Produit> productsList =(List<Produit>) request.getAttribute("PRODUCTS_LIST");
    	List<Categorie> categoriessList =(List<Categorie>) request.getAttribute("CATEGORIES_LIST");
    	Produit productToEdit = (Produit) request.getAttribute("PRODUCT_TO_EDIT");
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!-- BootStrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<!-- FontAwsome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<%@include file="navBar.jsp" %>
	<h1>Products</h1>
	
	<div class="container">
	<form action="products" method="post" enctype="multipart/form-data">
	
	<% if (productToEdit != null) { %>
	  	<input name="id" hidden="" value="<%=productToEdit.getId() %>">
	<% } %>
	
	  <div class="mb-3">
	    <label for="titre" class="form-label">Titre</label>
	    <input type="text" class="form-control" id="titre" name="titre" required="required" placeholder="Titre du produit.." value="<% if (productToEdit != null) { out.println(productToEdit.getTitre()); } %>">
	  </div>
	  
	  <div class="mb-3">
	    <label for="prix" class="form-label">Prix</label>
	    <input type="text" class="form-control" id="prix" name="prix" required="required" placeholder="Prix du produit.." value="<% if (productToEdit != null) { out.println(productToEdit.getPrix()); } %>">
	  </div>
	  
	  <div class="mb-3">
	    <label for="prix" class="form-label">Quantite</label>
	    <input type="text" class="form-control" id="quantite" name="quantite" required="required" placeholder="Quantite du produit.." value="<% if (productToEdit != null) { out.println(productToEdit.getQuantityDispo()); } %>">
	  </div>
	  
	  <% if (productToEdit == null) { %>
	  <div class="mb-3">
	    <label for="prix" class="form-label">Image</label>
	    <input type="file" class="form-control" id="image" name="image" required="required" accept="image/*">
	  </div>
	  <% } %>
	  
	  <% if (productToEdit != null) { %>
	    <input type="text" id="image" name="image" hidden="hidden">
	  <% } %>
	  
	  <div class="mb-3">
	    <label for="categorie" class="form-label">Categorie</label>
	    <select class="form-select" name="categorie" required="required">
		    <% if (productToEdit == null) { %>
		    	<option value="" selected="selected" disabled="disabled">Categorie</option>
		    <% } %>
	    	<% for (Categorie categorie : categoriessList) { %>
	    		<option value="<%=categorie.getId() %>" <% if (productToEdit != null && categorie.getCategorie().equalsIgnoreCase(productToEdit.getCategorie().getCategorie())) { out.println("selected"); } %>>
	    			<%=categorie.getCategorie() %>
	    		</option>
	    	<% } %>
	    </select>
	  </div>
	  
	  <% if (productToEdit == null) { %>
	  	<button type="submit" class="btn btn-primary">Ajouter</button>
	  <% } %>
	  
	  <% if (productToEdit != null) { %>
	  	<button type="submit" class="btn btn-primary">Modifier</button>
	  <% } %>
	</form>
	</div>
	
	<div class="container">
		<div class="table-responsive">
		<table class="table">
		  <thead>
		    <tr>
		      <!-- <th scope="col">#</th> -->
		      <th scope="col">Image</th>
		      <th scope="col">Titre</th>
		      <th scope="col">Prix</th>
		      <th scope="col" title="Quantite Disponible">Q.D</th>
		      <th scope="col">Categorie</th>
		      <th scope="col">Actions</th>
		    </tr>
		  </thead>
		  <tbody>
		  <% for (Produit produit : productsList) { %>
		    <tr>
		      <%-- <th scope="row"><%=produit.getId() %></th> --%>
		      <td>
		      	<img src="images/products/<%=produit.getImageName() %>.webp" style="width: 65px; height: 65px; object-fit: cover;" />
		      </td>
		      <td><%=produit.getTitre() %></td>
		      <td><%=produit.getPrix() %></td>
		      <td><%=produit.getQuantityDispo() %></td>
		      <td><%=produit.getCategorie().getCategorie() %></td>
		      <td class="d-flex justify-content-center align-items-center">
	      		<a href="products?edit_id=<%=produit.getId() %>" class="text-warning">
	      			<i class="fa-solid fa-pen-to-square"></i>
	      		</a>
	      	
	      		<div class="m-1"></div>
	      	
	      		<form method="post" action="products" onsubmit="return confirm('Do you really want to delete the product?');">
	      			<input readonly hidden="hidden" name="delete_id" value="<%=produit.getId() %>" />
	      			<button class="btn text-danger"><i class="fa-solid fa-trash-can"></i></button>
	      		</form>
	      	</td>
		   </tr>
		   <% } %>
		  </tbody>
		</table>
		</div>
	</div>
		
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>