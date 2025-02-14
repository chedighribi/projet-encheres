package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.mapper.UtilisateurRowMapper;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private final String FIND_BY_PSEUDO = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit, no_adresse FROM utilisateurs WHERE pseudo = :pseudo";
	private final String FIND_BY_EMAIL = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit, no_adresse FROM utilisateurs WHERE email = :email";
	private final String FIND_ALL = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit, no_adresse FROM utilisateurs";
	private final String READ_PASSWORD_BY_PSEUDO = "SELECT mot_de_passe FROM utilisateurs WHERE pseudo = :pseudo";
	private final String INSERT = "INSERT INTO utilisateurs (pseudo, email, nom, prenom, administrateur, telephone, credit, mot_de_passe, no_adresse) VALUES (:pseudo, :email, :nom, :prenom, :administrateur, :telephone, :credit, :mot_de_passe, :no_adresse)";
	private final String UPDATE = "UPDATE utilisateurs SET email = :email, nom = :nom, prenom = :prenom, administrateur = :administrateur, telephone = :telephone, credit = :credit, no_adresse = :no_adresse WHERE pseudo = :pseudo";
	private final String DELETE = "DELETE utilisateurs WHERE pseudo = :pseudo AND administrateur = 0";
	private final String COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM utilisateurs WHERE pseudo = :pseudo";
	private final String COUNT_EMAIL = "SELECT COUNT(email) FROM utilisateurs WHERE email = :email";
	private final String COUNT_NEW_EMAIL = "SELECT COUNT(email) FROM utilisateurs WHERE email = :email AND pseudo <> :pseudo";
	private final String UPDATE_CREDIT = "UPDATE utilisateurs SET credit = :credit WHERE pseudo = :pseudo";
	private final String UPDATE_PASSWORD = "UPDATE utilisateurs SET mot_de_passe = :mot_de_passe WHERE pseudo = :pseudo";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private MapSqlParameterSource loadNamedParameters(Utilisateur utilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("administrateur", utilisateur.isAdministrateur());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("credit", 10);
		namedParameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
		namedParameters.addValue("no_adresse", utilisateur.getAdresse().getNoAdresse());
		return namedParameters;
	}
	
	@Override
	public void create(Utilisateur utilisateur) {
		System.out.println(" DAO utilisateur create");
		System.out.println(utilisateur);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource namedParameters = loadNamedParameters(utilisateur);

		jdbcTemplate.update(INSERT, namedParameters, keyHolder);
	}
	
	@Override
	public void update(Utilisateur utilisateur) {
		System.out.println(" DAO utilisateur update");
		System.out.println(utilisateur);
		
		MapSqlParameterSource namedParameters = loadNamedParameters(utilisateur);

		jdbcTemplate.update(UPDATE, namedParameters);
	}
	
	@Override
	public void delete(Utilisateur utilisateur) {
		System.out.println(" DAO utilisateur delete");
		System.out.println(utilisateur);
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		jdbcTemplate.update(DELETE, namedParameters);
	}

	// need to check if we manage the id and add read with pseudo
	@Override
	public Utilisateur findByPseudo(String pseudo) {
		System.out.println("DAO utilisateur findByPseudo : " + pseudo);
		Utilisateur utilisateur = null;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		try {
			utilisateur = jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters,
					new BeanPropertyRowMapper<>(Utilisateur.class));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}
	
	@Override
	public Utilisateur ReadByPseudo(String pseudo) {
		System.out.println("DAO utilisateur ReadByPseudo : " + pseudo);
		Utilisateur utilisateur = null;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		try {
			utilisateur = jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters,
					new UtilisateurRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}

	@Override
	public Utilisateur read(String email) {
		System.out.println("DAO utilisateur read " + email);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL, namedParameters,
					new UtilisateurRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String readPasswordByPseudo(String pseudo) {
		System.out.println("DAO utilisateur readPasswordByPseudo " + pseudo);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		try {
			String temp =jdbcTemplate.queryForObject(READ_PASSWORD_BY_PSEUDO, namedParameters, String.class); 
			System.out.println(READ_PASSWORD_BY_PSEUDO);
			System.out.println(" retour requete : " + temp );
			return temp;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	
	
	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	@Override
	public int uniquePseudo(String pseudo) {
		System.out.println("DAO utilisateur findpseudo");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(COUNT_PSEUDO, namedParameters, Integer.class);
	}
	
	@Override
	public int uniqueEmail(String email) {
		System.out.println("DAO utilisateur uniqueEmail");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		return jdbcTemplate.queryForObject(COUNT_EMAIL, namedParameters, Integer.class);
	}
	
	@Override
	public int uniqueNewEmail(String email, String pseudo) {
		System.out.println("DAO utilisateur uniqueNewEmail");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		namedParameters.addValue("email", email);
		return jdbcTemplate.queryForObject(COUNT_NEW_EMAIL, namedParameters, Integer.class);
	}

	@Override
	public void updateCredit(String pseudo, int newCredit) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("pseudo", pseudo);
	    namedParameters.addValue("credit", newCredit);

		 jdbcTemplate.update(UPDATE_CREDIT, namedParameters);
	}
	
	@Override
	public void updatePassword(String pseudo, String newPassword) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("pseudo", pseudo);
	    namedParameters.addValue("mot_de_passe", newPassword);

		 jdbcTemplate.update(UPDATE_PASSWORD, namedParameters);
	}
	
}
