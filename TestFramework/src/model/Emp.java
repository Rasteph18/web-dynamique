package model;

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

        return modelView;
    }

}