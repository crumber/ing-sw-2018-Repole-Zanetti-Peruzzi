package repolezanettiperuzzi.common;

import repolezanettiperuzzi.view.GameViewGUI;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class DynamicPath {

    private String path;
    private static String OS = System.getProperty("os.name");

    public DynamicPath(String path){
        this.path = path;
    }

    public String getJarPath(){
        String currPath = System.getProperty("user.dir");
        String jarName = new File(GameViewGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
        String result = "jar:file:"+currPath+"/"+jarName+"!/"+path;
        return OS.startsWith("Windows") ? result.replace("\\", "/") : result;
    }

    public String getDebugPath(){
        String currPath = System.getProperty("user.dir");
        String result = "file:"+currPath+"/"+path;
        return OS.startsWith("Windows") ? result.replace("\\", "/") : result;
    }

    public String getPath(){
        if(isJar()){
            return getJarPath();
        } else {
            return getDebugPath();
        }
    }

    public String getPathNoFile(){
        if(isJar()){
            System.out.println(path);
            //System.out.println(getJarPath());
            //System.out.println(DynamicPath.class.getResourceAsStream("/"+path));
            String result = getClass().getResource("/"+path).toExternalForm();
            return OS.startsWith("Windows") ? result.replace("\\", "/") : result;
        } else {
            String result = getDebugPath().substring(5);
            return OS.startsWith("Windows") ? result.replace("\\", "/") : result;
        }
    }

    public String getPathJsonFile(){
        String currPath = System.getProperty("user.dir");
        if(isJar()){
            String result = currPath+"/playersinfo.json";
            return OS.startsWith("Windows") ? result.replace("\\", "/") : result;
        } else {
            String result = getDebugPath().substring(5);
            return result;
        }
    }

    public String getJarName(){
        if(isJar()){
            return new File(GameViewGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
        }
        return "";
    }

    public boolean isJar(){
        boolean isJar = false;
        try {
            isJar = DynamicPath.class.getResource("DynamicPath.class").toURI().getScheme().equals("jar");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return isJar;
    }
}
