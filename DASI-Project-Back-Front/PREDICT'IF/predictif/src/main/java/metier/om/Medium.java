package metier.om;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Médium, qui est celui avec qui le 
 * Client pense réaliser une consultation. En réalité, ce sera un Employé du
 * même genre qui se fera passer pour ce médium.
 * Un Médium peut être de trois types : Astrologue, Cartomancien ou Spirite.
 * Dans tous les cas, il a une liste des consultations qui ont été réalisées avec lui.
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Medium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    protected String denomination;
    protected String genre;
    protected String presentation;
    
    @OneToMany(mappedBy = "medium")
    private List<Consultation> consultations;

    public Medium() {
    }

    public Medium(String denomination, String genre, String presentation) {
        this.denomination = denomination;
        this.genre = genre;
        this.presentation = presentation;
        consultations = new ArrayList<>();
    }

    public Long getID() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }
    
    public void addConsultation(Consultation cons) {
        consultations.add(cons);
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

}