package repolezanettiperuzzi.common;

import repolezanettiperuzzi.view.GameViewGUI;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class DynamicPath {

    private String path;

    public DynamicPath(String path){
        this.path = path;
    }

    public String getJarPath(){
        String currPath = System.getProperty("user.dir");
        String jarName = new File(GameViewGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
        return "jar:file:"+currPath+"/"+jarName+"!/"+path;
    }

    public String getDebugPath(){
        String currPath = System.getProperty("user.dir");
        return "file:"+currPath+"/"+path;
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
            return getClass().getResource("/"+path).toExternalForm();
        } else {
            return getDebugPath().substring(5);
        }
    }

    public String getPathJsonFile(){
        String currPath = System.getProperty("user.dir");
        if(isJar()){
            return currPath+"/playersinfo.json";
        } else {
            return getDebugPath().substring(5);
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
