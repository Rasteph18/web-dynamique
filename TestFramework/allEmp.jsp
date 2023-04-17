<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.ArrayList" %>


<h3>Nos Employes: </h3>
<%
    ArrayList<Emp> list = (ArrayList<Emp>)request.getAttribute("nom");

%>

<%
    for(int i = 0; i < list.size(); i++)
    { %>
        <p><% out.print(list.get(i)); %></p>
    
 <% }
%>