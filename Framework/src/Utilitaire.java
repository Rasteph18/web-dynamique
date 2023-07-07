package utils;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.sql.Date;


public class Utilitaire {
    
    public Utilitaire()
    {

    }

    public String getRessource(HttpServletRequest req) throws ServletException, Exception
    {
        return req.getRequestURI().substring(req.getContextPath().length());
    }


    public ArrayList getClasses(File dossier, String packageName) throws Exception {
        String pckgName = packageName;
        ArrayList classes = new ArrayList();
        // Vérifie si le dossier existe
        if (dossier.exists()) {
            // Récupère la liste des fichiers et dossiers dans le dossier
            File[] fichiersEtDossiers = dossier.listFiles();
            if (fichiersEtDossiers != null) {
                // Parcours la liste des fichiers et dossiers
                for (File fichierOuDossier : fichiersEtDossiers) {
                    String fileName = fichierOuDossier.getName();
                    // Vérifie si c'est un dossier
                    if (fichierOuDossier.isDirectory()) {
                        // Appelle la méthode pour parcourir le sous-dossier
                        int start = fichierOuDossier.getPath().toString().indexOf("classes");
                        
                        pckgName = fichierOuDossier.getPath().toString().substring(start + 8);
                        //classes.add(pckgName);
                        classes.addAll(getClasses(fichierOuDossier, pckgName.replace("\\", ".")));
                    } else if(fileName.endsWith(".class")){
                        //Traite le fichier
                        String className = fileName.substring(0, fileName.lastIndexOf(".")); //enlève le ".class"
                        String fullClassName = packageName +"."+ className;

                        Class cls = Class.forName(fullClassName);
                        classes.add(cls);
                    }
                }
            }
        } else {
            throw new Exception("Le dossier n'existe pas");
        }

        return classes;
    }

    public Object cast(Class<?> parameterType, HttpServletRequest req, String n)
    {
        Object value = null;

        try{
            if (parameterType == String.class) {
                value = req.getParameter(n);
            } else if (parameterType == int.class || parameterType == Integer.class) {
                value = Integer.parseInt(req.getParameter(n));
            } else if (parameterType == double.class || parameterType == Double.class) {
                value = Double.parseDouble(req.getParameter(n));
            } else if (parameterType == float.class || parameterType == Float.class) {
                value = Float.parseFloat(req.getParameter(n));
            } else if (parameterType == boolean.class || parameterType == Boolean.class) {
                value = Boolean.parseBoolean(req.getParameter(n));
            } else if (parameterType == java.sql.Date.class) {
                String sDate = req.getParameter(n);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = format.parse(sDate); 
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                value = sqlDate;
            } else if (parameterType == java.util.Date.class) {
                String sDate = req.getParameter(n);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = format.parse(sDate);
                value = date;
            } 
        } catch(Exception e) {
            throw new IllegalArgumentException("Type de paramètre non géré : " + parameterType.getName());
        }
       

        return value;
    }

}
