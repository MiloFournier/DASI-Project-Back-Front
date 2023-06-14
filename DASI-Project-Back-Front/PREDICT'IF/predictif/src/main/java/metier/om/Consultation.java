package metier.om;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet de définir l'entité Consultation, qui est le but même
 * de l'application.
 * Elle permet de mettre en relation un Client avec un Employé, qui représente
 * un Médium choisi au préalable par le Client.
 * Pendant qu'elle est en cours de réalisation, l'Employé peut demander de l'aide
 * afin de générer des prédictions. Une fois que la Consultation est terminée,
 * l'Employé peut saisir un commentaire à cette Consultation, à l'intention de 
 * ses collègues.
 */
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date dateConsult;

    private Time heure;
    
    private String commentaire;
    
    @ManyToOne
    @JoinColumn(name = "medium")
    private Medium medium;
    
    @ManyToOne
    @JoinColumn(name = "employe")
    private Employe employe;
    
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;
    
    private boolean terminee;
    
    private boolean enCours;
    
    
    protected Consultation(){
        
    }
    
    public Consultation(Date date, Time heure) {
        this.dateConsult = date;
        this.heure = heure;
    }

    public Consultation(Medium m, Employe e, Client c, Date date, Time heure) {
        this.dateConsult = date;
        this.heure = heure;
        this.medium = m;
        this.employe = e;
        this.client = c;
        terminee = false;
        enCours = false;
    }

    public Long getId() {
        return id;
    }

    public Date getDateConsult() {
        return dateConsult;
    }

    public void setDateConsult(Date date) {
        this.dateConsult = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }
    

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(dateConsult);
        
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH'h'mm");
        String formattedTime = formatterTime.format(heure);
        
        return "Consultation : entre le client " + client.getPrenom() + " " + client.getNom() 
                + ", l'employé " + employe.getPrenom() + " " + employe.getNom() 
                + " et le médium " + medium.getDenomination()
                + " ; le " + formattedDate + ", à " + formattedTime;
    }
    
}