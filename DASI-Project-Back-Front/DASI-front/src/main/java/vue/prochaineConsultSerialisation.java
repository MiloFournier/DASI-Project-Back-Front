/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.om.Consultation;
import metier.om.Medium;
/**
 *
 * @author mfournier
 */
public class prochaineConsultSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        Consultation c = (Consultation)request.getAttribute("consultation");
        
        String nameClient = c.getClient().getPrenom();
        String nameMedium = c.getMedium().getDenomination();
        String date = c.getDateConsult().toString();
        
        JsonArray tabConsultation = new JsonArray();
        List<Consultation> historiqueConsultations = (List<Consultation>)request.getAttribute("historiqueConsultations");
        for(int i = 0; i < historiqueConsultations.size(); ++i) {
            JsonObject consultation = new JsonObject();
            consultation.addProperty("denominationMedium", historiqueConsultations.get(i).getMedium().getDenomination());
            consultation.addProperty("Date", historiqueConsultations.get(i).getDateConsult().toString());
            tabConsultation.add(consultation);
        }
        container.addProperty("etat", etat);
        
        container.add("historiqueConsultations", tabConsultation);
        
        container.addProperty("nameClient", nameClient);
        container.addProperty("nameMedium", nameMedium);
        container.addProperty("date", date);
        
        container.addProperty("zodiac", c.getClient().getProfilAstral().getSigneZodiaque());
        container.addProperty("chinois", c.getClient().getProfilAstral().getSigneChinois());
        container.addProperty("couleur", c.getClient().getProfilAstral().getCouleur());
        container.addProperty("animal", c.getClient().getProfilAstral().getAnimal());

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
