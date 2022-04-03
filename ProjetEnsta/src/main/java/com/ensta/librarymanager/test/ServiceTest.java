package com.ensta.librarymanager.test;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.utils.FillDatabase;

public class ServiceTest {
	public static void main(String[] args) throws Exception{
        FillDatabase.main(args);

        testLivre();
        testMembre();
        testEmprunt();
        
    }

	private static void testLivre() throws ServiceException {
		LivreService livreService = LivreServiceImpl.getInstance();

		List<Livre> listeLivres = livreService.getList();
		System.out.println("listeLivres" + listeLivres);
		
		listeLivres = livreService.getListDispo();
		System.out.println("listeLivres dispo" + listeLivres);
		
		Livre livre = livreService.getById(3);
		System.out.println("Livres by id :" + livre);

		int id = livreService.create("book of books", "writer of writers", "00000000");
		System.out.println("livre avec id cree :" + id);
		
        Livre livreTest = new Livre(2, "The art of programation", "Joseph", "555331");
        livreService.update(livreTest);
        livreService.delete(4);
        listeLivres = livreService.getList();
        System.out.println("listeLivres dispo" + listeLivres);
        int count = livreService.count();
        System.out.println("count" + count);
	}

	private static void testMembre() throws ServiceException{
		MembreService memberService = MembreServiceImpl.getInstance();

		List<Membre> membres = memberService.getList();
		System.out.println("listeMembres " + membres);
		membres = memberService.getListMembreEmpruntPossible();
		System.out.println("listeMembres " + membres);
        Membre mbr = memberService.getById(2);
        System.out.println("Membre " + mbr);
        memberService.create("Hamza", "Oudi", "hmz.oud@ensta-paris.fr", "+3307458659", "Allée des techniques avancées",Abonnement.VIP);
        
        Membre memberTest = new Membre(4, "aziz", "azouz", "az.az@ensta-paris.fr", "+3307269865", "Allée des techniques avancées",Abonnement.VIP);
        
        memberService.update(memberTest);
        
        memberService.delete(5);
        
        memberService.count();
		
	}

	private static void testEmprunt() throws ServiceException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();

		List<Emprunt> listeEmprunts = empruntService.getList();
		System.out.println("liste emprunts "+listeEmprunts);
		listeEmprunts = empruntService.getListCurrent();
		System.out.println("liste emprunts courants "+listeEmprunts);

		listeEmprunts = empruntService.getListCurrentByMembre(3);
		System.out.println("liste emprunts courants pour membre "+listeEmprunts);

		listeEmprunts = empruntService.getListCurrentByLivre(2);
		System.out.println("liste emprunts courants pour livre "+listeEmprunts);

		empruntService.getById(5);
		
		empruntService.create(1, 2, LocalDate.now());
		
		empruntService.returnBook(4);
		
		empruntService.count();
		listeEmprunts = empruntService.getList();
		System.out.println("liste emprunts "+listeEmprunts);
		boolean dispo = empruntService.isLivreDispo(4);
		System.out.println("livre est dispo "+dispo);
		
	}
}
