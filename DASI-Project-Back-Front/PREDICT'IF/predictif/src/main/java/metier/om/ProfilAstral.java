package metier.om;

import javax.persistence.Embeddable;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Profil Astral, qui est associé à un Client.
 * Un Profil Astral est utilisé pour stocker le profil astrologique d'un Client,
 * tels que son signe du zodiaque, son signe astrologique chinois, sa couleur fétiche
 * ou encore son animal totem.
 * Il s'agit d'une classe Embeddable, ce qui signifie qu'elle fait partie intégrante
 * de la classe Client.
 */
@Embeddable
public class ProfilAstral {
    
    private String signeZodiaque;
    private String signeChinois;
    private String couleur;
    private String animal;

    public ProfilAstral() {
    }

    public ProfilAstral(String signeZodiaque, String signeChinois, String couleur, String animal) {
        this.signeZodiaque = signeZodiaque;
        this.signeChinois = signeChinois;
        this.couleur = couleur;
        this.animal = animal;
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public String getSigneChinois() {
        return signeChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimal() {
        return animal;
    }
    
}