package metier.om;

import javax.persistence.Entity;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Spirite, qui est un certain type de Médium.
 * Il a, en plus d'un médium abstrait, un support pour exercer son métier.
 */
@Entity
public class Spirite extends Medium {
    private String support;

    public Spirite() {
    }

    public Spirite(String denomination, String genre, String presentation, String support) {
        super(denomination, genre, presentation);
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
    
    @Override
    public String toString() {
        String res = "Médium de type \"Spirite\"\n";
        res += "\tDénomination : " + denomination + "\n\tGenre : " + genre;
        res += "\n\tSupport : " + support;
        res += "\n\tPresentation : " + presentation + "\n";
        return res;
    }
    
}