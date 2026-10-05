<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Produkter - Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stil.css">
</head>
<body>
<h1>Webbshop</h1>
<%@ include file="meny.jspf" %>

<h2>Produkter</h2>

<c:if test="${not empty felmeddelande}">
    <p class="fel"><c:out value="${felmeddelande}"/></p>
</c:if>

<c:if test="${not empty meddelande}">
    <p><b><c:out value="${meddelande}"/></b></p>
</c:if>

<table>
    <tr>
        <th>Produkt</th>
        <th>Beskrivning</th>
        <th>Pris</th>
        <th>Lager</th>
        <th>Antal</th>
        <c:if test="${anvandare.roll == 'LAGER'}">
            <th>Fyll på lager</th>
        </c:if>
    </tr>
    <c:forEach var="produkt" items="${produkter}">
        <tr>
            <td><c:out value="${produkt.namn}"/></td>
            <td><c:out value="${produkt.beskrivning}"/></td>
            <td>${produkt.pris} kr</td>
            <c:choose>
                <c:when test="${produkt.lagerAntal > 0}">
                    <td>I lager (${produkt.lagerAntal} st)</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/varukorg">
                            <input type="hidden" name="produktId" value="${produkt.id}">
                            <input type="text" name="antal" value="1" size="3">
                            <input type="submit" value="Lägg i korgen">
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td class="fel">Slut i lager</td>
                    <td></td>
                </c:otherwise>
            </c:choose>
            <c:if test="${anvandare.roll == 'LAGER'}">
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/produkter">
                        <input type="hidden" name="produktId" value="${produkt.id}">
                        <input type="text" name="antal" size="5">
                        <input type="submit" value="Fyll på">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
