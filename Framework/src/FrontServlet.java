package etu1867.framework.servlet;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import annotation.AnnotationMethod;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.lang.Byte;
import javax.servlet.annotation.MultipartConfig;

import etu1867.framework.*;
import utils.FileUpload;
import utils.Utilitaire;
import modelView.*;

@MultipartConfig
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
            
            Method m = null;
            Method[] allMethod = o.getClass().getDeclaredMethods();
            for(Method met : allMethod)
            {
                if(map.getMethod().equals(met.getName()))
                {
                    m = met;
                }
            }
            //Object object = m.invoke(o);
            

            Enumeration<String> enume = req.getParameterNames();

            ModelView model = null;

            //sprint 7
            while(enume.hasMoreElements()) {
                String n = enume.nextElement();
                Field field = o.getClass().getDeclaredField(n);
                if(field == null){
                    continue;
                }

                Object value = null;
                Class<?> parameterType = o.getClass().getDeclaredMethod("set" + n , field.getType()).getParameterTypes()[0];

                value = util.cast(parameterType, req, n);

                o.getClass().getDeclaredMethod("set" + n, parameterType).invoke(o, value);
            }
            //out.print(o.getClass().getDeclaredMethod("save").invoke(o));


            //sprint 8
            ArrayList<Class<?>> typeParam = new ArrayList<Class<?>>();

            Class<?>[] param = m.getParameterTypes();
            ArrayList<Object> val = new ArrayList<>();
            if(param.length != 0)
            {
                typeParam.addAll(new ArrayList<>(Arrays.asList(param)));
                String[] p = m.getAnnotation(AnnotationMethod.class).param().split(",");
                for(int i = 0; i < param.length; i++)
                {
                    val.add(util.cast(param[i], req, p[i]));
                }
                model = (ModelView) o.getClass().getMethod(map.getMethod(), param).invoke(o, val.toArray());
            }
            else {
                model = (ModelView) o.getClass().getMethod(map.getMethod()).invoke(o);
            }
            
            //sprint 9
            String contentType = req.getContentType();
            if(contentType != null && contentType.startsWith("multipart/form-data")){
                for(Part part : req.getParts())
                {
                    String nameFile = util.getFileName(part);
                    String path = util.getFilePath(part);
                    byte[] tabByte = util.getFileBytes(part);

                    FileUpload upload = new FileUpload();
                    upload.setNameFile(nameFile);
                    upload.setPath(path);
                    upload.setTabByte(tabByte);
                    
                    model.addItem("upload", upload);
                }

            }

            
            //if(object instanceof ModelView){
                //ModelView model = (ModelView) object;
                HashMap<String, Object> data = model.getData();
                for(Map.Entry element : data.entrySet()){
                    req.setAttribute((String)element.getKey(), element.getValue());
                } 
            //}

            RequestDispatcher dispatch = req.getRequestDispatcher(model.getUrl());
            dispatch.forward(req, res);
            

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