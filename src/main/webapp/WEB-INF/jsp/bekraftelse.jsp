<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Order skickad - Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stil.css">
</head>
<body>
<h1>Webbshop</h1>
<%@ include file="meny.jspf" %>

<h2>Tack för din order!</h2>

<p>Ditt ordernummer är <b>${senasteOrderId}</b>.</p>

<p><a href="${pageContext.request.contextPath}/produkter">Fortsätt handla</a></p>
</body>
</html>
