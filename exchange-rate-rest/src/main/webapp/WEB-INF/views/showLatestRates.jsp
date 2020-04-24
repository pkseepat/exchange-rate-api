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
            <th>Current Rate</th>
        </tr>
        <c:forEach var="latestRate" items="${latestRates}">
            <tr>
                <td>${latestRate.currencyCode}</td>
                <td>${latestRate.rate}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>
            <button class="btn btn-primary">Historical Exchange Rates
            </button>
            </td>
        </tr>
    </table>
  </body>
</html>
