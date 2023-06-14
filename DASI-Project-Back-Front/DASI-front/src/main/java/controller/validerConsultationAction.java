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

public class validerConsultationAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceEmploye s = new ServiceEmploye();
        Employe e = (Employe)session.getAttribute("employe");
        Consultation c1 = s.afficherProchaineConsultation(e);
        
        Consultation c = s.validerFinConsultation(e,c1);
        
        if (c != null) {
            req.setAttribute("etat", "true");
        }else {
            req.setAttribute("etat", "false");
        }
    }
}