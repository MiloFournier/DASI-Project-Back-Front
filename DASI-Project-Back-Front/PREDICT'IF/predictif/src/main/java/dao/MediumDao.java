package dao;

import metier.om.Medium;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe permet d'accéder aux données de l'objet Médium et fournit
 * des opérations de base pour modifier / ajouter / supprimer / rechercher 
 * des données correspondant à un Médium.
 */
public class MediumDao {    
    
    // permet de persister un Médium dans la base de données
    public void create(Medium medium){
        JpaUtil.obtenirContextePersistance().persist(medium);
    }
    
    // permet de supprimer un Médium de la base de données
    public void delete(Medium medium){
        JpaUtil.obtenirContextePersistance().remove(medium);
    }
    
    // permet de mettre à jour les informations d'un Médium dans la base de données
    public void update(Medium medium){
        JpaUtil.obtenirContextePersistance().merge(medium);
    }

    // permet de trouver tous les Médiums persistés
    public List<Medium> findAll(){
        String s = "SELECT m FROM Medium m ORDER BY m.denomination";
        TypedQuery<Medium> query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return query.getResultList();
    }
    
    // permet de trouver tous les Médiums persistés du type et du genre entrés en paramètres
    public List<Medium> findAllByTypeandSex(String type, String genre){
        
        // requête en fonction des filtres donnés
        String s = "SELECT m FROM Medium m";
        
        // on peut avoir au plus 2 types différents selectionnés, sinon = 'Tous'
        if (!"Tous".equals(type)){
            s += " WHERE TYPE(m) = " + type;
        }
        
        // on peut avoir au plus 1 genre différent selectionné, sinon = 'Tous'
        if (!"Tous".equals(genre)){
            s += (type.equals("Tous") ? " WHERE" : " AND") + " m.genre = :sex";
        }
        
        s += " ORDER BY m.denomination";
        
        TypedQuery<Medium> query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        
        if (!"Tous".equals(genre)){
            query.setParameter("sex", genre);
        }
        
        return query.getResultList();
    }
    
    // permet de trouver le Médium correspondant à l'id entré en paramètre
    public Medium findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }

    // permet de trouver le Médium correspondant à la dénomination entrée en paramètre
    public Medium findByDenom(String denom) {
        Medium res;
        String s = "SELECT m FROM Medium m WHERE m.denomination = :nom ORDER BY m.denomination";
        TypedQuery<Medium> query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        query.setParameter("nom", denom);
        try {
            res = query.getSingleResult();
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
    
    // permet de déterminer le nombre de consultations par médium
    public Map<Medium, Integer> getConsultationsByMedium() {
        String jpql = "SELECT m, COALESCE(COUNT(c), 0) as numCons FROM Medium m LEFT JOIN m.consultations c ON c.terminee = 1 "
                    + "GROUP BY m ORDER BY numCons DESC, m.denomination ASC";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Object[].class);
        List<Object[]> resultList = query.getResultList();
        Map<Medium, Integer> consultationsByMedium = new LinkedHashMap<>();
        for (Object[] result : resultList) {
            Medium medium = (Medium) result[0];
            Integer count = (Integer) result[1];
            consultationsByMedium.put(medium, count);
        }
        return consultationsByMedium;
    }
    
    // permet de déterminer les 5 Médiums les plus demandés par les Clients
    public Map<Medium, Double> getTopFiveMediums() {
        String jpql = "SELECT m, (SELECT COUNT(c) FROM m.consultations c WHERE c.terminee = 1) * 100.0 / (SELECT COUNT(c) FROM Consultation c WHERE c.terminee = 1) as percentage "
                    + "FROM Medium m "
                    + "ORDER BY percentage DESC, m.denomination ASC";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Object[].class);
        query.setMaxResults(5);
        List<Object[]> resultList = query.getResultList();
        Map<Medium, Double> topMed = new LinkedHashMap<>();
        for (Object[] result : resultList) {
            Medium medium = (Medium) result[0];
            Double percentage = (Double) result[1];
            topMed.put(medium, percentage);
        }
        return topMed;
    }

}