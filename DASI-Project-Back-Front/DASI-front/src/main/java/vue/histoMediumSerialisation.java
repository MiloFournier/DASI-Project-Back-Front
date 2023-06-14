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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.om.Consultation;
import metier.om.Medium;
/**
 *
 * @author mfournier
 */
public class histoMediumSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        
        JsonArray tabMedium = new JsonArray();
        Map<Medium, Long> historiqueMedium = (Map<Medium, Long>)request.getAttribute("historiqueMedium");
        for(Medium m: historiqueMedium.keySet()) {
            Long nb = historiqueMedium.get(m);
            JsonObject medium = new JsonObject();
            medium.addProperty("denominationMedium", m.getDenomination());
            medium.addProperty("nb", nb);
            tabMedium.add(medium);
        }
        container.addProperty("etat", etat);
        container.add("historiqueConsultations", tabMedium);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
