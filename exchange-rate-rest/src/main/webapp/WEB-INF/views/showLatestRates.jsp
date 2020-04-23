<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Promotion UI</title>
    <link href="${pageContext.request.contextPath}/resources/css/spectre.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/promotions.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
  </head>
  <body>

    <c:if test="${message != null}">
      <p>${message}</p>
    </c:if>

    <div class="columns all-promotions-wrapper">
      <h3 class="col-12">Active Promotions</h3>
      <div class="columns col-12 active-promotions">
        <c:forEach var="promotion" items="${promotions}">
          <c:set var="promotionFilter" value="${promotion.promotionFilter}"/>
          <c:if test="${promotionFilter.disabled == false}">
            <div class="column col-3 promotion-wrapper">
              <div class="tile tile-centered">
                <div class="tile-content">
                  <p class="title-title">${promotion.promotion.name}</p>
                  <p class="tile-subtitle">${promotion.promotion.description}</p>
                  <div class="title-action">
                    <button class="btn btn-primary btn-sm disable-button disable-promotion" type="submit"
                            data-promotion="${promotion.promotion.id}">Disable
                    </button>
                    <button class="btn btn-primary btn-sm promotion-button"
                                     data-promotion="${promotion.promotion.id}">View
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </c:if>
        </c:forEach>
      </div>

      <div class="modal modal-sm" id="disable-modal">
        <div class="modal-overlay"></div>
        <div class="modal-container">
          <div class="modal-header">
            <p>Are you sure you want to <strong>disable</strong> this promotion?</p>
          </div>
          <div class="modal-body confirm-modal-buttons">
            <button class="btn btn-primary btn-sm disable-button" id="disable-modal-close">Cancel
            </button>
            <form:form class="hidden-form" commandName="disablePromotion" method="POST" modelAttribute="disablePromotion" action="${pageContext.request.contextPath}/api/promotions/disable">
              <form:input path="id" type="hidden" id="disable-id"/>
              <form:button class="btn btn-primary btn-sm enable-button" type="submit">Yes</form:button>
            </form:form>
          </div>
        </div>
      </div>

      <c:forEach var="promotion" items="${promotions}">
        <c:set var="promotionFilter" value="${promotion.promotionFilter}"/>
        <div class="modal" id="modal-${promotion.promotion.id}">
          <div class="modal-overlay"></div>
          <div class="modal-container">
            <div class="modal-header">
              <button class="btn btn-clear float-right modal-close-button"
                      id="${promotion.promotion.id}"></button>
              <div class="modal-title">${promotion.promotion.name}</div>
              <div class="tile-subtitle">${promotion.promotion.description}</div>
            </div>
            <div class="modal-body">
              <div class="content">
                <div class="columns col-12">
                  <c:if test="${not empty promotionFilter}">
                    <div class="column col-6">
                      <label class="form-label" for="supplier">Supplier</label>
                      <input class="form-input" type="text" id="supplier" value="${promotionFilter.supplier.name}" readonly/>

                      <label class="form-label" for="promo-code">Promo code</label>
                      <input class="form-input" type="text" id="promo-code" value="${promotionFilter.promoCode}" readonly/>

                      <label class="form-label" for="discount-code">Discount code</label>
                      <input class="form-input" type="text" id="discount-code" value="${promotionFilter.discountCode}" readonly/>
                    </div>

                    <div class="column col-6">
                      <label class="form-label" for="start-date">Start date</label>
                      <input class="form-input" type="text" id="start-date" value="${promotionFilter.startDate}" readonly/>

                      <label class="form-label" for="end-date">End date</label>
                      <input class="form-input" type="text" id="end-date" value="${promotionFilter.endDate}" readonly/>

                      <label class="form-label" for="affiliate-code">Affiliate code</label>
                      <input class="form-input" type="text" id="affiliate-code" value="${promotionFilter.affiliateCode.code}" readonly/>
                    </div>

                    <div class="column col-6">
                      <label class="form-label" for="status">Status</label>
                      <c:choose>
                        <c:when test="${promotionFilter.disabled == false}">
                          <input class="form-input" type="text" id="status" value="Active" readonly/>
                        </c:when>
                        <c:otherwise>
                          <input class="form-input" type="text" id="status" value="Disabled" readonly/>
                        </c:otherwise>
                      </c:choose>
                    </div>

                    <div class="column col-6">
                      <label class="form-label" for="vehicle">Applicable Vehicles</label>
                      <select class="form-select">
                        <c:forEach var="vehicle" items="${promotion.vehicles}">
                          <c:choose>
                            <c:when test="${empty vehicle}">
                              <option id="vehicle" readonly>
                                All
                              </option>
                            </c:when>
                            <c:otherwise>
                              <option id="vehicle" readonly>${vehicle.vehicleClassName}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </select>
                    </div>

                    <c:forEach var="config" items="${promotion.promotionConfigs}">
                      <div class="column col-6">
                        <label class="form-label" for="config">${config.configName}</label>
                        <input class="form-input" type="text" id="config" value="${config.configValue}" readonly>
                      </div>
                    </c:forEach>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>

      <h3 class="col-12">Disabled Promotions</h3>
      <div class="columns col-12">
        <c:forEach var="promotion" items="${promotions}">
          <c:if test="${promotion.promotionFilter.disabled}">
            <div class="column col-3 promotion-wrapper">
                <div class="tile tile-centered">
                  <div class="tile-content">
                    <p class="title-title">${promotion.promotion.name}</p>
                    <p class="tile-subtitle">${promotion.promotion.description}</p>
                    <div class="title-action">
                      <button class="btn btn-primary btn-sm enable-button enable-promotion"
                              data-promotion="${promotion.promotion.id}">Enable
                      </button>
                      <button class="btn btn-primary btn-sm promotion-button"
                              data-promotion="${promotion.promotion.id}">View
                      </button>
                    </div>
                  </div>
                </div>
            </div>
          </c:if>
        </c:forEach>
      </div>

      <div class="modal modal-sm" id="enable-modal">
        <div class="modal-overlay"></div>
        <div class="modal-container">
          <div class="modal-header">
            <p>Are you sure you want to <strong>enable</strong> this promotion?</p>
          </div>
          <div class="modal-body confirm-modal-buttons">
            <button class="btn btn-primary btn-sm disable-button" id="enable-modal-close">Cancel
            </button>
            <form:form commandName="enablePromotion" class="hidden-form" method="POST" methodAttribute="enablePromotion" action="${pageContext.request.contextPath}/api/promotions/enable">
              <form:input path="id" type="hidden" id="enable-id"/>
              <form:button class="btn btn-primary btn-sm enable-button" type="submit">Yes</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/promotions.js"></script>
  </body>
</html>
