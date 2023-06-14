package metier.services;

import dao.ConsultationDao;
import dao.JpaUtil;
import metier.om.Client;
import metier.om.Consultation;
import java.util.List;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe fournit des services relatifs à une Consultation.
 * Elle permet de trouver l'historique de consultations d'un Client,
 * ordonné de la plus récente à la plus ancienne.
 */
public class ServiceConsultation {
    
    public ServiceConsultation() {
    
    }
    
    // renvoie l'historique de Consultations d'un Client entré en paramètre,
    // ordonné de la plus récente Consultation à la plus ancienne
    public List<Consultation> retournerHistoConsultationsClient(Client client){
        ConsultationDao consDao = new ConsultationDao();
        List<Consultation> listeCons = null;
        try{
            JpaUtil.creerContextePersistance();
            listeCons = consDao.findAllAppointmentsOfClient(client);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return listeCons;
    }

}