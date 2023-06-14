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
import metier.services.ServiceClient;
import metier.services.ServiceConsultation;
import metier.services.ServiceEmploye;

public class prochaineConsultAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceEmploye s = new ServiceEmploye();
        Employe e = (Employe)session.getAttribute("employe");
        Consultation c = s.afficherProchaineConsultation(e);
        
        ServiceConsultation ser = new ServiceConsultation();
        List<Consultation> historiqueConsultations = ser.retournerHistoConsultationsClient(c.getClient());
        
        if (c != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("consultation", c);
            req.setAttribute("historiqueConsultations", historiqueConsultations);
        }else {
            req.setAttribute("etat", "false");
        }
    }
}