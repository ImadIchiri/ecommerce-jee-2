package com.ecommerce.Entity;

public class User {
	private int id;
	private String nom;
	private String prenom;
	private String dateNaissance;
	private Role role;
	private String email;
	private String password;
	
	public User() {}
	
	public User(String nom, String prenom, String dateNaissance, Role role, String email, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.role = role;
		this.email = email;
		this.password = password;
	}
	
	public User(int id, String nom, String prenom, String dateNaissance, Role role, String email, String password) {
		this.id= id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.role = role;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}