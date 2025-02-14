package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

	private final String INSERT = "INSERT INTO ENCHERES (date_enchere, montant_enchere, id_utilisateur, no_article) VALUES(:date_enchere, :montant_enchere, :id_utilisateur, :no_article)";
	private final String DELETE = "DELETE encheres WHERE id_utilisateur = :id_utilisateur";
	private final String FIND_BY_ARTICLE= "SELECT date_enchere, montant_enchere, id_utilisateur, no_article FROM ENCHERES WHERE no_article = :id";
	private final String FIND_BY_USER = "SELECT date_enchere, montant_enchere, id_utilisateur, no_article FROM ENCHERES WHERE id_utilisateur = :pseudo";
	private final String FIND_HIGHEST = "SELECT TOP 1 * FROM encheres WHERE no_article = :articleId ORDER BY montant_enchere DESC";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Enchere enchere) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		namedParameter.addValue("date_enchere", LocalDate.now());
		namedParameter.addValue("montant_enchere", enchere.getMontant());
		namedParameter.addValue("id_utilisateur", enchere.getUtilisateur().getPseudo());
		namedParameter.addValue("no_article", enchere.getArticleAVendre().getId());
		jdbcTemplate.update(INSERT, namedParameter, keyHolder);
	}

	@Override
	public List<Enchere> readByArticle(long id) {
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		namedParameter.addValue("id", id);
		return jdbcTemplate.query(FIND_BY_ARTICLE, namedParameter, new EnchereRowMapper());
	}

	@Override
	public List<Enchere> readByUser(String pseudo) {
	    MapSqlParameterSource namedParameter = new MapSqlParameterSource();
	    namedParameter.addValue("pseudo", pseudo);
	    return jdbcTemplate.query(FIND_BY_USER, namedParameter, new EnchereRowMapper());
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

	@Override
	public Enchere readHighestEnchere(long articleId) {
	    MapSqlParameterSource namedParameter = new MapSqlParameterSource();
	    namedParameter.addValue("articleId", articleId);
        return jdbcTemplate.queryForObject(FIND_HIGHEST, namedParameter, new EnchereRowMapper());
	}

	@Override
	public void deleteEnchere(String pseudo) {
		// TODO Auto-generated method stub
		
	}

}
