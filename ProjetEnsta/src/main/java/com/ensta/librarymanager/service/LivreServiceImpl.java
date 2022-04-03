package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;

public class LivreServiceImpl implements LivreService {
	private static LivreServiceImpl instance;
    private LivreServiceImpl(){};
    public static LivreServiceImpl getInstance(){
        if (instance == null)   instance = new LivreServiceImpl();
        return instance;
    }
	@Override
	public List<Livre> getList() throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();
        
		try{
			livres = livreDao.getList();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livres;
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();
        
		try{
			livres = livreDao.getList();
			Iterator<Livre> iter = livres.listIterator();

			while (iter.hasNext()) {
				Livre livre = iter.next();
				if (!empruntService.isLivreDispo(livre.getId())) {
					iter.remove();
				}
			}
				

		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livres;
		
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        Livre livre = null;
		try{
			livre = livreDao.getById(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        int id = 0;
		if (titre == null) {
			throw new ServiceException("sans titre");
		}
		
		try{
			id = livreDao.create(titre, auteur, isbn);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return id;
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		if (livre.getTitre() == null) {
			throw new ServiceException("sans titre");
		}
		
		try{
			livreDao.update(livre);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void delete(int id) throws ServiceException {
		 LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

			try{
				livreDao.delete(id);
			} catch (DaoException e){
				System.out.println(e.getMessage());
			}
		
	}

	@Override
	public int count() throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        int count = 0;
        
		try{
			count = livreDao.count();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		return count;
	}

}
