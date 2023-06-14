/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.om.Client;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.Medium;
import metier.om.ProfilAstral;
import metier.services.ServiceClient;
import metier.services.ServiceConsultation;
import metier.services.ServiceEmploye;
import metier.services.ServiceMedium;

public class statistiquesAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Employe c = (Employe)session.getAttribute("employe");
        ServiceMedium s = new ServiceMedium();
        ServiceEmploye sEmp = new ServiceEmploye();
        
        Map<Medium, Integer> nbConsul = s.trouverConsultationsParMedium();
        Map<Medium, Double> top5 = s.trouverMediumsPlusConsultes();
        Map<Employe, Integer> nbMedium = sEmp.trouverRepartitionClientsParEmp();
        
        if (nbConsul != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("nbConsul", nbConsul);
            req.setAttribute("top5", top5);
            req.setAttribute("nbMedium", nbMedium);
            req.setAttribute("name", c.getPrenom());
        }else {
            req.setAttribute("etat", "false");
        }
    }
}