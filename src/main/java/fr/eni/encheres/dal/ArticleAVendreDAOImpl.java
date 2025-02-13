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

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {

	private String REQ_ARTICLES = "SELECT no_article\r\n" + "      ,nom_article\r\n" + "      ,description\r\n"
			+ "      ,photo\r\n" + "      ,date_debut_encheres\r\n" + "      ,date_fin_encheres\r\n"
			+ "      ,statut_enchere\r\n" + "      ,prix_initial\r\n" + "      ,prix_vente\r\n"
			+ "      ,id_utilisateur\r\n" + "      ,no_categorie\r\n" + "      ,no_adresse_retrait\r\n"
			+ "  FROM ARTICLES_A_VENDRE";

	private String REQ_INSERT_ARTICLE = "INSERT INTO ARTICLES_A_VENDRE "
			+ "(nom_article,description,date_debut_encheres,date_fin_encheres,statut_enchere,prix_initial,"
			+ "prix_vente,id_utilisateur,no_categorie,no_adresse_retrait)"
			+ "VALUES (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :statut, :prixInit, :prixVente, :idUtilisateur,"
			+ ":idCategorie, :idAdresse)";

	private String FIND_ONE =  "SELECT no_article\r\n"
			+ "      ,nom_article\r\n"
			+ "      ,description\r\n"
			+ "      ,photo\r\n"
			+ "      ,date_debut_encheres\r\n"
			+ "      ,date_fin_encheres\r\n"
			+ "      ,statut_enchere\r\n"
			+ "      ,prix_initial\r\n"
			+ "      ,prix_vente\r\n"
			+ "      ,id_utilisateur\r\n"
			+ "      ,no_categorie\r\n"
			+ "      ,no_adresse_retrait\r\n"
			+ "  FROM ARTICLES_A_VENDRE WHERE no_article= :id";
	
	private String REQ_UPDATE_ARTICLE="UPDATE ARTICLES_A_VENDRE\r\n"
			+ "SET nom_article = :nomArticle, description = :description, date_debut_encheres = :dateDebutEncheres, date_fin_encheres = :dateFinEncheres,"
			+ "statut_enchere = :statut,prix_initial = :prixInit,prix_vente = :prixVente,no_categorie = :idCategorie,no_adresse_retrait = :idAdresse\r\n"
			+ "WHERE no_article = :idArticle";
	
	private String REQ_DELETE_ARTICLE = "DELETE FROM ARTICLES_A_VENDRE WHERE no_article= :idArticle";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ArticleAVendre> findAll() {
		return jdbcTemplate.query(REQ_ARTICLES, new ArticleAVendreRowMapper());
	}

	@Override
	public void insertArticle(ArticleAVendre articleAVendre) {
		System.out.println("=========================ARTICLE A VENDRE=====================" + articleAVendre);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		if(articleAVendre.getDateDebutEncheres().isEqual(LocalDate.now())) {
			articleAVendre.setStatut(1);
		}else {
			articleAVendre.setStatut(0);
		}
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("nomArticle", articleAVendre.getNom());
		namedParameters.addValue("description", articleAVendre.getDescription());
		namedParameters.addValue("dateDebutEncheres", articleAVendre.getDateDebutEncheres());
		namedParameters.addValue("dateFinEncheres", articleAVendre.getDateFinEncheres());
		namedParameters.addValue("statut", articleAVendre.getStatut());
		namedParameters.addValue("prixInit", articleAVendre.getPrixInitial());
		namedParameters.addValue("prixVente", articleAVendre.getPrixVente());
		namedParameters.addValue("idUtilisateur", articleAVendre.getVendeur().getPseudo());
		namedParameters.addValue("idCategorie", articleAVendre.getCategorie().getId());
		namedParameters.addValue("idAdresse", articleAVendre.getAdresse().getNoAdresse());

		jdbcTemplate.update(REQ_INSERT_ARTICLE, namedParameters, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {
			articleAVendre.setId(keyHolder.getKey().longValue());
		}
	}

	
	@Override
	public void updateArticle(ArticleAVendre articleAVendre) {
		if(articleAVendre.getDateDebutEncheres().isEqual(LocalDate.now())) {
			articleAVendre.setStatut(1);
		}else {
			articleAVendre.setStatut(0);
		}
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idArticle", articleAVendre.getId());
		namedParameters.addValue("nomArticle", articleAVendre.getNom());
		namedParameters.addValue("description", articleAVendre.getDescription());
		namedParameters.addValue("dateDebutEncheres", articleAVendre.getDateDebutEncheres());
		namedParameters.addValue("dateFinEncheres", articleAVendre.getDateFinEncheres());
		namedParameters.addValue("statut", articleAVendre.getStatut());
		namedParameters.addValue("prixInit", articleAVendre.getPrixInitial());
		namedParameters.addValue("prixVente", articleAVendre.getPrixVente());
		namedParameters.addValue("idCategorie", articleAVendre.getCategorie().getId());
		namedParameters.addValue("idAdresse", articleAVendre.getAdresse().getNoAdresse());

		jdbcTemplate.update(REQ_UPDATE_ARTICLE, namedParameters);
	}
	
	@Override
	public void delete(ArticleAVendre articleAVendre) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idArticle", articleAVendre.getId());
		jdbcTemplate.update(REQ_DELETE_ARTICLE, namedParameters);
	}


	public ArticleAVendre find(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_ONE, namedParameters, new ArticleAVendreRowMapper());
	}

	class ArticleAVendreRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre a = new ArticleAVendre();

			a.setId(rs.getLong("no_article"));
			a.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
			a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
			a.setDescription(rs.getString("description"));
			a.setNom(rs.getString("nom_article"));
			a.setPrixInitial(rs.getInt("prix_initial"));
			a.setPrixVente(rs.getInt("prix_vente"));
			a.setStatut(rs.getInt("statut_enchere"));
			
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			a.setVendeur(vendeur);

			Categorie categorie = new Categorie();
			categorie.setId(rs.getLong("no_categorie"));

			Adresse adresse = new Adresse();
			adresse.setNoAdresse(rs.getLong("no_adresse_retrait"));

			a.setCategorie(categorie);
			a.setAdresse(adresse);
			return a;
		}
	}
}
