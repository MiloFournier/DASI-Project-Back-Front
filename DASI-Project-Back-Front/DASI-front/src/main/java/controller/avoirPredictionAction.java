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

public class avoirPredictionAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceEmploye s = new ServiceEmploye();
        Employe e = (Employe)session.getAttribute("employe");
        Consultation c = s.afficherProchaineConsultation(e); 
        List<String> li = s.demanderPredictions(c.getClient(),Integer.parseInt(req.getParameter("amour")), Integer.parseInt(req.getParameter("sante")), Integer.parseInt(req.getParameter("travail")), c);
        System.out.println(li.get(1));
        if (li != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("predictions", li);
        }else {
            req.setAttribute("etat", "false");
        }
    }
}