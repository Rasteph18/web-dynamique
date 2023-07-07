<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.ArrayList" %>

<h3>Insert</h3>
<%
    String nom = (String)request.getAttribute("nom");
    int numDept = (int)request.getAttribute("numDept");
%>

<p>Nom: <% out.print(nom); %></p>
<p>Numero dept: <% out.print(numDept); %></p>