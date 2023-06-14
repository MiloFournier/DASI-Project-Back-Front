package metier.om;

import javax.persistence.Entity;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Cartomancien, qui est un certain type de Médium.
 * Il n'a rien en plus d'un Médium abstrait.
 */
@Entity
public class Cartomancien extends Medium {

    public Cartomancien() {
    }

    public Cartomancien(String denomination, String genre, String presentation) {
        super(denomination, genre, presentation);
    }

    @Override
    public String toString() {
        String res = "Médium de type \"Cartomancien\"\n";
        res += "\tDénomination : " + denomination + "\n\tGenre : " + genre;
        res += "\n\tPresentation : " + presentation + "\n";
        return res;
    }
    
}