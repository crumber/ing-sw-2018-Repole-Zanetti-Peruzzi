package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.DynamicPath;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Classe che rappresenta lo stato di reset del game cioòè pulizia file Json
 * @author Giampiero Repole
 */
public class ResetGameState extends ControllerState{

    private DynamicPath dP;

    /**
     * Pulisce file Json
     * @param controller Controller
     */
    @Override
    public void doAction(Controller controller) {
        this.dP = new DynamicPath("gamedata/playersinfo.json");
        handleJson();
        cleanJson("gamedata/playersinfo.json");
    }

    /**
     * Prende risorse dal Jar
     */
    public void handleJson(){
        if(dP.isJar()){
            try {
                //extractResourceFromJar("/gamedata/playersinfo.json");
                //Files.copy(Paths.get(dP.getPath()), Paths.get(System.getProperty("user.dir");), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pulisce il Json
     * @param jsonPath Percorso Json
     */
    //method to clean the json file every time the game restarts
    private void cleanJson(String jsonPath){
        JSONArray jsonArr = new JSONArray();
        try (FileWriter file = new FileWriter(new DynamicPath(jsonPath).getPathJsonFile())) {
            file.write(jsonArr.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot write on file");
        }
    }

    /**
     * Estrae le risorse dal Jar
     * @param resourceName Nome Jar
     * @return Stringa che rappresenta i parametri del Jar
     * @throws Exception Non riesce ha prendere le risorse dal Jar
     */
    public String extractResourceFromJar(String resourceName) throws Exception {
        InputStream stream = null;
        String jarFolder;
        try {
            stream = ResetGameState.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(ResetGameState.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            try(FileOutputStream resStreamOut = new FileOutputStream(jarFolder + resourceName);){
                while ((readBytes = stream.read(buffer)) > 0) {
                    resStreamOut.write(buffer, 0, readBytes);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(stream!=null) {
                stream.close();
            }
        }

        return jarFolder + resourceName;
    }
}
