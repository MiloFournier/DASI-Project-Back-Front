package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controller.AuthentifierUtilisateurAction;
import controller.CarrouselAction;
import controller.ConsultationAction;
import controller.InscriptionAction;
import controller.RdvAction;
import controller.astralAction;
import controller.avoirPredictionAction;
import controller.commentaireAction;
import controller.finConsultAction;
import controller.histoAction;
import controller.histoMediumAction;
import controller.prochaineConsultAction;
import controller.statistiquesAction;
import controller.validerConsultationAction;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vue.CarrouselSerialisation;
import vue.ConsultationSerialisation;
import vue.InscriptionSerialisation;
import vue.ProfilUtilisateurSerialisation;
import vue.RdvSerialisation;
import vue.astralSerialisation;
import vue.avoirPredictionSerialisation;
import vue.commentaireSerialisation;
import vue.finConsultSerialisation;
import vue.histoMediumSerialisation;
import vue.histoSerialisation;
import vue.prochaineConsultSerialisation;
import vue.statistiquesSerialisation;
import vue.validerConsultationSerialisation;

/**
 *
 * @author mfournier
 */
@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
    }
    
    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Date d = new Date();
        
        System.out.println("Test");
        
        String todo = request.getParameter("todo");
        
        switch(todo){
            case "authentifier" : {
                new AuthentifierUtilisateurAction().executer(request);
                new ProfilUtilisateurSerialisation().serialiser(request, response);
                break;
            }
            case "authentifierC" : {
                new AuthentifierUtilisateurAction().executerC(request);
                new ProfilUtilisateurSerialisation().serialiserC(request, response);
                break;
            }
            case "inscription" : {
                new InscriptionAction().executer(request);
                new InscriptionSerialisation().serialiser(request, response);
                break;
            }
            case "carrousel" : {
                new CarrouselAction().executer(request);
                new CarrouselSerialisation().serialiser(request, response);
                break;
            }
            case "consultations" : {
                new ConsultationAction().executer(request);
                new ConsultationSerialisation().serialiser(request, response);
                break;
            }
            case "rdv" : {
                new RdvAction().executer(request);
                new RdvSerialisation().serialiser(request, response);
                break;
            }
            case "historiqueConsultations" : {
                new histoAction().executer(request);
                new histoSerialisation().serialiser(request, response);
                break;
            }
            case "historiqueMedium" : {
                new histoMediumAction().executer(request);
                new histoMediumSerialisation().serialiser(request, response);
                break;
            }
            case "profilAstral" : {
                new astralAction().executer(request);
                new astralSerialisation().serialiser(request, response);
                break;
            }
            case "prochaineConsult" : {
                new prochaineConsultAction().executer(request);
                new prochaineConsultSerialisation().serialiser(request, response);
                break;
            }
            case "finConsult" : {
                new finConsultAction().executer(request);
                new finConsultSerialisation().serialiser(request, response);
                break;
            }
            case "commentaire" : {
                new commentaireAction().executer(request);
                new commentaireSerialisation().serialiser(request, response);
                break;
            }
            case "validerConsultation" : {
                new validerConsultationAction().executer(request);
                new validerConsultationSerialisation().serialiser(request, response);
                break;
            }

            case "AvoirPrediction" : {
                new avoirPredictionAction().executer(request);
                new avoirPredictionSerialisation().serialiser(request, response);
                break;
            }
            
            case "statistiques" : {
                new statistiquesAction().executer(request);
                new statistiquesSerialisation().serialiser(request, response);
                break;
            }
            
            
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            out.println("<p>" + d + "</p>");
            System.out.println("[TEST] Appel de l'ActionServlet");
            System.out.println(request.getParameter("todo"));
            out.println("</body>");
            out.println("</html>");*/
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
