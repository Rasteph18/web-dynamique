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
import modelView.*;

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
                        mapping.setClassName(cls.getName());
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

            String urlRessource = (String)util.getRessource(req);
            out.print(urlRessource + "\n");


            Mapping map = MappingUrls.get(urlRessource);

            if(map == null){
                throw new Exception("Introuvable!");
            }

            Class cl = Class.forName(map.getClassName());
            Object o = cl.getDeclaredConstructor().newInstance();
            
            Method m = o.getClass().getMethod(map.getMethod());
            Object object = m.invoke(o);
            if(object instanceof ModelView){
                ModelView model = (ModelView) object;
                HashMap<String, Object> data = model.getData();
                for(Map.Entry element : data.entrySet()){
                    req.setAttribute((String)element.getKey(), element.getValue());
                } 

                RequestDispatcher dispatch = req.getRequestDispatcher(model.getUrl());
                dispatch.forward(req, res);

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