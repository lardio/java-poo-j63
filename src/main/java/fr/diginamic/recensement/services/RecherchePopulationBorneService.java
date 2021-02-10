package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.*;


/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws RescencementException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();
		if (!Pattern.matches("[+]?[0-9]+", saisieMin)) {
			throw new NotANumberException("Le minimum doit être un entier suppérieur à 0");
		}

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;

		if (min < 0) {
			throw new NotAGoodValueException("min < 0.");
		} else if (max < 0) {
			throw new NotAGoodValueException("max > 0.");
		} else if (min > max) {
			throw new NotAGoodValueException("max < min");
		}

		
		List<Ville> villes = rec.getVilles();
		boolean depTrouve = false;
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				depTrouve = true;
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}

		if (!depTrouve) {
			throw new NotANumberException("code departement invalide");
		}
	}

}
