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
import metier.om.ProfilAstral;
import metier.services.ServiceClient;
import metier.services.ServiceConsultation;

public class astralAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceClient s = new ServiceClient();
        Client c = (Client)session.getAttribute("client");
        ProfilAstral p = (c).getProfilAstral();
        
        if (p != null) {
            req.setAttribute("etat", "true");
            req.setAttribute("zodiac", p.getSigneZodiaque());
            req.setAttribute("chinois", p.getSigneChinois());
            req.setAttribute("couleur", p.getCouleur());
            req.setAttribute("animal", p.getAnimal());
            req.setAttribute("name", c.getPrenom());
        }else {
            req.setAttribute("etat", "false");
        }
    }
}