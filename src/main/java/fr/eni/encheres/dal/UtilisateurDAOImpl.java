package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	private final String FIND_BY_ID = "SELECT id, pseudo, email, nom, prenom, admin, telephone, credit from UTILISATEUR WHERE id = :id";
	private final String FIND_BY_EMAIL = "SELECT id, pseudo, email, nom, prenom, admin, telephone, credit from UTILISATEUR WHERE email = :email";
	private final String FIND_ALL = "SELECT id, pseudo, email, nom, prenom, admin, telephone, credit from UTILISATEUR";
	private final String FIND_PSEUDO = "SELECT pseudo from UTILISATEUR where pseudo = :pseudo";
	private final String INSERT = "INSERT INTO UTILISATEUR(pseudo, email, nom, prenom, admin, telephone, credit)";
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	

	@Override
	public void create(Utilisateur utilisateur) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("admin", utilisateur.isAdmin());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("credit", utilisateur.getCredit());
		
		jdbcTemplate.update(INSERT, namedParameters, keyHolder);
		
	}

	// need to check if we manage the id and add read with pseudo
	@Override
	public Utilisateur read(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Utilisateur.class));
	}
	@Override
	public Utilisateur read(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		return jdbcTemplate.queryForObject(FIND_BY_EMAIL, namedParameters, new BeanPropertyRowMapper<>(Utilisateur.class));
	}
	

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	@Override
	public String findPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_PSEUDO, namedParameters, String.class );
	}

}
