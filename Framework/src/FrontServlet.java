package etu1867.framework.servlet;

import java.io.*;
import java.lang.reflect.Method;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import annotation.AnnotationMethod;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import etu1867.framework.*;
import utils.Utilitaire;

public class FrontServlet extends HttpServlet {

    HashMap<String,Mapping> MappingUrls;
    Utilitaire util;

    @Override
    public void init() throws ServletException {
        try {
            util = new Utilitaire();
            MappingUrls = new HashMap<>();
            File file = new File(getServletContext().getResource("/WEB-INF/classes").getFile().replace("%20", " "));

            ArrayList<Class> classInPackage = util.getClasses(file, null);

            Mapping mapping;

            for(int i = 0; i < classInPackage.size(); i++)
            {
                Class cls = classInPackage.get(i);
                Method[] allMethod = cls.getMethods();

                for(Method m : allMethod)
                {
                    if(m.isAnnotationPresent(AnnotationMethod.class))
                    {
                        mapping = new Mapping();
                        mapping.setClassName(cls.getSimpleName());
                        mapping.setMethod(m.getName());

                        MappingUrls.put(m.getAnnotation(AnnotationMethod.class).url(), mapping);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        PrintWriter out  = res.getWriter();
        try {
            util = new Utilitaire();
            out.print(util.getRessource(req)+"\n");
            
            for(Map.Entry m : MappingUrls.entrySet())
            {
                Mapping mapping = (Mapping)m.getValue();
                out.print("\n\n url: " + m.getKey() + "\t class: " + mapping.getClassName() + "\n \t\t method: " + mapping.getMethod());   
            }
             

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        processRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        processRequest(req, res);
    }
}