package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao {

	private static final String SELECT_ALL = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
	private static final String SELECT_ONE = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id=?;";
	private static final String CREATE = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE = "UPDATE membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?;";
	private static final String DELETE = "DELETE FROM membre WHERE id=?;";
	private static final String COUNT = "SELECT COUNT(id) AS count FROM membre;";

	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<Membre>();
		PreparedStatement stmt = null;
		Connection con = null;
		ResultSet rst = null;
		try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_ALL);
            rst = stmt.executeQuery();
            while(rst.next()){
            	membres.add(new Membre(rst.getInt("id"), rst.getString("nom"), rst.getString("prenom"), rst.getString("email"), rst.getString("telephone"), rst.getString("adresse"), Abonnement.valueOf(rst.getString("abonnement"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return membres;
		
	}

	@Override
	public Membre getById(int id) throws DaoException {
		Membre membre = new Membre();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(SELECT_ONE);
            stmt.setInt(1, id);
            rst = stmt.executeQuery();
            if(rst.next()){
            	membre = new Membre(rst.getInt("id"), rst.getString("nom"), rst.getString("prenom"), rst.getString("email"), rst.getString("telephone"), rst.getString("adresse"), Abonnement.valueOf(rst.getString("abonnement")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return membre;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws DaoException {
		int id = -1;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, adresse);
            stmt.setString(4, email);
            stmt.setString(5, telephone);
            stmt.setString(6, abonnement.toString());
            stmt.executeUpdate();
            rst = stmt.getGeneratedKeys();
            if(rst.next()){
                id = rst.getInt(1);
            }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return id;
	}

	@Override
	public void update(Membre membre) throws DaoException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(UPDATE);
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getPrenom());
            stmt.setString(3, membre.getAdresse());
            stmt.setString(4, membre.getEmail());
            stmt.setString(5, membre.getTelephone());
            stmt.setString(6, membre.getAbonnement().toString());
            stmt.setInt(7, membre.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void delete(int id) throws DaoException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public int count() throws DaoException {
		int totalMembers = -1;
        PreparedStatement stmt = null;
        Connection con = null;
        ResultSet rst = null;
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(COUNT);
            rst = stmt.executeQuery();
            if(rst.next()){
                totalMembers = rst.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalMembers;
 
	}
	
	private static MembreDaoImpl instance;
	public static MembreDaoImpl getInstance() {
		if (instance == null) instance = new MembreDaoImpl();
		return instance;
	}
	
}
