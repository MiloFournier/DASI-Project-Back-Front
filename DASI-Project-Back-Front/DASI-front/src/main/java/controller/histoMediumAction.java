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
import metier.om.Medium;
import metier.services.ServiceClient;
import metier.services.ServiceConsultation;

public class histoMediumAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceClient s = new ServiceClient();
        Map<Medium, Long> historiqueMedium = s.trouverMediumsFavoris((Client)session.getAttribute("client"));
        
        if (historiqueMedium != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("historiqueMedium", historiqueMedium);
        }else {
            req.setAttribute("etat", "false");
        }
    }
}