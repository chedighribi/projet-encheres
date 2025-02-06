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

	private final String FIND_BY_PSEUDO = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit FROM utilisateurs WHERE pseudo = :pseudo";
	private final String FIND_BY_EMAIL = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit FROM utilisateurs WHERE email = :email";
	private final String FIND_ALL = "SELECT pseudo, email, nom, prenom, administrateur, telephone, credit FROM utilisateurs";
	private final String INSERT = "INSERT INTO utilisateurs (pseudo, email, nom, prenom, administrateur, telephone, credit, mot_de_passe)";
	private final String COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM utilisateurs WHERE pseudo = :pseudo";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Utilisateur utilisateur) {
		System.out.println(" DAO utilisateur create");
		System.out.println(utilisateur);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("administrateur", utilisateur.isAdministrateur());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("credit", utilisateur.getCredit());

		jdbcTemplate.update(INSERT, namedParameters, keyHolder);

	}

	// need to check if we manage the id and add read with pseudo

	public Utilisateur findByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters,
				new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	@Override
	public List<Utilisateur> read(String email) {
		System.out.println("DAO utilisateur read ");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		return jdbcTemplate.query(FIND_BY_EMAIL, namedParameters, new BeanPropertyRowMapper<>(Utilisateur.class));

	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	@Override
	public int findPseudo(String pseudo) {
		System.out.println("DAO utilisateur findpseudo");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(COUNT_PSEUDO, namedParameters, Integer.class);
	}
}
