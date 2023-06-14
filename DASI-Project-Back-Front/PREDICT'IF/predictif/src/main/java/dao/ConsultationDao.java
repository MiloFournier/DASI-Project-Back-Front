package dao;

import metier.om.Client;
import metier.om.Consultation;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet d'accéder aux données de l'objet Consultation et fournit
 * des opérations de base pour modifier / ajouter / supprimer / rechercher 
 * des données correspondant à une Consultation.
 */
public class ConsultationDao {
    
    // permet de persister une Consultation dans la base de données
    public void create(Consultation consultation){
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }
    
    // permet de supprimer une Consultation de la base de données
    public void delete(Consultation consultation){
        JpaUtil.obtenirContextePersistance().remove(consultation);
    }
    
    // permet de mettre à jour les informations d'une Consultation dans la base de données
    public void update(Consultation consultation){
        JpaUtil.obtenirContextePersistance().merge(consultation);
    }
    
    // permet de trouver la Consultation correspondant à l'id entré en paramètre
    public Consultation findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }

    // permet de trouver toutes les consultations qu'un Client client a déjà effectuées
    // correspond donc à l'historique de consultations d'un Client, ordonné à partir de la plus récente Consultation
    public List<Consultation> findAllAppointmentsOfClient(Client client){
        String jpql = "SELECT co FROM Consultation co WHERE co.client = :client and co.terminee = 1 ORDER BY co.dateConsult desc, co.heure desc";
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Consultation.class);
        query.setParameter("client", client);
        List<Consultation> res = query.getResultList();
        return res;
    }
    
}