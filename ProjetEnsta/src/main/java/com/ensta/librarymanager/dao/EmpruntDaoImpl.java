package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {
	
	private static final String SELECT_ALL = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String SELECT_NotReturned = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private static final String SELECT_NotReturnedForMember = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String SELECT_NotReturnedForBook = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String SELECT_ONE = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private static final String CREATE = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String UPDATE = "UPDATE Emprunt SET idMembre=?, idLivre=?,dateEmprunt=?, dateRetour=? WHERE id=?;";
	private static final String COUNT = "SELECT COUNT(*) AS count FROM emprunt WHERE idMembre IN (SELECT id FROM membre) and idLivre IN (SELECT id FROM livre);";
    

	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		PreparedStatement stmt = null;
		Connection con = null;
		ResultSet rst = null;
		try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_ALL);
            rst = stmt.executeQuery();
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(rst.next()){
            	emprunts.add(new Emprunt(rst.getInt("id"), livreDao.getById(rst.getInt("idLivre")), membreDao.getById(rst.getInt("idMembre")), rst.getDate("dateEmprunt").toLocalDate(), rst.getDate("dateRetour") == null ? null : rst.getDate("dateRetour").toLocalDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return emprunts;
	
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		List<Emprunt> empruntsCourants = new ArrayList<Emprunt>();
		Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_NotReturned);
            rst = stmt.executeQuery();
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(rst.next()){
            	empruntsCourants.add(new Emprunt(rst.getInt("id"), livreDao.getById(rst.getInt("idLivre")), membreDao.getById(rst.getInt("idMembre")), rst.getDate("dateEmprunt").toLocalDate(), rst.getDate("dateRetour") == null ? null : rst.getDate("dateRetour").toLocalDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return empruntsCourants;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		List<Emprunt> empruntsCourantsPourMembre = new ArrayList<Emprunt>();
		Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_NotReturnedForMember);
            stmt.setInt(1, idMembre);
            rst = stmt.executeQuery();
           
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(rst.next()){
            	empruntsCourantsPourMembre.add(new Emprunt(rst.getInt("id"), livreDao.getById(rst.getInt("idLivre")), membreDao.getById(rst.getInt("idMembre")), rst.getDate("dateEmprunt").toLocalDate(), rst.getDate("dateRetour") == null ? null : rst.getDate("dateRetour").toLocalDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return empruntsCourantsPourMembre;	
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		List<Emprunt> empruntsCourantsPourLivre = new ArrayList<Emprunt>();
		Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_NotReturnedForBook);
            stmt.setInt(1, idLivre);
            rst = stmt.executeQuery();
            
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(rst.next()){
            	empruntsCourantsPourLivre.add(new Emprunt(rst.getInt("id"), livreDao.getById(rst.getInt("idLivre")), membreDao.getById(rst.getInt("idMembre")), rst.getDate("dateEmprunt").toLocalDate(), rst.getDate("dateRetour") == null ? null : rst.getDate("dateRetour").toLocalDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return empruntsCourantsPourLivre;
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		Emprunt emprunt = new Emprunt();
		Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_ONE);
            stmt.setInt(1, id);
            rst = stmt.executeQuery();
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            if(rst.next()){
            	emprunt = new Emprunt(rst.getInt("id"), livreDao.getById(rst.getInt("idLivre")), membreDao.getById(rst.getInt("idMembre")), rst.getDate("dateEmprunt").toLocalDate(), rst.getDate("dateRetour") == null ? null : rst.getDate("dateRetour").toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		 Connection con = null;
		 PreparedStatement stmt = null;
		 
	        try {
	            con = ConnectionManager.getConnection();
	            stmt = con.prepareStatement(CREATE);
	            stmt.setInt(1, idMembre);
	            stmt.setInt(2, idLivre);
	            stmt.setString(3, dateEmprunt + "");
	            stmt.setDate(4, null);
	            stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(UPDATE);
            stmt.setInt(1, emprunt.getMembre().getId());
            stmt.setInt(2, emprunt.getLivre().getId());
            stmt.setString(3, emprunt.getDateEmprunt()+"");
            stmt.setString(4, emprunt.getDateRetour()+"");
            stmt.setInt(5, emprunt.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public int count() throws DaoException {
		int totalEmprunts = -1;
		Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(COUNT);
            rst = stmt.executeQuery();
            if(rst.next()){
            	totalEmprunts = rst.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		return totalEmprunts;
	}
	
	private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl(){};
    public static EmpruntDaoImpl getInstance(){
        if (instance == null)   instance = new EmpruntDaoImpl();
        return instance;
    }


}
