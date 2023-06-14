/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.om.Client;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.Medium;
import metier.services.ServiceClient;
import metier.services.ServiceEmploye;
import metier.services.ServiceMedium;
/**
 *
 * @author mfournier
 */
public class RdvAction extends Action{
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ServiceMedium s = new ServiceMedium();
        Medium med = s.trouverMediumParDenom(req.getParameter("idMedium"));
        
        ServiceEmploye se = new ServiceEmploye();
        System.out.println(req.getParameter("idMedium"));
        Employe e = se.trouverEmployeDispo(med.getGenre());
        
        if (e != null) {
                ServiceClient sc = new ServiceClient();
                Date date = new Date();
                Time heure = new Time(date.getTime());
                Consultation c = sc.creerConsultation(med, e, (Client)session.getAttribute("client"), new Consultation(date, heure));
                if (c != null) {
                    req.setAttribute("etat", "true");
                }
                else {
                    req.setAttribute("etat", "false");
                }
            }else {
                req.setAttribute("etat", "false");
            }
    }
}