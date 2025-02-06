package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO{

	private String REQ_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	private String REQ_CATEGORIE_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :id";
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Categorie> findAll() {
		return jdbcTemplate.query(REQ_ALL_CATEGORIES, new CategoriesRowMapper());
	}
	
	@Override
	public Categorie findById(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);

		return jdbcTemplate.queryForObject(REQ_CATEGORIE_BY_ID, namedParameters, new CategoriesRowMapper());
	}

	class CategoriesRowMapper implements RowMapper<Categorie>{

		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Categorie c = new Categorie();

			c.setId(rs.getLong("no_categorie"));
			c.setLibelle(rs.getString("libelle"));
			return c;
		}
		
	}

	
	
}
