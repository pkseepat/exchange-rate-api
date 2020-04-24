<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Promotion UI</title>
    <link href="${pageContext.request.contextPath}/resources/css/rates.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
  </head>
  <body>

    <c:if test="${message != null}">
      <p>${message}</p>
    </c:if>

    <div class="columns all-promotions-wrapper">
      <h3 class="col-12">Active Promotions</h3>
      <div class="columns col-12 active-promotions">
        <c:forEach var="latestRate" items="${latestRates}">
            <div class="column col-3 promotion-wrapper">
              <div class="tile tile-centered">
                <div class="tile-content">
                  <p class="title-title">${latestRate.currencyCode}</p>
                  <p class="tile-subtitle">${latestRate.rate}</p>
                  <div class="title-action">
                    <button class="btn btn-primary btn-sm promotion-button">View
                    </button>
                  </div>
                </div>
              </div>
            </div>
        </c:forEach>
      </div>





    </div>
  </body>
</html>
