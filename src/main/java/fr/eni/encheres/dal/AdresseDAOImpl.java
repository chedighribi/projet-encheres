package fr.eni.encheres.dal;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Logger;
import fr.eni.encheres.bo.Adresse;

@Repository
public class AdresseDAOImpl implements AdresseDAO {

	private Logger logger = (Logger) LoggerFactory.getLogger("fr.eni.encheres.dev");
	
	private final String FIND_BY_ID = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM adresses WHERE no_adresse = :no_adresse";
	private final String FIND_ALL = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM adresses";

	private final String FIND_ALL_ENI = "select no_adresse,rue,code_postal, adresse_eni from adresses WHERE adresse_eni = 1;";

	private final String FIND_ENI_PLUS_UTILISATEUR = "SELECT * FROM ADRESSES WHERE adresse_eni = 1 OR no_adresse = :no_adresse ORDER BY adresse_eni";

	private final String INSERT = "INSERT INTO ADRESSES (rue, code_postal, ville, adresse_eni) VALUES (:rue, :code_postal, :ville, :adresse_eni)";
	private final String UPDATE = "UPDATE ADRESSES SET rue = :rue, code_postal = :code_postal, ville = :ville, adresse_eni = :adresse_eni) WHERE no_adresse = :no_adresse AND adresse_eni = 0";
	private final String DELETE = "DELETE adresses WHERE no_adresse = :no_adresse AND adresse_eni = 0";
	private final String COUNT_VILLE = "SELECT COUNT(ville) FROM adresses WHERE ville = :ville";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private MapSqlParameterSource loadNamedParameters(Adresse adresse) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("rue", adresse.getRue());
		namedParameters.addValue("code_postal", adresse.getCodePostal());
		namedParameters.addValue("ville", adresse.getVille());
		namedParameters.addValue("adresse_eni", 0);
		return namedParameters;
	}
	
	@Override
	public void create(Adresse adresse) {
		logger.trace("DAO adresse create");
		logger.trace(adresse.toString());
		System.out.println(adresse);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = loadNamedParameters(adresse);
		
		jdbcTemplate.update(INSERT, namedParameters, keyHolder);
		
		System.out.println("------INSERT ADRESSE ID " + keyHolder.getKey() + " -----------");
		adresse.setNoAdresse(keyHolder.getKey().longValue());
	}
	
	@Override
	public void update(Adresse adresse) {
		logger.trace("DAO adresse update");
		logger.trace(adresse.toString());
		System.out.println(adresse);
		
		MapSqlParameterSource namedParameters = loadNamedParameters(adresse);
		
		jdbcTemplate.update(UPDATE, namedParameters);
	}
	
	@Override
	public void delete(Adresse adresse) {
		logger.trace("DAO adresse delete");
		logger.trace(adresse.toString());
		System.out.println(adresse);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_adresse", adresse.getNoAdresse());
		jdbcTemplate.update(DELETE, namedParameters);
	}

	@Override
	public Adresse read(long noAdresse) {
		System.out.println("DAO adresse read");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_adresse", noAdresse);
		return jdbcTemplate.queryForObject(FIND_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Adresse.class));
	}

	@Override
	public List<Adresse> findAll() {
		System.out.println("DAO adresse findAll");
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Adresse.class));
	}

	@Override
	public List<Adresse> findAllEni() {
		return jdbcTemplate.query(FIND_ALL_ENI, new BeanPropertyRowMapper<>(Adresse.class));
	}
	
	/**
	 * Renvoi une liste d'adresse contenant les adresses liées à l'ENI plus l'adresse de l utilisateur
	 * 
	 * @param noAdresse id de l adresse de l utilisateur cible
	 * @return une liste d adresse
	 */
	@Override
	public List<Adresse> findEniPlusUtilisateur(long noAdresse) {
		System.out.println("DAO adresse findEniPlusUtilisateur");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_adresse", noAdresse);
		return jdbcTemplate.query(FIND_ENI_PLUS_UTILISATEUR, namedParameters, new BeanPropertyRowMapper<>(Adresse.class));
	}
	
}
