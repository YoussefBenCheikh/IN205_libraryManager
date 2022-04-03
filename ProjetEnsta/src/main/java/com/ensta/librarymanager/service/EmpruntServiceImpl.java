package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;

public class EmpruntServiceImpl implements EmpruntService {

	private static EmpruntServiceImpl instance;
    private EmpruntServiceImpl(){};
    public static EmpruntServiceImpl getInstance(){
        if (instance == null)   instance = new EmpruntServiceImpl();
        return instance;
    }
    
	@Override
	public List<Emprunt> getList() throws ServiceException {
		List<Emprunt> tousEmprunts = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
        	tousEmprunts = empruntDao.getList();
        } catch (Exception e) {
            throw new ServiceException("peut pas recuperer la liste des emprunts\n", e);
        }
        return tousEmprunts;
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
        	empruntList = empruntDao.getListCurrent();

        } catch (Exception e) {
            throw new ServiceException("peut pas recuperer la liste des emprunts courants!\n", e);
        }

        return empruntList;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		List<Emprunt> currentList = new ArrayList<Emprunt>();
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            currentList = empruntDao.getListCurrentByMembre(idMembre); 
        } catch (Exception e) {
            throw new ServiceException("peut pas recuperer la liste des emprunts courants pour membre!\n", e);
        }

        return currentList;
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
        	empruntList = empruntDao.getListCurrentByLivre(idLivre);

 
        } catch (Exception e) {
            throw new ServiceException("peut pas recuperer la liste des emprunts courants pour livre!\n", e);
        }

        return empruntList;
		
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		Emprunt chosenEmprunt = new Emprunt();
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            chosenEmprunt = empruntDao.getById(id);

        } catch (Exception e) {
            throw new ServiceException("peut pas recuperer l'emprunt par ID!\n", e);
        }
        return chosenEmprunt;
		
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
        	empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (Exception e) {
            throw new ServiceException("peut pas creer!\n", e);
        }
		
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
        	Emprunt miseAjour = empruntDao.getById(id);
        	miseAjour.setDateRetour(LocalDate.now());
        	empruntDao.update(miseAjour);
            
        } catch (Exception e) {
            throw new ServiceException("peut pas retourner le livre!\n", e);
        }
		
	}

	@Override
	public int count() throws ServiceException {
		int total = -1;
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            total = empruntDao.count();
            
        } catch (Exception e) {
            throw new ServiceException("peut pas compter!\n", e);
        }
        return total;
		
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		boolean disponible = false;
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            disponible = empruntDao.getListCurrentByLivre(idLivre).isEmpty();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponible;
		
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		boolean possible = false;
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
        	possible = empruntDao.getListCurrentByMembre(membre.getId()).isEmpty();
           
        } catch (Exception e) {
            e.printStackTrace();
        }

        return possible;
	}

}
