<%
    	String productName = (String) request.getAttribute("productName");
%>

<header class="header bg-light py-3">
	<div class="container main_header_container">
		<img src="images/websiteImages/logo.png" />
            
		<form action="homepage" method="get">
			<input class="form-control" name="productName" placeholder="Product Name" value="<% if (productName != null) { out.print(productName); } %>" />
			<button class="btn text-white ml-1" style="background: linear-gradient(to bottom, #800080, #4b0082);">
				<i class="fa-solid fa-magnifying-glass"></i>
            </button>
		</form>
            
		<div class="right_btns">
			<a href="panier">
				<i class="fa-solid fa-cart-shopping fa-xl" style="color: #800080;"></i>
           	</a>
            <a href="">
            	<i class="fa-solid fa-user fa-xl" style="color: #800080;"></i>
            </a>
		</div>
	</div>
</header>