<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.ArrayList" %>

<h3>Inserer avec succes</h3>
<%
    Emp e = (Emp)request.getAttribute("emp");
%>

<p>Nom: <% out.print(e.getNom()); %></p>
<p>Prenom: <% out.print(e.getPrenom()); %></p>
<p>Numero dept: <% out.print(e.getNumDept()); %></p>
<p>Date d' embauche: <% out.print(e.getDateEmbauche()); %></p>
