package etu1867.framework.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import etu1867.framework.*;

public class FrontServlet extends HttpServlet {

    HashMap<String,Mapping> MappingUrls;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        PrintWriter out  = res.getWriter();
        try {
            out.print(req.getRequestURI().substring(req.getContextPath().length()));
            
        } catch (Exception e) {
            out.print(e);
            e.printStackTrace();
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