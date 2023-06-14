package dao;

import metier.om.Consultation;
import metier.om.Employe;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet d'accéder aux données de l'objet Employé et fournit
 * des opérations de base pour modifier / ajouter / supprimer / rechercher 
 * des données correspondant à un Employé.
 */
public class EmployeDao {
    
    // permet de persister un Employé dans la base de données
    public void create(Employe employe){
        JpaUtil.obtenirContextePersistance().persist(employe);
    }
    
    // permet de supprimer un Employé de la base de données
    public void delete(Employe employe){
        JpaUtil.obtenirContextePersistance().remove(employe);
    }
    
    // permet de mettre à jour les informations d'un Employé dans la base de données
    public void update(Employe employe){
        JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    // permet de trouver tous les Employés persistés
    public List<Employe> findAll(){
        String s = "SELECT e FROM Employe e ORDER BY e.nom asc";
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        return query.getResultList();
    }
    
    // permet de trouver l'Employé correspondant à l'id entré en paramètre
    public Employe findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }
    
    // permet de trouver l'Employé correspondant à l'adresse mail entrée en paramètre
    public Employe findByMail(String aMail){
        String jpql = "SELECT e FROM Employe e WHERE e.mail = :aMail";
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Employe.class);
        query.setParameter("aMail", aMail);
        
        Employe emp;
        try {
            emp = query.getSingleResult();
        } catch (Exception e) {
            emp = null;
        }
        return emp;
    }
    
    // permet de trouver l'Employé disponible qui a le moins de consultations réalisées
    public Employe findAvailableBySex(String genre){
        String jpql = "SELECT e FROM Employe e WHERE e.genre = :sex AND e.disponible = 1 ORDER BY SIZE(e.consultations)";
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Employe.class);
        query.setParameter("sex", genre);
        query.setMaxResults(1);
        
        Employe emp;
        try {
            emp = query.getSingleResult();
        } catch (Exception e) {
            emp = null;
        }
        return emp;
    }
    
    // permet de trouver la prochaine consultation qu'un Employé e doit effectuer.
    public Consultation getNextAppointment(Employe e) {
        String s = "SELECT c FROM Consultation c WHERE c.enCours = 0 AND c.terminee = 0 AND c.employe = :emp "
                + "ORDER BY c.dateConsult desc, c.heure desc";
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        query.setParameter("emp", e);
        query.setMaxResults(1);
        
        Consultation cons;
        try {
            cons = query.getSingleResult();
        } catch (Exception ex) {
            cons = null;
        }
        return cons;
    }
    
    // permet de déterminer la répartition des Clients par Employé
    public Map<Employe, Integer> getRepartitionClientsParEmp() {
        String jpql = "SELECT e, COALESCE((SELECT COUNT(DISTINCT c.client) FROM e.consultations c WHERE c.terminee = 1), 0) as nbClients "
                    + "FROM Employe e "
                    + "ORDER BY nbClients DESC, e.prenom ASC";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Object[].class);
        List<Object[]> resultList = query.getResultList();
        
        Map<Employe, Integer> repartitionCli = new LinkedHashMap<>();
        for (Object[] result : resultList) {
            Employe emp = (Employe) result[0];
            Integer nbClients = (Integer) result[1];
            repartitionCli.put(emp, nbClients);
        }
        return repartitionCli;
    }
    
}