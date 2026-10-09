<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Varukorg - Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stil.css">
</head>
<body>
<h1>Webbshop</h1>
<%@ include file="meny.jspf" %>

<h2>Din varukorg</h2>

<c:if test="${not empty felmeddelande}">
    <p class="fel"><c:out value="${felmeddelande}"/></p>
</c:if>

<c:choose>
    <c:when test="${varukorgInfo.tom}">
        <p>Varukorgen är tom.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Produkt</th>
                <th>Pris</th>
                <th>Antal</th>
                <th>Summa</th>
            </tr>
            <c:forEach var="rad" items="${varukorgInfo.rader}">
                <tr>
                    <td><c:out value="${rad.produkt.namn}"/></td>
                    <td>${rad.produkt.pris} kr</td>
                    <td>${rad.antal}</td>
                    <td>${rad.radSumma} kr</td>
                </tr>
            </c:forEach>
            <tr class="total">
                <td>Totalt</td>
                <td></td>
                <td>${varukorgInfo.antalVaror}</td>
                <td>${varukorgInfo.totalSumma} kr</td>
            </tr>
        </table>

        <form method="post" action="${pageContext.request.contextPath}/order">
            <p><input type="submit" value="Skicka order"></p>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>
