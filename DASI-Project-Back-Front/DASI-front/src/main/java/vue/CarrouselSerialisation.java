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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.om.Medium;
/**
 *
 * @author mfournier
 */
public class CarrouselSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        String denominationMedium = (String)request.getAttribute("denominationMedium");
        String genre = (String)request.getAttribute("genre");
        String pres = (String)request.getAttribute("pres");
        
        container.addProperty("etat", etat);
        container.addProperty("denominationMedium", denominationMedium);
        container.addProperty("genre", genre);
        container.addProperty("pres", pres);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
