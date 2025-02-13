package fr.eni.encheres.exceptions;

import fr.eni.encheres.bo.Adresse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BusinessCode {
	public static final String VALIDATION_ARTICLE_NULL = "validation.article.null";
	public static final String VALIDATION_ARTICLE_NOM_BLANK = "validation.article.nom.blank";
	public static final String VALIDATION_ARTICLE_CATEGORIE_NULL = "validation.article.categorie.null";
	public static final String VALIDATION_ARTICLE_DESCRIPTION_BLANK = "validation.article.description.blank";
	public static final String VALIDATION_ARTICLE_DESCRIPTION_LENGTH = "validation.article.description.length";
	public static final String VALIDATION_ARTICLE_ADRESSE_NULL = "validation.article.adresse.null";
	public static final String VALIDATION_ARTICLE_PRIX_MIN = "validation.article.prix.min";
	public static final String VALIDATION_ARTICLE_DATEDEBUT_MIN = "validation.article.datedebut.min";
	public static final String VALIDATION_ARTICLE_DATEFIN_MIN = "validation.article.datefin.min";
	public static final String VALIDATION_ARTICLE_DATES = "validation.article.dates";

	public static final String VALIDATION_UTILISATEUR_NULL = "validation.utilisateur.null";
	public static final String VALIDATION_UTILISATEUR_PSEUDO_BLANK = "validation.utilisateur.pseudo.blank";
	public static final String VALIDATION_UTILISATEUR_PSEUDO_SIZE = "validation.utilisateur.pseudo.size";
	public static final String VALIDATION_UTILISATEUR_PSEUDO_UNIQUE = "validation.utilisateur.pseudo.unique";
	public static final String VALIDATION_UTILISATEUR_NOM_BLANK = "validation.utilisateur.nom.blank";
	public static final String VALIDATION_UTILISATEUR_NOM_SIZE = "validation.utilisateur.nom.size";
	public static final String VALIDATION_UTILISATEUR_PRENOM_BLANK = "validation.utilisateur.prenom.blank";
	public static final String VALIDATION_UTILISATEUR_PRENOM_SIZE = "validation.utilisateur.prenom.size";
	public static final String VALIDATION_UTILISATEUR_EMAIL_BLANK = "validation.utilisateur.email.blank";
	public static final String VALIDATION_UTILISATEUR_EMAIL_SIZE = "validation.utilisateur.email.size";
	public static final String VALIDATION_UTILISATEUR_EMAIL_FORMAT = "validation.utilisateur.email.format";
	public static final String VALIDATION_UTILISATEUR_EMAIL_UNIQUE = "validation.utilisateur.email.unique";
	public static final String VALIDATION_UTILISATEUR_PHONE_SIZE = "validation.utilisateur.phone.size";
	public static final String VALIDATION_UTILISATEUR_MDP_BLANK = "validation.utilisateur.mdp.blank";
	public static final String VALIDATION_UTILISATEUR_MDP_SIZE = "validation.utilisateur.mdp.size";
	public static final String VALIDATION_UTILISATEUR_MDP_PATTERN = "validation.utilisateur.mdp.pattern";
	public static final String VALIDATION_UTILISATEUR_ADRESSE_BLANK = "validation.utilisateur.adresse.blank";

	
	public static final String VALIDATION_ADRESSE_NULL = "validation.adresse.null";
	public static final String VALIDATION_ADRESSE_VILLE_BLANK = "validation.adresse.ville.blank";
	public static final String VALIDATION_ADRESSE_VILLE_SIZE = "validation.adresse.ville.size";
	public static final String VALIDATION_ADRESSE_RUE_BLANK = "validation.adresse.rue.blank";
	public static final String VALIDATION_ADRESSE_RUE_SIZE = "validation.adresse.rue.size";
	public static final String VALIDATION_ADRESSE_CODEPOSTAL_BLANK = "validation.adresse.codePostal.blank";
	public static final String VALIDATION_ADRESSE_CODEPOSTAL_SIZE = "validation.adresse.codePostal.size";
	
	public static final String VALIDATION_ENCHERE_AMOUNT = "validation.enchere.amount";

	public static final String MESSAGE_ERREUR_PROFILE_NON_TROUVE = "message.erreur.profil.non.trouve";
	
	
}
