<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.*" %>
<%@ page import = "utils.*" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.lang.Byte" %>


<h3>Fichier uploader</h3>
<%
    FileUpload upload = (FileUpload)request.getAttribute("upload");
%>

<p>Byte: <% out.print(upload.getTabByte().length); %></p>
<p>Chemin: <% out.print(upload.getPath()); %></p>
<p>Fichier: <% out.print(upload.getNameFile()); %></p>

