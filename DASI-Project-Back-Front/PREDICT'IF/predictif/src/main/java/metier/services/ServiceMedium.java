package metier.services;

import dao.JpaUtil;
import dao.MediumDao;
import metier.om.Medium;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe fournit des services relatifs aux Médiums.
 * Elle permet de les afficher sous forme de liste ordonnée alphabétiquement,
 * ou sous forme de carrousel grâce à une liste aléatoire.
 */
public class ServiceMedium {
    
    public ServiceMedium() {
        
    }
    
    // permet de trouver la liste complète des Médiums de l'application,
    // ordonnés par leur dénomination
    public List<Medium> afficherTousMediumsOrdreAlpha(){
        List<Medium> listMed = null;
        MediumDao medDao = new MediumDao();
        try{
            JpaUtil.creerContextePersistance();
            listMed = medDao.findAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return listMed; 
    }
    
    // permet de trouver la liste complète des Médiums, mais non-ordonnée
    // utile pour la découverte des Médiums sous forme de carrousel
    public List<Medium> afficherListeAleatoireMedium(){
        MediumDao medDao = new MediumDao();
        List<Medium> listeMed = null;
        try{
            JpaUtil.creerContextePersistance();
            listeMed = medDao.findAll();
            
            // mélange aléatoire de la liste
            Collections.shuffle(listeMed);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return listeMed;
    }
    
    // permet de ne trouver que les Médiums qui correspondent aux filtres
    // choisis par l'utilisateurs
    // types correspond à l'ensemble des types voulus : 
    // soit 1, 2 ou 3 parmi Astrologue, Cartomancien et Spirite ; soit Tous
    // genre correspond au genre voulu : 
    // soit Homme, soit Femme, soit Tous
    public List<Medium> filtrerMediums(List<String> types, String genre){
        MediumDao medDao = new MediumDao();
        List<Medium> listMed = new ArrayList<>();
        try{
            JpaUtil.creerContextePersistance();
            
            for (String type : types){
                listMed.addAll(medDao.findAllByTypeandSex(type, genre));
            }
                   
        }
        catch (Exception e){
            e.printStackTrace();
            listMed = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return listMed;
    }  

    // permet de trouver le Médium correspondant à la dénomination denom entrée en paramètre 
    public Medium trouverMediumParDenom(String denom) {
        Medium medium = null;
        MediumDao medDao = new MediumDao();
        try{
            JpaUtil.creerContextePersistance();
            medium = medDao.findByDenom(denom);
        }
        catch (Exception e){
            e.printStackTrace();
            medium = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return medium;
    }
    
    // permet de déterminer le nombre de consultations par Médium pour le graphique
    public Map<Medium, Integer> trouverConsultationsParMedium(){
        MediumDao medDao = new MediumDao();
        Map<Medium, Integer> consParMed = null;
        try{
            JpaUtil.creerContextePersistance();
            consParMed = medDao.getConsultationsByMedium();
        }
        catch (Exception e){
            e.printStackTrace();
            consParMed = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consParMed;
    }
    
    // permet de déterminer les 5 Médiums les plus consultés par les Clients
    public Map<Medium, Double> trouverMediumsPlusConsultes(){
        MediumDao medDao = new MediumDao();
        Map<Medium, Double> medPref = null;
        try{
            JpaUtil.creerContextePersistance();
            medPref = medDao.getTopFiveMediums();
        }
        catch (Exception e){
            e.printStackTrace();
            medPref = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return medPref;
    }
    
}