package metier.om;

import javax.persistence.Entity;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Astrologue, qui est un certain type de Médium.
 * Il a, en plus d'un médium abstrait, un lieu formation et une date de promotion.
 */
@Entity
public class Astrologue extends Medium {
    private String formation;
    private String promotion;

    public Astrologue() {
    }

    public Astrologue(String denomination, String genre, String presentation, String formation, String promotion) {
        super(denomination, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        String res = "Médium de type \"Astrologue\"\n";
        res += "\tDénomination : " + denomination + "\n\tGenre : " + genre;
        res += "\n\tFormation : " + formation + "\n\tPromotion : " + promotion;
        res += "\n\tPresentation : " + presentation + "\n";
        return res;
    }
    
}