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
public class astralSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        
        String zodiac = (String)request.getAttribute("zodiac");
        String chinois = (String)request.getAttribute("chinois");
        String couleur = (String)request.getAttribute("couleur");
        String animal = (String)request.getAttribute("animal");
        String name = (String)request.getAttribute("name");
        
        container.addProperty("etat", etat);
        container.addProperty("zodiac", zodiac);
        container.addProperty("chinois", chinois);
        container.addProperty("couleur", couleur);
        container.addProperty("animal", animal);
        container.addProperty("name", name);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
