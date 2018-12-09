<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %> 
    <%@ page import="java.sql.Connection.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Kasvitietoa</title>
</head>
<body>
<h1>Kasvitieto</h1>
<p>Tällä sivulla voit oppia kasveista.</p>
<br>

<%

try {
	
	//Muodostetaan yhteys tietokantaan
	System.out.println("Yhdistetään tietokantaan....");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasvit", "root", "");
	Statement stmt = con.createStatement();
	System.out.println("Yhteys muodostettu.");
	
	//Valitaan tietokannasta tulostettava joukko
	ResultSet joukko = stmt.executeQuery("SELECT * FROM kasvit");

	//Tulostetaan valittu sisältö näytölle
	while (joukko.next())
		out.println(joukko.getString(1) + "  " + joukko.getString(2));
	
	//Lopetetaan yhteys
	con.close();

	//Etsitään mahdollisia virheitä
} catch (Exception e) {
	System.out.println(e);
}
%>

</body>
</html>