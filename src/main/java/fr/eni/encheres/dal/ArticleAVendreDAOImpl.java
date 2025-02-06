package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO{

	private String REQ_ARTICLES = "SELECT no_article\r\n"
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
			+ "  FROM ARTICLES_A_VENDRE WHERE statut_enchere = 1";
	

	private String REQ_INSERT_ARTICLE="INSERT INTO ARTICLES_A_VENDRE "
			+ "(no_article,nom_article,description,photo,date_debut_encheres,date_fin_encheres,statut_enchere,prix_initial,"
			+ "prix_vente,id_utilisateur,no_categorie,no_adresse_retrait)"
			+ "VALUES (:idArticle, :nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :statut, :prixInit, :prixVente, :idUtilisateur,"
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
	

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<ArticleAVendre> findAll() {
		
		return jdbcTemplate.query(REQ_ARTICLES, new ArticleAVendreRowMapper());
	}
	
	@Override

	public void insertArticle(ArticleAVendre articleAVendre) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(":idArticle", articleAVendre.getId());
		namedParameters.addValue(":nomArticle", articleAVendre.getNom());
		namedParameters.addValue(":description", articleAVendre.getDescription());
		namedParameters.addValue(":dateDebutEncheres", articleAVendre.getDateDebutEncheres());
		namedParameters.addValue(":dateFinEncheres", articleAVendre.getDateFinEncheres());
		namedParameters.addValue(":statut", articleAVendre.getStatut());
		namedParameters.addValue(":prixInit", articleAVendre.getPrixInitial());
		namedParameters.addValue(":prixVente", articleAVendre.getPrixVente());
		namedParameters.addValue(":idUtilisateur", articleAVendre.getUtilisateur().getPseudo());
		namedParameters.addValue(":idCategorie", articleAVendre.getCategorie().getId());
		namedParameters.addValue(":idAdresse", articleAVendre.getAdresse().getId());
		
		
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
			/*
			a.setUtilisateur(null);
			a.setAdresse(null);
			
			a.setCategorie(null);
			// Association pour le réalisateur
			Participant realisateur = new Participant();
			realisateur.setId(rs.getLong("ID_REALISATEUR"));
			f.setRealisateur(realisateur);

			// Association pour le genre
			Genre genre = new Genre();
			genre.setId(rs.getLong("ID_GENRE"));
			f.setGenre(genre);

			return f;
			*/
			return a;
		}
	}

}
