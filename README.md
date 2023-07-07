# REMARQUE
- Utilisez une version de Tomcat 9.0


# UTILISATION
- Copier dans **WEB-INF/lib** le fichier .jar (stephframe.jar)
- Créer un dossier **src** à coté de **WEB-INF**
- Dans **src** créer votre classe java
- Dans **WEB-INF/web.xml**, metez le comme suit:
	```xml
	<servlet>
	<servlet-name>frontServlet</servlet-name>
	<servlet-class>etu1867.framework.servlet.FrontServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>frontServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	```
	
- Créer vos fichier .jsp à coté des dossiers **src** et **WEB-INF**
## Pour envoyer vos données vers une Vue
- créer une classe dans **src**
- il faut que les noms de vos attributs commence par une majuscule
- dans cette classe, 
	- faire des importations tel que: annotation.* et modelView.*
	- créer une fonction de type ModelView 
		- dans la fonction instancier une ModelView comme: ModelView m = new ModelView()
		- m.setUrl("monVue.jsp") pour preciser quel vue sera afficher apres le passage dans l'url
		- m.addItem("nom", Object) pour envoyer des données vers votre Vue
		- retourner m à la fin

	- annoter votre fonction comme ceci par exemple: @AnnotationMethod(url="/getAllEmp")
	- c'est **/getAllEmp** qui doit etre écrit sur l'url pour appeler votre vue

	> Voici un exemple de code:
	```java
	package model;

	import java.util.ArrayList;
	import annotation.*;
	import modelView.*;

	public class Emp {
		String Nom;
		String Prenom;
		int NumDept;
		Date DateEmbauche;

		@AnnotationMethod(url="/getAllEmp")
		public ModelView getAllEmp()
		{
			ModelView modelView = new ModelView();
			modelView.setUrl("allEmp.jsp");
			ArrayList<String> listEmp = new ArrayList<String>();
			listEmp.add("Rakoto");
			listEmp.add("Rabe");
			
			modelView.addItem("nom", listEmp);

			return modelView;
		}

	}
	```

## Pour récupérer des données depuis votre Vue avec les attributs de votre classe comme données
- il faut que les données envoyés soient de même nom que les attributs de votre classe, soit **"name=Nom"** par exemple dans le fichier **jsp**.
- il faut annoter la fonction, qui sert à appeler votre fonction
	>Exemple de code dans la classe **Emp**
	```java
	@AnnotationMethod(url="/saveEmp")
	public ModelView save()
	{
		ModelView modelView = new ModelView();
		modelView.setUrl("saveEmp.jsp");
		modelView.addItem("emp", this);

		return modelView;
	}
	```

## Pour récupérer des données depuis votre Vue mais pas forcément avec les attributs de votre classe.
- il faut créer une fonction de type ModelView qui possède un ou plusieurs paramètres 
- il faut l'annoter comme ceci:  **@AnnotationMethod(url="/insert", param="Nom,NumDept")**, avec la valeur de **param** les noms des input, séparer par une virgule, qu'on va utiliser dans un formulaire par exemple
	>Exemple de code:
	```java
	@AnnotationMethod(url="/insert", param="Nom,NumDept")
	public ModelView insertEmp(String nom, int numDept)
	{
		ModelView modelView = new ModelView();
		modelView.setUrl("insertEmp.jsp");
		modelView.addItem("nom", nom);
		modelView.addItem("numDept", numDept);

		return modelView;
	}
	```

## Pour faire un upload de fichier
- il faut juste que vous creer un **input de type file** dans votre formulaire
- pour récupérer les information sur ce fichier, il faut procéder comme suite dans le fichier jsp:
```jsp
	<%@ page import = "utils.*" %>
	FileUpload upload = (FileUpload)request.getAttribute("upload");

	<p>Byte: <% out.print(upload.getTabByte().length); %></p>
	<p>Chemin: <% out.print(upload.getPath()); %></p>
	<p>Fichier: <% out.print(upload.getNameFile()); %></p>
```