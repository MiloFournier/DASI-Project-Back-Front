package metier.om;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Client, qui est un utilisateur de 
 * l'application.
 * Il lui est affecté un Profil Astral, qui lui donne notamment une couleur fétiche,
 * un animal totem, un signe astrologique chinois et un signe du zodiaque.
 * Il a également une liste de consultations qu'il a réalisées.
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    private String nom;
    private String prenom;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    private String adressePostale;
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    private String motDePasse;
    
    private String tel;
    
    @Embedded
    private ProfilAstral profilAstral;
    
    @OneToMany(mappedBy = "client")
    private List<Consultation> consultations;

    protected Client() {
    }

    public Client(String nom, String prenom, Date dateNaissance, String adressePostale, String tel, String mail) {
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adressePostale = adressePostale;
        this.mail = mail;
        consultations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public ProfilAstral getProfilAstral() {
        return profilAstral;
    }

    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
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
        return "Client : " + nom + " " + prenom + ", né le " + dateNaissance + ", mail : " + mail + ", téléphone : " + tel;
    }
    
}