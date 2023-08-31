package com.ecommerce.clientDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ecommerce.DAO.DataBaseConnection;
import com.ecommerce.Entity.Categorie;
import com.ecommerce.Entity.Commande;
import com.ecommerce.Entity.LigneCommande;
import com.ecommerce.Entity.LignePanier;
import com.ecommerce.Entity.Panier;
import com.ecommerce.Entity.Produit;
import com.ecommerce.Entity.Role;
import com.ecommerce.Entity.User;
import com.ecommerce.userDAO.UserDAO;

public class ClientDAO {
	public static Produit getProductById(int productId) {
		Produit produit = null;
		
		int id, quantityDispo;
		Categorie categorie;
		String titre, imageName;
		double prix;

		try (Connection connection = DataBaseConnection.connectToDB();) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Produit WHERE id = ?");
	    	preparedStatement.setInt(1, productId);

	    	ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	        	id = resultSet.getInt("id");
	        	titre = resultSet.getString("titre");
	        	prix = resultSet.getDouble("prix");
	        	quantityDispo = resultSet.getInt("quantiteDispo");
	        	imageName = resultSet.getString("image");
	        	int idCategorie = resultSet.getInt("idCategorie");
	        	
	        	Optional<Categorie> optionalCategorie = UserDAO.getCategorieById(idCategorie);
	        	
	        	
	        	if (optionalCategorie.isPresent()) {
	        		categorie = optionalCategorie.get();
	        		produit = new Produit(id, titre, prix, quantityDispo, imageName, categorie);
	        	}
	        }
	     } catch (SQLException e) {
	    	 e.printStackTrace();
	     }
		
	     return produit;		
	}
	
	public static Optional<LignePanier> addProductToPanier(Panier panier, Produit produit, int quantite) {
		LignePanier lPanier = null;
		
		if (produit.getQuantityDispo() >= quantite) {
			lPanier = new LignePanier(produit, panier, quantite);
		}
		
		return Optional.ofNullable(lPanier);
	}
	
	//public affichePanier() {} we already have getListLignePanier()
	
	public static void addCommande(int idProduit, int quantite, User user, String dateCommande) {
		
		// i think we should do the same thing we have been did with the previous method
		Commande commande = new Commande(user, dateCommande);
		Produit produit = ClientDAO.getProductById(idProduit);
		LigneCommande lCommande = new LigneCommande(commande, produit, quantite);
		 try (Connection connection = DataBaseConnection.connectToDB();){
		        

		        PreparedStatement insertInToCmd = connection.prepareStatement("insert into Commande (date, user) values (?, ? ) ;");
		        User u = commande.getUser();

		        insertInToCmd.setString(1,commande.getDateCommande());
		        insertInToCmd.setInt(2,u.getId());
		        insertInToCmd.executeUpdate();
		        
		        PreparedStatement insertInToLigneCmd = connection.prepareStatement("insert into LigneCommande (idCommande, idProduit, quantite) values (?, ?, ?) ;");

		        insertInToLigneCmd.setInt(1, commande.getId());
		        insertInToLigneCmd.setInt(2, idProduit);
		        insertInToLigneCmd.setInt(3, quantite);
		        insertInToLigneCmd.executeUpdate();
		            
		        } catch(SQLException e) {
		        	e.printStackTrace();
		        }
	}
	
	public static Optional<Commande> createCommande(User user, String dateCommande) {
		Commande commande = null;
		
		try (Connection connection = DataBaseConnection.connectToDB();){
	        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Commande (user, date) VALUES (?, ?)");
	        preparedStatement.setInt(1, user.getId());
	        preparedStatement.setString(2, dateCommande);
	        
	        int insertCount = preparedStatement.executeUpdate();
	        
	        if (insertCount > 0) {
		        PreparedStatement selectCmdId = connection.prepareStatement("SELECT MAX(id) AS 'id' FROM Commande");
		        ResultSet resultSet = selectCmdId.executeQuery();
		        
		        if (resultSet.next()) {
		        	int id = resultSet.getInt("id");
		        	commande = new Commande(id, user, dateCommande);
		        }
	        }
		          
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(commande);
	}

	public static int createLigneCommande(Commande commande, Produit produit, int quantite) {
		int insertCount = 0;
		try (Connection connection = DataBaseConnection.connectToDB();){
	        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO LigneCommande (idCommande, idProduit, quantite) VALUES (?, ?, ?)");
	        preparedStatement.setInt(1, commande.getId());
	        preparedStatement.setInt(2, produit.getId());
	        preparedStatement.setInt(3, quantite);
	        
	        insertCount = preparedStatement.executeUpdate();
	        
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return insertCount;
	}
	
	public void removeProduitFromPanier(int idlLignePanier) {
	    List<LignePanier> listLignePanier = Panier.getListLignePanier();
	    
	    listLignePanier.removeIf(ligne -> ligne.getId() == idlLignePanier);
	}
	
}
	
	
	
	






