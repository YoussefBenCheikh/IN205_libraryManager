package com.ensta.librarymanager.test;


import java.time.LocalDate;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.utils.FillDatabase;

public class ModeleTest {
	public static void main(String... args) throws Exception {
		FillDatabase.main(args);
        testLivre();
		testMembre();
		testEmprunt();
    }
	
	public static void testLivre(){
		Livre livre = new Livre(0, "LE LIVRE", "Taher Benjalloun", "ISBN who cares");
		System.out.println(livre);
	}
	
	public static void testMembre(){
		Membre membre = new Membre(0, "Red", "Remond", "Casa", "haha@hoho.com", "06060606", Abonnement.BASIC);
		System.out.println(membre);
	}
	
	public static void testEmprunt(){
		Membre membre = new Membre(0, "Red", "Remond", "Casa", "haha@hoho.com", "06060606", Abonnement.BASIC);
		Livre livre = new Livre(0, "LE LIVRE", "Taher Benjalloun", "ISBN who cares");
		LocalDate dateEmprunt = LocalDate.now();
		Emprunt emprunt = new Emprunt(0, livre, membre, dateEmprunt, dateEmprunt.plusDays(21));
		System.out.println(emprunt);
	}
}
