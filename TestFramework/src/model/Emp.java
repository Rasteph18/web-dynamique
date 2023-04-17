package model;

import java.util.ArrayList;

import annotation.*;
import modelView.*;


public class Emp {

    String nom;
    String prenom;

    public Emp()
    {

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

}