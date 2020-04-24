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
<form:form class="col-12" method="POST" action="${pageContext.request.contextPath}/api/rates" modelAttribute="rate">

<table border="1">
        <tr>
            <th>Currency</th>
            <th>Current Rate</th>
        </tr>
        <c:forEach var="latestRate" items="${latestRates}">
            <tr>
                <td>${latestRate.key}</td>
                <td>${latestRate.value}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>
                <form:hidden path="days" value="6"/>
            <form:button class="btn" type="submit">Historical Exchange Rates</form:button>
            </button>
            </td>
        </tr>
    </table>
</form:form>
  </body>
</html>
