package model;

import java.util.ArrayList;
import java.sql.Date;

import annotation.*;
import modelView.*;


public class Emp {

    String Nom;
    String Prenom;
    int NumDept;
    Date DateEmbauche;
   


    public Emp()
    {

    }

    public Emp(String nom, String prenom, int numDept, Date dateEmbauche)
    {
        setNom(nom);
        setPrenom(prenom);
        setNumDept(numDept);
        setDateEmbauche(dateEmbauche);
    }

    @AnnotationMethod(url="/getAllEmp")
    public ModelView getAllEmp()
    {
        ModelView modelView = new ModelView();
        modelView.setUrl("allEmp.jsp");
        ArrayList<String> listEmp = new ArrayList<String>();
        listEmp.add("Rakoto");
        listEmp.add("Rabe");
        
        modelView.addItem("nom", listEmp);

        return modelView;
    }

    @AnnotationMethod(url="/saveEmp")
    public ModelView save()
    {
        ModelView modelView = new ModelView();
        modelView.setUrl("saveEmp.jsp");
        modelView.addItem("emp", this);

        return modelView;
    }

    @AnnotationMethod(url="/insert", param="Nom,NumDept")
    public ModelView insertEmp(String nom, int numDept)
    {
        ModelView modelView = new ModelView();
        modelView.setUrl("insertEmp.jsp");
        modelView.addItem("nom", nom);
        modelView.addItem("numDept", numDept);

        return modelView;
    }

    @AnnotationMethod(url="/upload")
    public ModelView uploadFile()
    {
        ModelView modelView = new ModelView();
        modelView.setUrl("testUpload.jsp");

        return modelView;
    }


    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public int getNumDept() {
        return NumDept;
    }

    public void setNumDept(int numDept) {
        NumDept = numDept;
    }

    public Date getDateEmbauche() {
        return DateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        DateEmbauche = dateEmbauche;
    }


}