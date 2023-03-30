package model;

import java.sql.Date;

import annotation.*;


public class Emp {

    String nom;
    String prenom;
    Date dtn;

    public Emp()
    {

    }

    @AnnotationMethod(url="get-All-Emp")
    public void getAllEmp()
    {

    }

    @AnnotationMethod(url="add-Emp")
    public void addEmp()
    {

    }

}