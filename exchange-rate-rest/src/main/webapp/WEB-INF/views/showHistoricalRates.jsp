<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Current Exchange Rates</title>
</head>
<body>
    <c:if test="${message != null}">
        <p>${message}</p>
    </c:if>

    <table border="1">
        <tr>
            <th>Currency</th>
            <th>Month 1</th>
            <th>Month 2</th>
            <th>Month 3</th>
            <th>Month 4</th>
            <th>Month 5</th>
            <th>Month 6</th>
        </tr>
        <c:forEach var="historicalRate" items="${historicalRates}">
            <tr>
                <td>${historicalRate.currencyCode}</td>
                <c:forEach var="rate" items="${historicalRate.rates}">
                    <td>${rate.rate}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
