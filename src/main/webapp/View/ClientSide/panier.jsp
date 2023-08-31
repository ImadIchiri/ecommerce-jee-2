<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ecommerce.Entity.LignePanier"%>
<%@page import="com.ecommerce.Entity.Panier"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
    	List<LignePanier> listLignePanier =(List<LignePanier>) session.getAttribute("LIST_LIGNE_PANIER");
    %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<!-- FontAwsome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<!-- Main CSS -->
	<link rel="stylesheet" href="View/ClientSide/homePage.css" />
</head>
<body>

	<%@include file="header.jsp"%>

    <nav class="menu py-3" style="background: linear-gradient(to bottom, #800080, #4b0082);">
        <div class="container">
            <div id="menu-links" class="menu-links">
                <a href="#" class="text-white">Home</a>
                <a href="#" class="text-white">Products</a>
                <a href="#" class="text-white">Categories</a>
                <a href="#" class="text-white">About Us</a>
                <a href="#" class="text-white">Contact</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-5">
        <h1 class="mb-4">Votre Panier</h1>
        <div class="row">
          
          <div class="col-md-8">
            <table class="table">
              <thead>
                  <tr>
                      <th>Produit</th>
                      <th>Titre</th>
                      <th>Prix</th>
                      <th>Quantite</th>
                      <th>Total</th>
                      <th></th>
                  </tr>
              </thead>
              <tbody>
              
              <% if (listLignePanier != null) { 
              		 for(LignePanier lignePanier : listLignePanier) { %>
                  <tr>
                      <td><img src="images/products/<%=lignePanier.getProduit().getImageName() %>.webp" alt="Product 1" class="img-fluid" style="max-width: 100px;"></td>
                      <td><%=lignePanier.getProduit().getTitre() %></td>
                      <td>$<%=lignePanier.getProduit().getPrix() %></td>
                      <td><input type="number" class="form-control" value="<%=lignePanier.getQuantite() %>"></td>
                      <td>$<%=lignePanier.getProduit().getPrix() *  lignePanier.getQuantite()%></td>
                      <td>
                      	<form action="panier" method="post">
                      	<input type="text" hidden readonly name="delete_id" value="<%=lignePanier.getId() %>" />
	                      	<button>
	                      		<i class="fa-solid fa-trash-can"></i>
	                      	</button>
                      	</form>
                      </td>
                  </tr>
              <% }
              } %>
              	
              </tbody>
            </table>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <form class="card-body" method="post" action="commande">
                        <h4 class="card-title">Total</h4>
                        <p class="card-text">Total de produits: <%=Panier.getListLignePanier().size() %></p>
                        <p class="card-text">Total: $<%=Panier.getTotalPrice() %></p>
                        <button class="btn btn-primary btn-block " style="background: linear-gradient(to bottom, #800080, #4b0082)">Proceed to Checkout</button>
                    </form>
                </div>
            </div>
            </div>
          </div>
</body>
</html>