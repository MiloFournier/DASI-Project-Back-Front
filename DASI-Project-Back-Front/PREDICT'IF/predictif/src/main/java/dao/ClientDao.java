package dao;

import metier.om.Client;
import metier.om.Medium;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet d'accéder aux données de l'objet Client et fournit
 * des opérations de base pour modifier / ajouter / supprimer / rechercher 
 * des données correspondant à un Client.
 */
public class ClientDao {
    
    // permet de persister un Client dans la base de données
    public void create(Client client){
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    // permet de supprimer un Client de la base de données
    public void delete(Client client){
        JpaUtil.obtenirContextePersistance().remove(client);
    }
    
    // permet de mettre à jour les informations d'un Client dans la base de données
    public void update(Client client){
        JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    // permet de trouver tous les Clients persistés
    public List<Client> findAll(){
        String s = "SELECT c FROM Client c ORDER BY c.id";
        TypedQuery<Client> query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        return query.getResultList();
    }
    
    // permet de trouver le Client correspondant à l'id entré en paramètre
    public Client findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    // permet de trouver le Client correspondant à l'adresse mail entrée en paramètre
    public Client findByMail(String aMail){
        String jpql = "SELECT c FROM Client c WHERE c.mail = :aMail";
        TypedQuery<Client> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Client.class);
        query.setParameter("aMail", aMail);
        Client client;
        try {
            client = query.getSingleResult();
        } catch (Exception e) {
            client = null;
        }
        return client;
    }
    
    
    // permet de retourner les 3 médiums les plus consultés d'un Client, avec le nombre de Consultations
    // permet de déterminer les 5 Médiums les plus demandés par les Clients
    public Map<Medium, Long> getFavMediums(Client client) {
        String jpql = "SELECT c.medium, COUNT(c) as nbConsultations FROM Consultation c WHERE c.client = :client and c.terminee = 1 "
                + "GROUP BY c.medium ORDER BY nbConsultations DESC, c.medium.denomination ASC";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Object[].class);
        query.setParameter("client", client);
        query.setMaxResults(3);
        List<Object[]> resultList = query.getResultList();
        Map<Medium, Long> favMed = new LinkedHashMap<>();
        for (Object[] result : resultList) {
            Medium medium = (Medium) result[0];
            Long nbCons = (Long) result[1];
            favMed.put(medium, nbCons);
        }
        return favMed;
    }
    
}