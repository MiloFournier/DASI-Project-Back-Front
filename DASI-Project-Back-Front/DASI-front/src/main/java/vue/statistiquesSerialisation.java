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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.Medium;
/**
 *
 * @author mfournier
 */
public class statistiquesSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        String name = (String)request.getAttribute("name");
        
        JsonArray tabNbConsul = new JsonArray();
        Map<Medium, Integer> nbConsul = (Map<Medium, Integer>)request.getAttribute("nbConsul");
        Iterator it = nbConsul.entrySet().iterator();
        while (it.hasNext()) {
	        Map.Entry<Medium, Integer> entry = (Map.Entry)it.next();
            JsonObject nb = new JsonObject();
            nb.addProperty("denomination", entry.getKey().getDenomination());
            nb.addProperty("nombre", entry.getValue());
            tabNbConsul.add(nb);
        }


        JsonArray tabtop5 = new JsonArray();
        Map<Medium, Double> top5 = (Map<Medium, Double>)request.getAttribute("top5");
        it = top5.entrySet().iterator();
        while (it.hasNext()) {
	        Map.Entry<Medium, Double> entry = (Map.Entry)it.next();
            JsonObject top = new JsonObject();
            top.addProperty("denomination", entry.getKey().getDenomination());
            top.addProperty("nombre", entry.getValue());
            tabtop5.add(top);
        }

        JsonArray tabNbMedium = new JsonArray();
        Map<Employe, Long> nbMedium = (Map<Employe, Long>)request.getAttribute("nbMedium");
        it = nbMedium.entrySet().iterator();
        while (it.hasNext()) {
	        Map.Entry<Employe, Long> entry = (Map.Entry)it.next();
            JsonObject nb = new JsonObject();
            nb.addProperty("prenom", entry.getKey().getPrenom());
            nb.addProperty("nombre", entry.getValue());
            tabNbMedium.add(nb);
        }
        

        container.addProperty("etat", etat);
        container.addProperty("name", name);
        container.add("nbConsul", tabNbConsul);
        container.add("top5", tabtop5);
        container.add("nbMedium", tabNbMedium);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
