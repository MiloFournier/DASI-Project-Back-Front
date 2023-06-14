/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.om.Medium;
import metier.services.ServiceMedium;
/**
 *
 * @author mfournier
 */
public class CarrouselAction extends Action{
    public void executer(HttpServletRequest req) {
        System.out.println("Ouverture de CarrouselAction et exécution de executer()");
        
        ServiceMedium s = new ServiceMedium();
        List<Medium> listeMed = s.afficherListeAleatoireMedium();
        if (listeMed != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("denominationMedium" ,listeMed.get(0).getDenomination());
            req.setAttribute("genre" ,listeMed.get(0).getGenre());
            req.setAttribute("pres" ,listeMed.get(0).getPresentation());
        }else {
            req.setAttribute("etat", "false");
        }
    }
}
