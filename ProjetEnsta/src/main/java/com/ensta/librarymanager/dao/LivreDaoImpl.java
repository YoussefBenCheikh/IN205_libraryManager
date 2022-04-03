package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {
	
	private static final String SELECT_ALL = "SELECT id, titre, auteur, isbn FROM livre ";
    private static final String SELECT_ONE = "SELECT id, titre, auteur, isbn FROM livre WHERE id=?;";
    private static final String CREATE = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
	private static final String UPDATE = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM livre WHERE id=?;";
    private static final String COUNT= "SELECT count(*) AS count FROM livre";

	@Override
	public List<Livre> getList() throws DaoException {
		List<Livre> listLivres = new ArrayList<>();
		 try (Connection connection = ConnectionManager.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
	             ResultSet result = preparedStatement.executeQuery();) {
			 while (result.next()){
				 listLivres.add(new Livre(result.getInt("id"), result.getString("titre"), result.getString("auteur"), result.getString("isbn")));
	            }
		 } catch (SQLException e) {
         throw new DaoException("Erreur pendant le chargement de la liste de livres à partir de la base de données", e);
     }
		 return listLivres;
	}
	
	public ResultSet GetByIdStatement(PreparedStatement preparedStatement, int id) throws SQLException {
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

	@Override
	public Livre getById(int id) throws DaoException{
		Livre livre = new Livre();
        
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE);
            ResultSet result = GetByIdStatement(preparedStatement, id);){
            
            if (result.next()) {
            	livre = new Livre(result.getInt("id"), result.getString("titre"), result.getString("auteur"), result.getString("isbn"));
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur de chargement du livre avec id: " + id, e);
        }

        return livre;
    }

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		Connection connection = null;
		int id=-1;
	
		try {
			connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2, auteur);
			preparedStatement.setString(3, isbn);
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
        	 id = resultSet.getInt(1);
        	}
			preparedStatement.close();
			resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DaoException("Probleme lors de création du livre",e);
			}
			return id;
		
	}

	@Override
	public void update(Livre livre) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);) {
	            
				preparedStatement.setString(1, livre.getTitre());
	            preparedStatement.setString(2, livre.getAuteur());
	            preparedStatement.setString(3, livre.getIsbn());
	            preparedStatement.setInt(4, livre.getId());
	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
				throw new DaoException("Erreur lors de mise à jour du livre " + livre, e);
			}
		
	}

	@Override
	public void delete(int id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
	            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);) {
	            preparedStatement.setInt(1, id);
	            preparedStatement.executeUpdate();
	            
			} catch (SQLException e) {
				throw new DaoException("Erreur lors de la suppressiondu livre à id:" + id, e);
			}
		
	}

	@Override
	public int count() throws DaoException {
		int nombreLivres = -1;

        try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT);
             ResultSet result = preparedStatement.executeQuery();) {

            if (result.next()) {
                nombreLivres = result.getInt(1);
                
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du compte du nombre des livres", e);
        } 
        
        return nombreLivres;
	}
	
	private static LivreDaoImpl instance;
	public static LivreDaoImpl getInstance() {
		if (instance == null) instance = new LivreDaoImpl();
		return instance;
	}

}
