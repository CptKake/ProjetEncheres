package fr.eni.tp.dal;

import fr.eni.tp.bo.Retrait;
import fr.eni.tp.bo.Utilisateur;

public interface RetraitDAO {

	Retrait takeAdress(Utilisateur user);
	
	
	
}
