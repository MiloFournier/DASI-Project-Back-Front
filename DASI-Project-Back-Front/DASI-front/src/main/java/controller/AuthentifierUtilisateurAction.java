/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.om.Client;
import metier.om.Employe;
import metier.services.ServiceClient;
import metier.services.ServiceEmploye;

/**
 *
 * @author mfournier
 */
public class AuthentifierUtilisateurAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        System.out.println("Ouverture de AuthentifierUtilisateurAction et exécution de executer()");
        
        ServiceEmploye serE = new ServiceEmploye();
        Employe e = serE.authentifierEmploye(req.getParameter("login"), req.getParameter("password"));
        
        
        if (e != null) {
            req.setAttribute("connexion", "true");
            req.setAttribute("utilisateur", e);
            session.setAttribute("employe", e);
        }else {
                req.setAttribute("connexion", "false");
                req.setAttribute("utilisateur", null);
        }
    }
    
    public void executerC(HttpServletRequest req) {
        System.out.println("Ouverture de AuthentifierUtilisateurAction et exécution de executer()");
        HttpSession session = req.getSession(true);
        ServiceClient serC = new ServiceClient();
        Client c = serC.authentifierClient(req.getParameter("login"), req.getParameter("password"));
        
        if (c != null) {
            req.setAttribute("connexion", "true");
            req.setAttribute("utilisateur", c);
            session.setAttribute("client", c);
        }else {
                req.setAttribute("connexion", "false");
                req.setAttribute("utilisateur", null);
        }
    }
}
