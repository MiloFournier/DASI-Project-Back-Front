package metier.services;

import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import metier.om.Client;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.ProfilAstral;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe fournit des services aux Employés.
 * Elle permet notamment d'authentifier un Employé, de trouver la prochaine
 * Consultation qu'il doit effectuer, et de savoir quel est l'Employé disponible
 * qui effectuera la Consultation demandée par le Client.
 * Elle permet aussi de démarrer la Consultation à effectuer, de demander
 * des prédictions lors de cette Consultation, et enfin d'en valider la fin.
 */
public class ServiceEmploye {
    
    public ServiceEmploye() {
    
    }
    
    // permet d'authentifier un Employé en vérifiant que l'Employé correspondant au mail existe,
    // et que le mot de passe est bien associé à cet Employé
    public Employe authentifierEmploye(String mail, String motDePasse){
        Employe emp = null;       
        EmployeDao empDao = new EmployeDao();
        
        try{
            JpaUtil.creerContextePersistance();
            emp = empDao.findByMail(mail);
            if (emp != null && !emp.getMotDePasse().equals(motDePasse))
                emp = null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return emp;
    }
    
    // permet de trouver la prochaine Consultation que l'Employé e doit effectuer
    public Consultation afficherProchaineConsultation(Employe e) {
        Consultation cons = null;
        EmployeDao empDao = new EmployeDao();
        
        try{
            JpaUtil.creerContextePersistance();
            cons = empDao.getNextAppointment(e);
        } catch (Exception ex){
            ex.printStackTrace();
            cons = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return cons;
    }
    
    // permet de trouver l'Employé disponible du même genre (passé en paramètre) que le Médium demandé,
    // et qui a le moins de Consultations à son nom
    public Employe trouverEmployeDispo(String genre) {
        EmployeDao empDao = new EmployeDao();
        Employe e = null;
        
        try{
            JpaUtil.creerContextePersistance();
            e = empDao.findAvailableBySex(genre);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }
    
    // permet à l'Employé de dire qu'il est prêt à effectuer la Consultation requise
    // par le Client, et donc de démarrer la Consultation
    // le Client reçoit alors une notification
    // l'employé devient alors indisponible, et la Consultation est en cours
    public Consultation demarrerConsultation(Employe e, Consultation cons){
        ConsultationDao consDao = new ConsultationDao();
        EmployeDao empDao = new EmployeDao();
        
        try{
            JpaUtil.creerContextePersistance();
            cons.setEnCours(true);
            
            // l'employé est en consultation, il est indisponible
            e.setDisponible(false);
            
            JpaUtil.ouvrirTransaction();
            consDao.update(cons);
            empDao.update(e);
            JpaUtil.validerTransaction();
            
            // envoi d'une notification au client
            Client c = cons.getClient();
            String dest = c.getPrenom() + " " + c.getNom();
            String tel = c.getTel();
            // date consultation formatée
            SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = formatterDate.format(cons.getDateConsult());
            // heure consultation formatée
            SimpleDateFormat formatterTime = new SimpleDateFormat("HH'h'mm");
            String formattedTime = formatterTime.format(cons.getHeure());
            
            String corps = "Bonjour "+c.getPrenom()+". J'ai bien reçu votre demande de consultation du "+formattedDate+" à "+formattedTime+". "
                    + "\nVous pouvez dès à présent me contacter au "+e.getTelephonePro()+". A tout de suite ! Médiumiquement vôtre, "+cons.getMedium().getDenomination();
            Message.envoyerNotification(dest, tel, corps);
        }
        catch (Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            cons = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return cons;
    }
    
    // permet de générer des prédictions sur l'amour, la santé et le travail, en fonction des notes données
    // en paramètres par l'Employé, et en fonction du profil du Client c
    // ce service ne pourra être demandé que lorsque la Consultation cons est en cours.
    public List<String> demanderPredictions(Client c, int amour, int sante, int travail, Consultation cons){
        AstroNetApi astro = new AstroNetApi();
        List<String> predictions = null;
        
        if (cons.isEnCours()){
            try {
                ProfilAstral profilClient = c.getProfilAstral();
                String couleur = profilClient.getCouleur();
                String animal = profilClient.getAnimal();
                predictions = astro.getPredictions(couleur, animal, amour, sante, travail);
            } catch (IOException ex) {
                Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return predictions;
    }
    
    // permet de mettre fin à la Consultation cons
    // l'employé devient alors disponible, et la Consultation n'est plus en cours, et est terminée
    public Consultation validerFinConsultation(Employe e, Consultation cons){
        ConsultationDao consDao = new ConsultationDao();
        EmployeDao empDao = new EmployeDao();
        
        try{
            JpaUtil.creerContextePersistance();
            cons.setEnCours(false);
            cons.setTerminee(true);
            
            // l'employé redevient disponible
            e.setDisponible(true);
            
            JpaUtil.ouvrirTransaction();
            consDao.update(cons);
            empDao.update(e);
            JpaUtil.validerTransaction();
        }
        catch (Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            cons = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return cons;
    }
    
    // permet d'ajouter un commentaire à la Consultation cons
    public String saisirCommentaire(String commentaire, Consultation cons){
        ConsultationDao consDao = new ConsultationDao();
        
        try{
            JpaUtil.creerContextePersistance();
            cons.setCommentaire(commentaire);
                        
            JpaUtil.ouvrirTransaction();
            consDao.update(cons);
            JpaUtil.validerTransaction();
        }
        catch (Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            commentaire = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return commentaire;
    }
    
    // permet de déterminer la répartition des Clients par Employé
    public Map<Employe, Integer> trouverRepartitionClientsParEmp(){
        EmployeDao empDao = new EmployeDao();
        Map<Employe, Integer> repCli = null;
        try{
            JpaUtil.creerContextePersistance();
            repCli = empDao.getRepartitionClientsParEmp();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return repCli;
    }
    
}