package utils;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;


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

}
