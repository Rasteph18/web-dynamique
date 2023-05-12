<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.*" %>


<h3>Formulaire</h3>
<form action="./saveEmp" method="post">
    <label for="nom">Nom</label>
    <input type="text" name="Nom" id="nom">

    <label for="prenom">Prenom</label>
    <input type="text" name="Prenom" id="prenom">

    <label for="numDept">NumDept</label>
    <input type="number" name="NumDept" id="numDept">

    <label for="dateEmbauche">Date d' embauche</label>
    <input type="date" name="DateEmbauche" id="dateEmbauche">

    <button type="submit">Valider</button>

</form>