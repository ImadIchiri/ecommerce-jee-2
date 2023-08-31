<%@page import="com.ecommerce.Entity.Categorie"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
    	List<Categorie> listCategories = (List<Categorie>) request.getAttribute("CATEGORIES_LIST");
    	Categorie categorieToEdit =(Categorie) request.getAttribute("categorieToEdit");
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
	<h1>Categories</h1>
	
	<div class="container">
	
		<% if (categorieToEdit == null) { %>
			<form action="categories" method="post">
			  <div class="mb-3">
			    <label for="categorie" class="form-label">Categorie name:</label>
			    <input type="text" name="categorie" class="form-control" id="categorie" placeholder="ex: Watches" required="required">
			  </div>
			  <button type="submit" class="btn btn-primary">Ajouter</button>
			</form>
		<% } %>
	
		<% if (categorieToEdit != null) { %>
			<form action="categories" method="post">
			  <div class="mb-3">
			    <label for="categorie" class="form-label">Categorie name:</label>
			    <input type="text" name="id" value="<%=categorieToEdit.getId() %>" hidden readonly>
			    <input type="text" name="categorie" value="<%=categorieToEdit.getCategorie() %>" class="form-control" id="categorie" placeholder="ex: Watches" required="required">
			  </div>
			  <button type="submit" class="btn btn-primary">Modifier</button>
			</form>
		<% } %>
		
<table class="table w-75 mx-auto text-center">
  <thead>
    <tr>
      <th scope="col" class="w-25">#</th>
      <th scope="col" class="w-25">Nom</th>
      <th scope="col" class="w-25">Action</th>
      <th scope="col" class="w-25">Voire Pooduits</th>
    </tr>
  </thead>
  <tbody>
	<% for (Categorie categorie : listCategories) { %>
	    <tr>
	      <th scope="row"><%=categorie.getId() %></th>
	      <th><%=categorie.getCategorie() %></th>
	      <th class="d-flex justify-content-center align-items-center">
	      		<a href="categories?edit_id=<%=categorie.getId() %>" class="text-warning">
	      			<i class="fa-solid fa-pen-to-square"></i>
	      		</a>
	      	
	      	<div class="m-1"></div>
	      	
	      	<form method="post" action="categories" onsubmit="return confirm('Do you really want to delete the category?');">
	      		<input readonly hidden name="delete_id" value="<%=categorie.getId() %>" />
	      		<button class="btn text-danger"><i class="fa-solid fa-trash-can"></i></button>
	      	</form>
	      </th>
	      <th>
	      	<a href="products?category=<%=categorie.getCategorie() %>" class="fs-6">Tous Les Produits</a>
	      </th>
	    </tr>
	<% } %>
  </tbody>
</table>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>