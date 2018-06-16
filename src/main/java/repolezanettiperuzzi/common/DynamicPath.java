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
