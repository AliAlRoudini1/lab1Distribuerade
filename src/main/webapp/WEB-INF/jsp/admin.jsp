<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Administrera användare - Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stil.css">
</head>
<body>
<h1>Webbshop</h1>
<%@ include file="meny.jspf" %>

<h2>Administrera användare</h2>

<c:if test="${not empty felmeddelande}">
    <p class="fel"><c:out value="${felmeddelande}"/></p>
</c:if>

<table>
    <tr>
        <th>Användarnamn</th>
        <th>Roll</th>
        <th></th>
    </tr>
    <c:forEach var="konto" items="${allaAnvandare}">
        <tr>
            <td><c:out value="${konto.anvandarnamn}"/></td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin">
                    <input type="hidden" name="atgard" value="andraRoll">
                    <input type="hidden" name="id" value="${konto.id}">
                    <select name="roll">
                        <c:forEach var="roll" items="${roller}">
                            <option value="${roll}" <c:if test="${roll == konto.roll}">selected</c:if>>${roll}</option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="Ändra roll">
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin">
                    <input type="hidden" name="atgard" value="taBort">
                    <input type="hidden" name="id" value="${konto.id}">
                    <input type="submit" value="Ta bort">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Lägg till användare</h3>
<form method="post" action="${pageContext.request.contextPath}/admin">
    <input type="hidden" name="atgard" value="laggTill">
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
            <td>Roll</td>
            <td>
                <select name="roll">
                    <c:forEach var="roll" items="${roller}">
                        <option value="${roll}">${roll}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Lägg till"></td>
        </tr>
    </table>
</form>
</body>
</html>
