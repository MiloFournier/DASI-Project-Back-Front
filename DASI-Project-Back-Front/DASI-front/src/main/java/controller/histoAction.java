/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.om.Client;
import metier.om.Consultation;
import metier.services.ServiceConsultation;

public class histoAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceConsultation s = new ServiceConsultation();
        List<Consultation> historiqueConsultations = s.retournerHistoConsultationsClient((Client)session.getAttribute("client"));
        
        if (historiqueConsultations != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("historiqueConsultations", historiqueConsultations);
        }else {
            req.setAttribute("etat", "false");
        }
    }
}