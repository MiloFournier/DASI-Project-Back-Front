package metier.services;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;
import metier.om.Client;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.Medium;
import metier.om.ProfilAstral;
import java.util.List;
import java.util.Map;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe fournit des services aux Clients.
 * Elle permet notamment d'inscrire, d'authentifier, de trouver un Client.
 * Elle permet aussi de demander une Consultation.
 */
public class ServiceClient {
    
    public ServiceClient(){
  
    }
    
    // permet d'inscrire un Client et de générer son Profil Astral, et de le renvoyer
    // s'occupe donc de l'envoi de mail de confirmation / infirmation d'inscription
    public Client inscriptionClient(Client client){
        ClientDao clientFinalDao = new ClientDao();
        String exp = "contact@predict.if";
        String dest = client.getMail();
        String obj = "Echec de l'inscription chez PREDICT'IF";
        String corps = "Bonjour "+client.getPrenom()+", votre inscription au service PREDICT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.";
        
        try{
            AstroNetApi api = new AstroNetApi();
            List<String> profils;
            profils = api.getProfil(client.getPrenom(), client.getDateNaissance());
            String signeZodiaque = profils.get(0);
            String signeChinois = profils.get(1);
            String couleur = profils.get(2);
            String animal = profils.get(3);
                
            ProfilAstral profilAstral = new ProfilAstral(signeZodiaque, signeChinois, couleur, animal);
            client.setProfilAstral(profilAstral);

            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            clientFinalDao.create(client);
            JpaUtil.validerTransaction();
            obj = "Bienvenue chez PREDICT'IF";
            corps = "Bonjour "+client.getPrenom()+", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums.";
        } catch(Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            client = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
            Message.envoyerMail(exp, dest, obj, corps);
        }
        return client;        
    }
    
    // permet d'authentifier un Client en vérifiant que le Client correspondant au mail existe,
    // et que le mot de passe est bien associé à ce Client
    public Client authentifierClient(String mail, String motDePasse){
        Client cli = null;       
        ClientDao cliDao = new ClientDao();
        
        try{
            JpaUtil.creerContextePersistance();
            cli = cliDao.findByMail(mail);
            // pas les bons logins ou n'existe pas
            if (cli != null && !cli.getMotDePasse().equals(motDePasse))
                cli = null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return cli;
    }
    
    // permet de demander une Consultation, en créant une Consultation entre :
    // un Médium choisi par le Client, le premier Employé du genre du Médium choisi et disponible, 
    // et le Client qui émet la demande.
    // L'objet Consultation en paramètre permet de transmettre la date et l'heure de la demande
    public Consultation creerConsultation(Medium m, Employe e, Client c, Consultation cons){
        ConsultationDao consDao = new ConsultationDao();
        ClientDao cliDao = new ClientDao();
        EmployeDao empDao = new EmployeDao();
        MediumDao medDao = new MediumDao();
        
        try{
            JpaUtil.creerContextePersistance();
            m.addConsultation(cons);
            e.addConsultation(cons);
            c.addConsultation(cons);
            // maj des liens réciproques
            cons.setMedium(m);
            cons.setEmploye(e);
            cons.setClient(c);
            
            JpaUtil.ouvrirTransaction();
            consDao.create(cons);
            cliDao.update(c);
            empDao.update(e);
            medDao.update(m);
            JpaUtil.validerTransaction();
            
            // envoi d'une notification à l'Employé
            String dest = e.getPrenom() + " " + e.getNom();
            String tel = e.getTelephonePro();
            String corps = "Bonjour "+e.getPrenom()+". Consultation requise pour "+c.getPrenom()+" "+c.getNom()+". Médium à incarner : "+m.getDenomination();
            Message.envoyerNotification(dest, tel, corps);
        } catch(Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            cons = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return cons;
    }
    
    // permet de déterminer les trois Médiums préférés d'un Client, avec le nombre de Consultations
    public Map<Medium, Long> trouverMediumsFavoris(Client client){
        ClientDao cliDao = new ClientDao();
        Map<Medium, Long> medPref = null;
        try{
            JpaUtil.creerContextePersistance();
            medPref = cliDao.getFavMediums(client);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return medPref;
    }
    
}