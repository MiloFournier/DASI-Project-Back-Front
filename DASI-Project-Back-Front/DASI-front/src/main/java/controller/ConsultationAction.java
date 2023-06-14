/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.om.Medium;
import metier.services.ServiceMedium;
/**
 *
 * @author mfournier
 */
public class ConsultationAction extends Action{
    public void executer(HttpServletRequest req) {
        if(req.getParameter("champNom").equals("null")) {
            ServiceMedium s = new ServiceMedium();
            List<Medium> listeMed = s.afficherTousMediumsOrdreAlpha();
            if (listeMed != null) {
                req.setAttribute("etat", "true");
                req.setAttribute("mediums", listeMed);
            }else {
                req.setAttribute("etat", "false");
            }
        } else {
               ServiceMedium s = new ServiceMedium(); 
               Medium med = s.trouverMediumParDenom(req.getParameter("champNom"));
               List<Medium> listeMed = new ArrayList<Medium>();
               listeMed.add(med);
               if(med != null) {
                   req.setAttribute("etat", "true");
                   req.setAttribute("mediums", listeMed);
               }else {
                req.setAttribute("etat", "false");
               }
        }
    }
}