package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public class EnchereDAOImpl implements EnchereDAO {

	private final String INSERT = "INSERT INTO ENCHERE (date_enchere, montant_enchere, id_utilisateur, no_article)";
	private final String FIND_BY_ITEM = "SELECT date_enchere, montant_enchere, id_utilisateur, no_article FROM ENCHERE WHERE montant_enchere = :pseudo";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Enchere enchere) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		namedParameter.addValue("date", enchere.getDate());
		namedParameter.addValue("montant", enchere.getMontant());
		namedParameter.addValue("utilisateur", enchere.getUtilisateur());
		namedParameter.addValue("articleAVendre", enchere.getArticleAVendre());
		jdbcTemplate.update(INSERT, namedParameter, keyHolder);
	}

	@Override
	public Enchere readByItem(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> readByUser(String pseudo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class EnchereRowMapper implements RowMapper<Enchere> {

		@Override
		public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
			Enchere e = new Enchere();
			//e.setDate(rs.get("date_enchere"));
			e.setMontant(rs.getInt("montant_enchere"));
			Utilisateur acquereur  = new Utilisateur();
			acquereur.setPseudo(rs.getString("id_utilisateur"));
			e.setUtilisateur(acquereur);
			ArticleAVendre article = new ArticleAVendre();
			article.setId(rs.getInt("no_article"));
			e.setArticleAVendre(article);
			return e;
		}
		
	}

}
