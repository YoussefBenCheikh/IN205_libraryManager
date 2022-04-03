package com.ensta.librarymanager.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.utils.FillDatabase;

public class DaoTest{
	public static void main(String[] args) throws Exception {
        FillDatabase.main(args);
        
        testMembre();
        testLivre();
        testEmprunt();
    }

	public static void testLivre() throws DaoException {
		LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> listeLivres = new ArrayList<Livre>();
        listeLivres = livreDao.getList();
        System.out.println("listeLivres" + listeLivres);
        Livre livreAvecID = new Livre();
        livreAvecID = livreDao.getById(3);
        System.out.println("\n Avec ID: " + livreAvecID);
        int idTest = livreDao.create("Learn to cook air", "Choumicha", "457869");
        System.out.println("\n New id initialized: " + idTest);
        Livre livreTest = new Livre(4, "Comedia", "EL Fed", "457869");
        livreDao.update(livreTest);
        listeLivres = livreDao.getList();
        System.out.println("\n livres mis à jour: " + listeLivres);
        livreDao.delete(1);
        listeLivres = livreDao.getList();
        System.out.println("\n livres mis à jour (suppression): " + listeLivres);
        int totalLivres = livreDao.count();
        System.out.println("\n total livres: " + totalLivres);
		
	}

	public static void testEmprunt() throws Exception {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        empruntList = empruntDao.getList();
        System.out.println("\n empruntList: " + empruntList);
        empruntList = empruntDao.getListCurrent();
        System.out.println("\n Current empruntList: " + empruntList);
        empruntList = empruntDao.getListCurrentByMembre(4);
        System.out.println("\n Current empruntList by membre: " + empruntList);
        empruntList = empruntDao.getListCurrentByLivre(3);
        System.out.println("\n Current empruntList by Livre: " + empruntList);
        Emprunt empruntTest = empruntDao.getById(7);
        System.out.println("\n emprunt: " + empruntTest);
        empruntDao.create(1, 7, LocalDate.now());
        empruntList = empruntDao.getList();
        System.out.println("\n Total : " + empruntTest);
        Livre bookTest = new Livre(1,"Comment planter de l'argent", "Joseph Avare", "783454");
        Membre memberTest = new Membre(1,"HILLARY", "MOLAT DAR", "hroo.molat@earth.org", "+3306754959", "Allée des techniques avancées", Abonnement.BASIC);
        empruntTest = new Emprunt(2, bookTest,memberTest, LocalDate.now(), LocalDate.now().plusDays(7l));
        empruntDao.update(empruntTest);
        empruntList = empruntDao.getList();
        System.out.println("\n Total: " + empruntList);
        int total = empruntDao.count();
        System.out.println("\n Total  " + total);
		
	}

	public static void testMembre() throws DaoException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
        System.out.println("\nTests de Membre\n");
        List<Membre> listeMembres = new ArrayList<Membre>();
        listeMembres = membreDao.getList();
        System.out.println("liste de membres" + listeMembres);
        Membre membreAvecID = new Membre();
        membreAvecID = membreDao.getById(1);
        System.out.println("\n avec ID: " + membreAvecID);
        int idMembre = membreDao.create("Saad", "Elfad", "Allée des techniques avancées", "libghiti@gisele.fr", "+3307984567", Abonnement.BASIC);
        System.out.println("\n New id initialized: " + idMembre);
        Membre membreTest = new Membre(10, "Foued", "Bougou", "libgha@ensta-paris.fr", "+3309456587", "Allée des techniques avancées", Abonnement.PREMIUM);
        membreDao.update(membreTest);
        listeMembres = membreDao.getList();
        System.out.println("\n Total list updated " + listeMembres);
        membreDao.delete(7);
        listeMembres = membreDao.getList();
        System.out.println("\n Total list updated " + listeMembres);
        int totalMembres = membreDao.count();
        System.out.println("\n Total members in current DB " + totalMembres);
		
	}
	
}
