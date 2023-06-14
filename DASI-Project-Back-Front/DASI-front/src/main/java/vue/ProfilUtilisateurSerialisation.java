/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.om.Client;
import metier.om.Employe;

/**
 *
 * @author mfournier
 */
public class ProfilUtilisateurSerialisation extends Serialisation {
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        Employe user = (Employe)request.getAttribute("utilisateur");
        String conn = (String)request.getAttribute("connexion");
        
        container.addProperty("connexion", conn);
        if(user != null) {
            JsonObject jsonUser= new JsonObject();
            jsonUser.addProperty("id", user.getId());
            jsonUser.addProperty("prenom", user.getPrenom());
            jsonUser.addProperty("nom", user.getNom());
            jsonUser.addProperty("mail", user.getMail());
            container.add("utilisateur", jsonUser);
        }else{
            container.addProperty("utilisateur", "Erreur authentification");
        }
        
        System.out.println("Ouverture de ProfilUtilisateurSerialisation et exécution de serialiser()");

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
    public void serialiserC(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        Client user = (Client)request.getAttribute("utilisateur");
        String conn = (String)request.getAttribute("connexion");
        
        container.addProperty("connexion", conn);
        if(user != null) {
            JsonObject jsonUser= new JsonObject();
            jsonUser.addProperty("id", user.getId());
            jsonUser.addProperty("prenom", user.getPrenom());
            jsonUser.addProperty("nom", user.getNom());
            jsonUser.addProperty("mail", user.getMail());
            container.add("utilisateur", jsonUser);
        }else{
            container.addProperty("utilisateur", "Erreur authentification");
        }
        
        System.out.println("Ouverture de ProfilUtilisateurSerialisation et exécution de serialiser()");
  
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }   
}
