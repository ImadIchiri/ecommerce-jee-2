<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
      integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
</head>
<body>
<header class="header bg-light py-3">
        <div class="container main_header_container">
            <img src="images/websiteImages/logo.png" />
            
            <form action="homepage" method="get">
            	<input class="form-control" name="productName" placeholder="Product Name"/>
            	<button class="btn text-white ml-1" style="background: linear-gradient(to bottom, #800080, #4b0082);">
            		<i class="fa-solid fa-magnifying-glass"></i>
            	</button>
            </form>
            
            <div class="right_btns">
            	<a href="">
            		<i class="fa-solid fa-cart-shopping fa-xl" style="color: #800080;"></i>
            	</a>
            	<a href="">
            		<i class="fa-solid fa-user fa-xl" style="color: #800080;"></i>
            	</a>
            </div>
        </div>
    </header>
    
    
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
                  <tr>
                      <td><img src="img/jeans-category.png" alt="Product 1" class="img-fluid" style="max-width: 100px;"></td>
                      <td>Product 1 - Lorem ipsum dolor sit amet, consectetur adipiscing elit.</td>
                      <td>$25.00</td>
                      <td><input type="number" class="form-control" value="2"></td>
                      <td>$50.00</td>
                      <td><a><i class="fa-solid fa-trash-can"></i></a></td>
                  </tr>
                  <tr>
                      <td><img src="img/watches-category-1.jpg" alt="Product 2" class="img-fluid" style="max-width: 100px;"></td>
                      <td>Product 2 - Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</td>
                      <td>$30.00</td>
                      <td><input type="number" class="form-control" value="1"></td>
                      <td>$30.00</td>
                      <td><a><i class="fa-solid fa-trash-can"></i></a></td>
                  </tr>
              </tbody>
            </table>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Total</h4>
                        <p class="card-text">Total de produits: 3</p>
                        <p class="card-text">Total: $88.00</p>
                        <button class="btn btn-primary btn-block " style="background: linear-gradient(to bottom, #800080, #4b0082)">Proceed to Checkout</button>
                    </div>
                </div>
            </div>
            </div>
          </div>
</body>
</html>