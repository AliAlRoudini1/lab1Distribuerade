<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Logga in - Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stil.css">
</head>
<body>
<h1>Webbshop</h1>
<h2>Logga in</h2>

<c:if test="${not empty felmeddelande}">
    <p class="fel"><c:out value="${felmeddelande}"/></p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/loggain">
    <table>
        <tr>
            <td>Användarnamn</td>
            <td><input type="text" name="anvandarnamn"></td>
        </tr>
        <tr>
            <td>Lösenord</td>
            <td><input type="password" name="losenord"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Logga in"></td>
        </tr>
    </table>
</form>
</body>
</html>
