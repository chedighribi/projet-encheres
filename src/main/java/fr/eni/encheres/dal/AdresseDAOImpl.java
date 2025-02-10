package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Adresse;

@Repository
public class AdresseDAOImpl implements AdresseDAO {

	private final String FIND_BY_ID = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM adresses WHERE no_adresse = :no_adresse";
	private final String FIND_ALL = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM adresses";
	private final String FIND_ALL_ENI = "select no_adresse,rue,code_postal, adresse_eni from adresses WHERE adresse_eni = 1;";
	private final String INSERT = "INSERT INTO ADRESSES (rue, code_postal, ville, adresse_eni) VALUES (:rue, :code_postal, :ville, :adresse_eni)";
	private final String COUNT_VILLE = "SELECT COUNT(ville) FROM adresses WHERE ville = :ville";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Adresse adresse) {
		System.out.println(" DAO adresse create");
		System.out.println(adresse);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("rue", adresse.getRue());
		namedParameters.addValue("code_postal", adresse.getCodePostal());
		namedParameters.addValue("ville", adresse.getVille());
		namedParameters.addValue("adresse_eni", (adresse.isAdresseEni())?1:0);
		
		jdbcTemplate.update(INSERT, namedParameters, keyHolder);
	}

	@Override
	public Adresse read(long noAdresse) {
		System.out.println("DAO utilisateur findpseudo");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_adresse", noAdresse);
		return jdbcTemplate.queryForObject(FIND_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Adresse.class));
	}

	@Override
	public List<Adresse> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Adresse.class));
	}

	@Override
	public List<Adresse> findAllEni() {
		return jdbcTemplate.query(FIND_ALL_ENI, new BeanPropertyRowMapper<>(Adresse.class));
	}
	
}
