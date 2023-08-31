package com.ecommerce.Entity;

import java.util.*;

public class Commande {
	private int id;
	private String dateCommande;
	private User user;
	//private List<LigneCommande> listeLigneCommande;
	//list de ligne de commande ?
	
	public Commande() {}
	
	public Commande(User user, String dateCommande) {
		this.user = user;
		this.dateCommande = dateCommande;
	}

	public Commande(int id, User user, String dateCommande) {
		this.id = id;
		this.user = user;
		this.dateCommande = dateCommande;
	}
/*
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}
	
	public void insertIntoListLigneCommande(LigneCommande ligneCommande) {
		listeLigneCommande.add(ligneCommande);
		
	}
	*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDateCommande() {
		return dateCommande;
	}
	public void setDateCommande(String dateCommande) {
		this.dateCommande = dateCommande;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}