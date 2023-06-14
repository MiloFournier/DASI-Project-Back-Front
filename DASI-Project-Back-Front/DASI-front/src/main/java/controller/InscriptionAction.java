/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import metier.om.Client;
import metier.services.ServiceClient;

/**
 *
 * @author mfournier
 */
public class InscriptionAction extends Action{
    public void executer(HttpServletRequest req) {
        System.out.println("Ouverture de InscriptionAction et exécution de executer()");
        
        ServiceClient serC = new ServiceClient();
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String naissance = req.getParameter("naissance");
        String adresse = req.getParameter("adresse");
        String telephone = req.getParameter("telephone");
        String mail = req.getParameter("mail");
        String mdp = req.getParameter("mdp");
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNaissance = null;
        try {
            dateNaissance = formatter.parse(naissance);
        } catch (ParseException ex) {
            ex.printStackTrace(System.out);
        }
        
        Client c = new Client(nom, prenom, dateNaissance, adresse, telephone, mail);
        c.setMotDePasse(mdp);
        
        Client cTest = serC.inscriptionClient(c);
        if(cTest != null) {
            req.setAttribute("inscription", "Succès de l'inscription");
            //req.setAttribute("inscription", true);
        }else {
            req.setAttribute("inscription", "Echec de l'inscription");
        }
    }
}
