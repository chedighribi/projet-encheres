package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Adresse implements Serializable {
	/**
	 * Identifiant de l'interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String rue;
	private String codePostal;
	private String ville;
	
	
}
