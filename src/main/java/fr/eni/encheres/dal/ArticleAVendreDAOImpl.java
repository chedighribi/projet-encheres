package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleAVendre;

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
			+ "  FROM ARTICLES_A_VENDRE";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<ArticleAVendre> findAll() {
		
		return jdbcTemplate.query(REQ_ARTICLES, new ArticleAVendreRowMapper());
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
