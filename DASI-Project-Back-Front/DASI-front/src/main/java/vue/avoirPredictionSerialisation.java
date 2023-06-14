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
public class avoirPredictionSerialisation extends Serialisation{
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        String etat = (String)request.getAttribute("etat");
        
        JsonArray tabPrediction = new JsonArray();
        List<String> predictions = (List<String>)request.getAttribute("predictions");
        for(int i = 0; i < predictions.size(); ++i) {
            JsonObject prediction = new JsonObject();
            prediction.addProperty("texte", predictions.get(i));
            tabPrediction.add(prediction);
        }
        container.addProperty("etat", etat);
        container.add("predictions", tabPrediction);

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
