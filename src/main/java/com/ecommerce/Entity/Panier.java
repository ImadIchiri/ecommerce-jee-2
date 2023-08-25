package com.ecommerce.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Panier {
	private static int idCounter = 0;
	private int id;
	private static List<LignePanier> listLignePanier = new ArrayList<LignePanier>();

	public Panier() {
		this.id = idCounter;
		idCounter++;
	}
	
	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Panier.idCounter = idCounter;
	}

	public static void setListLignePanier(List<LignePanier> listLignePanier) {
		Panier.listLignePanier = listLignePanier;
	}

	public Panier(int id) {
		this.id = idCounter;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static void insertIntoListLignePanier(LignePanier lignePanier) {
		
		boolean isExist = listLignePanier
				.stream()
				.anyMatch(item -> item.getProduit().getId() == lignePanier.getProduit().getId());
		
		System.out.println("IS_EXIST ==> " + isExist);
		
		if (isExist) {	
			listLignePanier = listLignePanier.stream().map(item -> {
				if (item.getProduit().getId() == lignePanier.getProduit().getId()) {
					item.setQuantite(
							item.getQuantite() + lignePanier.getQuantite()
					);
				}
				
				return item;
			}).collect(Collectors.toList());
		}
		
		if (!isExist) {
			listLignePanier.add(lignePanier);
		}
	}
	
	public static void removeFromListLignePanier(int id) {
		listLignePanier.removeIf(item -> item.getId() == id);
	}
	
	public static List<LignePanier> getListLignePanier() {
		return listLignePanier;
	}
	
	public static double getTotalPrice() {
		// lp == LignePanie
		double total = 0;
		
		for (LignePanier lp : listLignePanier) {
			total += lp.getQuantite() * lp.getProduit().getPrix();
		};
		
		return total;
	}

	@Override
	public String toString() {
		return "Panier [id=" + id + "]";
	}
	
	
}