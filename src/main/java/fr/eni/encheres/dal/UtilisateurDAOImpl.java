package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.mapper.UtilisateurRowMapper;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private final String FIND_BY_PSEUDO = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit, no_adresse FROM utilisateurs WHERE pseudo = :pseudo";
	private final String FIND_BY_EMAIL = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit, no_adresse FROM utilisateurs WHERE email = :email";
	private final String FIND_ALL = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit, no_adresse FROM utilisateurs";
	private final String INSERT = "INSERT INTO utilisateurs (pseudo, email, nom, prenom, administrateur, telephone, credit, mot_de_passe, no_adresse) VALUES (:pseudo, :email, :nom, :prenom, :administrateur, :telephone, :credit, :mot_de_passe, :no_adresse)";
	private final String UPDATE = "UPDATE utilisateurs SET email = :email, nom = :nom, prenom = :prenom, administrateur = :administrateur, telephone = :telephone, credit = :credit, no_adresse = :no_adresse WHERE pseudo = :pseudo";
	private final String COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM utilisateurs WHERE pseudo = :pseudo";
	private final String COUNT_EMAIL = "SELECT COUNT(email) FROM utilisateurs WHERE email = :email";
	private final String COUNT_NEW_EMAIL = "SELECT COUNT(email) FROM utilisateurs WHERE email = :email AND pseudo <> :pseudo";
	private final String UPDATE_CREDIT = "UPDATE utilisateurs SET credit = :credit WHERE pseudo = :pseudo";

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
					new BeanPropertyRowMapper<>(Utilisateur.class));
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
;
	}
}
