package com.jeux.jungle.bo;

public class Joueur {
	private int ID;
	private String NOM;
	private String PASSWORD;
	public Joueur(String NOM,String PASSWORD){
      this.NOM=NOM;
      this.PASSWORD=PASSWORD;
}
	public String getnom() {
		return NOM;
	}
	public String getpass() {
		return PASSWORD;
	}
	public String setnom(String NOM) {
		return this.NOM=NOM;
	}public String setpass(String PASSWORD) {
		return this.PASSWORD=PASSWORD;
	}
	    
}