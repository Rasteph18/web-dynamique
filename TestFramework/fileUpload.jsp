<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.ArrayList" %>

<h3>Choisissez un fichier:</h3>

<form action="./upload" method="post" enctype="multipart/form-data">

    <label for="file">Le fichier</label>
    <input type="file" name="file" id="file">

    <button type="submit">Valider</button>

</form>

