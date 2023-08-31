package com.ecommerce.adminDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.DAO.DataBaseConnection;
import com.ecommerce.Entity.Categorie;
import com.ecommerce.Entity.Produit;
import com.ecommerce.userDAO.UserDAO;

public class AdminDAO {
    // Méthode pour ajouter une nouvelle catégorie
    public static void addCategory(Categorie categorie) {
    	
    	Optional<Categorie> optCategorie = UserDAO.getCategorieByName(categorie.getCategorie());
    	
    	if (optCategorie.isEmpty()) { 	
	        try (Connection connection = DataBaseConnection.connectToDB()) {
	            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Categorie (categorie) VALUES (?)");
	            preparedStatement.setString(1, categorie.getCategorie().toLowerCase());
	            
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    // Méthode pour mettre à jour une catégorie
    public static void updateCategory(Categorie categorie) {// signature de lan methode 
        try (Connection connection = DataBaseConnection.connectToDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Categorie SET categorie = ? WHERE id = ?");
            preparedStatement.setString(1, categorie.getCategorie());
            preparedStatement.setInt(2, categorie.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour supprimer une catégorie
    public static void deleteCategory(int categoryId) {
        try (Connection connection = DataBaseConnection.connectToDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Categorie WHERE id = ?");
            preparedStatement.setInt(1, categoryId);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Méthode pour ajouter un produit
    public static void addProduct(Produit produit) {
    try (Connection connection = DataBaseConnection.connectToDB()) {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Produit (titre, prix, quantiteDispo, image, idCategorie) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, produit.getTitre());
        preparedStatement.setDouble(2, produit.getPrix());
        preparedStatement.setInt(3, produit.getQuantityDispo());
        preparedStatement.setString(4, produit.getImageName());
        preparedStatement.setInt(5, produit.getCategorie().getId());
        
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Méthode pour mettre à jour un produit
    public static void updateProduct(Produit produit) {
    try (Connection connection = DataBaseConnection.connectToDB()) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Produit SET titre = ?, prix = ?, quantiteDispo = ?, image = ?, idCategorie = ? WHERE id = ?");
        preparedStatement.setString(1, produit.getTitre());
        preparedStatement.setDouble(2, produit.getPrix());
        preparedStatement.setInt(3, produit.getQuantityDispo());
        preparedStatement.setString(4, produit.getImageName());
        preparedStatement.setInt(5, produit.getCategorie().getId());
        preparedStatement.setInt(6, produit.getId());
        
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Méthode pour supprimer un produit
    public static void deleteProduct(int productId) {
    try (Connection connection = DataBaseConnection.connectToDB()) {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Produit WHERE id = ?");
        preparedStatement.setInt(1, productId);
        
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Méthode pour mettre à jour la quantité d'un produit
    public static void updateProductQuantity(int productId, int newQuantity) {
    try (Connection connection = DataBaseConnection.connectToDB()) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Produit SET quantiteDispo = ? WHERE id = ?");
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, productId);
        
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public static void decreaseQuantity(Produit produit, int quantity) {
        try (Connection connection = DataBaseConnection.connectToDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Produit SET quantiteDispo = (quantiteDispo - ?) WHERE id = ?");
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, produit.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }


    // Méthode pour recevoire un produit par son ID
    public static Optional<Produit> getProductById(int productId) {
	Produit produit = null;
	
	try (Connection connection = DataBaseConnection.connectToDB()) {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Produit WHERE id = ?");
        preparedStatement.setInt(1, productId);
        
         ResultSet resultSet = preparedStatement.executeQuery();
         
         while (resultSet.next()) {
        	 String titre = resultSet.getString("titre");
        	 double prix = resultSet.getDouble("prix");
        	 int quantiteDispo = resultSet.getInt("quantiteDispo");
        	 String image = resultSet.getString("image");
        	 int idCategorie = resultSet.getInt("idCategorie");
        	 
        	 Optional<Categorie> optCategory = UserDAO.getCategorieById(idCategorie);
        	 
        	 produit = new Produit(productId, titre, prix, quantiteDispo, image, optCategory.get());
         }
    } catch (SQLException e) {
        e.printStackTrace();
    }
	
	return Optional.ofNullable(produit);
}

    // Méthode pour récupérer la liste des commandes passées par les utilisateurs (simplifié ici)
    public static List<String> getAllOrders() {
    List<String> orders = new ArrayList<>(); 
    
    try (Connection connection = DataBaseConnection.connectToDB()) {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Commande");

        ResultSet resultSet = preparedStatement.executeQuery(); // film 

        while (resultSet.next()) {
            String orderDetails = resultSet.getString("details");
            orders.add(orderDetails);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return orders;
}

    // Méthode pour selectionner le dérnier Produit inseré (Snas Image) 
    public static Optional<Produit> getProductToSetImage() {
	Produit produit = null;
	
	int id, quantityDispo;
	Categorie categorie; 
	String titre, imageName = "";
	double prix;
		
     try (Connection connection = DataBaseConnection.connectToDB();) {
    	 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Produit WHERE id in (SELECT MAX(id) FROM Produit)");

    	 ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
        	 id = resultSet.getInt("id");
        	 quantityDispo = resultSet.getInt("quantiteDispo");
        	 titre = resultSet.getString("titre");
        	 prix = resultSet.getDouble("prix");
        	 imageName = "";
        	 
        	 int categoryId = resultSet.getInt("idCategorie");
        	 categorie = UserDAO.getCategorieById(categoryId).get();
        	 
        	 produit = new Produit(id, titre, prix, quantityDispo, imageName, categorie);
         }
     } catch (SQLException e) {
    	 e.printStackTrace();
     }
	
     return Optional.ofNullable(produit);
}
}
