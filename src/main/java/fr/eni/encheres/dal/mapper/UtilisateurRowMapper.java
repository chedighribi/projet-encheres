package fr.eni.encheres.dal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurRowMapper implements RowMapper<Utilisateur> {
	@Override
	public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
		Utilisateur u = new Utilisateur();
		
		u.setPseudo(rs.getString("pseudo"));
		u.setNom(rs.getString("nom"));
		u.setPrenom(rs.getString("prenom"));
		u.setEmail(rs.getString("email"));
		u.setAdministrateur(rs.getBoolean("administrateur"));
		u.setTelephone(rs.getString("telephone"));
		u.setCredit(rs.getInt("credit"));
		u.setMotDePasse("P@s123456");
		Adresse adresse = new Adresse();
		adresse.setNoAdresse(rs.getLong("no_adresse"));
		u.setAdresse(adresse);

		return u;
	}
}