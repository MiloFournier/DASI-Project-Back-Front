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
import metier.om.Medium;
/**
 *
 * @author mfournier
 */
public class ConsultationSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        
        JsonArray tabMedium = new JsonArray();
        List<Medium> li = (List<Medium>)request.getAttribute("mediums");
        for(int i = 0; i < li.size(); ++i) {
            JsonObject medium = new JsonObject();
            medium.addProperty("id", li.get(i).getID());
            medium.addProperty("denominationMedium", li.get(i).getDenomination());
            medium.addProperty("genreMedium", li.get(i).getGenre());
            medium.addProperty("presentationMedium", li.get(i).getPresentation());
            tabMedium.add(medium);
        }
        container.addProperty("etat", etat);
        container.add("mediums", tabMedium);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}