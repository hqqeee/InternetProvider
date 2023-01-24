<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ attribute name="tariff" required="true" rtexprvalue="true"
	type="com.epam.services.dto.TariffDTO"%>
<%@ attribute name="daysUtilNext" rtexprvalue="true"%>
<c:set var="perDay" scope="request" ><fmt:message key="tariffs.per_day"/></c:set>
<c:set var="perWeek" scope="request" ><fmt:message key="tariffs.per_week"/></c:set>
<c:set var="perTwoWeeks" scope="request" ><fmt:message key="tariffs.per_two_weeks"/></c:set>
<c:set var="perMonth" scope="request" ><fmt:message key="tariffs.per_month"/></c:set>
<div class="card bg-dark text-white border-light mb-5 mt-3">
	<div class="d-flex justify-content-between align-items-center">
		<p class="h2 m-3 fw-bold">${tariff.name}</p>
		<img class="align-self-center mx-3"
			src="${tariff.service eq 'TELEPHONE'?'images/call-contact-phone-svgrepo-com.png'
									:tarif.service eq 'INTERNET'?'images/connection-svgrepo-com.png'
				 					:tariff.service eq 'CABLE_TV'?'images/satelite-svgrepo-com.png':'images/smart-svgrepo-com.png'} "
			alt="..." width="32" style="filter: invert(1);">
	</div>
	<c:if test="${not empty daysUtilNext}">
		<div class="text-start mb-3 ms-3 text-muted">${daysUtilNext}
			day(s) until next payment.</div>
	</c:if>
	<hr class="style1">
	<div class="d-flex justify-content-between align-items-center">
		<div class="text-muted mx-3"><fmt:message key="tariffs.description" /></div>
		<p class="mx-3 dw-bold"><fmt:message key="tariffs.price" />
			$${tariff.rate} ${tariff.paymentPeriod eq 28?requestScope.perMonth:tariff.paymentPeriod eq 14
			?requestScope.perTwoWeeks: tariff.paymentPeriod eq 7? requestScope.perWeek:
			requestScope.perDay}</p>
	</div>
	<div class="d-flex justify-content-start m-3 text-start">${tariff.description}</div>
	<jsp:doBody />
</div>