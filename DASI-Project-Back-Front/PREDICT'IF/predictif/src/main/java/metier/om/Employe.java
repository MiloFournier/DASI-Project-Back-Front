package metier.om;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Employé, qui est un utilisateur
 * de l'application.
 * Son genre définira quel Médium il pourra représenter lors d'une Consultation. 
 * Il a également la liste des consultations qu'il a réalisées.
 */
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    // Le Long est un objet de la classe Long, id peut être null. 
    // Si on avait mis long ça n'aurait pas pu être le cas car c'est juste un type de base
    private String nom;
    private String prenom;
    private String genre;
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    private String telephonePro;
    private String motDePasse;
    private boolean disponible;
    
    @OneToMany(mappedBy = "employe")
    private List<Consultation> consultations;

    protected Employe() {
    }

    public Employe(String nom, String prenom, String genre, String mail, String telephonePro) {
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.mail = mail;
        this.telephonePro = telephonePro;
        disponible = true;
        consultations = new ArrayList<>();
    }

    public String getGenre() {
        return genre;
    }

    public String getMail() {
        return mail;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephonePro() {
        return telephonePro;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephonePro(String telephonePro) {
        this.telephonePro = telephonePro;
    }


    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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
    
    @Override
    public String toString() {
        return "Employe : " + nom + " " + prenom + ", genre : " + genre + ", mail : " + mail + ", telephonePro : " + telephonePro;
    }
   
}