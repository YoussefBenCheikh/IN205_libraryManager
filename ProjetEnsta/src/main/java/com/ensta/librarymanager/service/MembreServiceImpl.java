package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;

public class MembreServiceImpl implements MembreService {
	
	private static MembreServiceImpl instance;
    private MembreServiceImpl(){};
    public static MembreServiceImpl getInstance(){
        if (instance == null)   instance = new MembreServiceImpl();
        return instance;
    }
    
	@Override
	public List<Membre> getList() throws ServiceException {
		List<Membre> list = new ArrayList<Membre>();
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
        	list = membreDao.getList();

        } catch (Exception e) {
            throw new ServiceException("peut pas obtenir la liste des membres!\n", e);
        }

        return list;
		
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		List<Membre> membersEmpruntDispo = new ArrayList<Membre>();
        List<Membre> members = new ArrayList<Membre>();
        MembreDao membreDao = MembreDaoImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            members = membreDao.getList();
            for (int i = 0; i < members.size(); i++){
                if (empruntService.isEmpruntPossible(members.get(i))){
                	membersEmpruntDispo.add(members.get(i));
                }
            }
           
        } catch (Exception e) {
            throw new ServiceException("peut pas obtenir la liste des emprunts possibles pour le membre!!\n", e);
        }
        return membersEmpruntDispo;	
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		Membre choisi = new Membre();
		MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
        	choisi = membreDao.getById(id);
         
        } catch (Exception e) {
            throw new ServiceException("peut pas obtenir le membre!\n", e);
        }

        return choisi;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement subscription)
			throws ServiceException {
		int id = -1;
		MembreDao memberDao = MembreDaoImpl.getInstance();

        try {
            if (nom == null || prenom == null || nom == "" || prenom == ""){
                throw new ServiceException("Prenom ou nom est vide");
            } else{
                nom = nom.toUpperCase();
                
                id = memberDao.create(nom, prenom, adresse, email, telephone, subscription);
                
            }
        } catch (Exception e) {
            throw new ServiceException("peut pas creer membre!\n", e);
        }


        return id;
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		MembreDao memberDao = MembreDaoImpl.getInstance();

        try {
            if(membre.getPrenom() == null || membre.getNom() == null || membre.getPrenom() == "" || membre.getNom() == ""){
                throw new ServiceException("Prenom ou nom est vide");
            } else{
                membre.setNom(membre.getNom().toUpperCase());
                memberDao.update(membre);

               
            }
        } catch (Exception e) {
            throw new ServiceException("peut pas mettre le membre à jour\n", e);
        }
		
	}

	@Override
	public void delete(int id) throws ServiceException {
		MembreDao memberDao = MembreDaoImpl.getInstance();

        try {
            memberDao.delete(id);
           
        } catch (Exception e) {
            throw new ServiceException("peut pas supprimer le membre à id:"+id+"\n", e);
        }
		
	}

	@Override
	public int count() throws ServiceException {
		int total = -1;
		MembreDao memberDao = MembreDaoImpl.getInstance();

        try {
            total = memberDao.count();
        } catch (Exception e) {
            throw new ServiceException("peut pas compter!\n", e);
        }

        return total;
	}

}
