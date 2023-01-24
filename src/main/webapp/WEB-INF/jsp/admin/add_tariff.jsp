<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<p class="fs-2" id="enter_invite"><fmt:message key="tariff_add.prompt"/></p>
<hr class="style1">
<form action="controller?action=addTariff" method="post"
	id="edit_tariff_form">
	<div class="input-group">
		<label for="name" class="input-group-text"><fmt:message key="tariff_add.name"/> </label><input
			type="text" class="form-control" id="edit_tariff_name" name="name"
			maxlength="32" placeholder="<fmt:message key="tariff_add.max_character_1"/> 32 <fmt:message key="tariff_add.max_character_2"/>"
			value="${requestScope.tariffForm.name}" required>
	</div>
	<select class="form-select my-3" id="edit_tariff_service_id"
		name="serviceSelected">
		<option value="TELEPHONE"
			${requestScope.tariffForm.service == TELEPHONE?'selected':''}><fmt:message key="tariffs.telephone_service_header"/></option>
		<option value="INTERNET"
			${requestScope.tariffForm.service == INTERNET?'selected':''}><fmt:message key="tariffs.internet_service_header"/></option>
		<option value="CABLE_TV"
			${requestScope.tariffForm.service == CABLE_TV?'selected':''}><fmt:message key="tariffs.cableTV_service_header"/></option>
		<option value="IP_TV"
			${requestScope.tariffForm.service == IP_TV?'selected':''}><fmt:message key="tariffs.IP-TV_service_header"/></option>
	</select>


	<div class="input-group my-3">
		<label for="rate" class="input-group-text"><fmt:message key="tariff_add.rate"/></label> <span
			class="input-group-text">$</span> <input id="edit_tariff_rate"
			type="number" name="rate" class="form-control" step="0.01" value="0"
			min="0" value="${requestScope.tariffForm.rate}" required /> <select
			class="form-select" name="paymentPeriod">
			<option value="1" ${requestScope.tariffForm.paymentPeriod == 1?'selected':''}><fmt:message key="tariffs.per_day"/></option>
			<option value="7" ${requestScope.tariffForm.paymentPeriod == 7?'selected':''}><fmt:message key="tariffs.per_week"/></option>
			<option value="14" ${requestScope.tariffForm.paymentPeriod == 14?'selected':''}><fmt:message key="tariffs.per_two_weeks"/></option>
			<option value="28" ${requestScope.tariffForm.paymentPeriod == 28?'selected':''}><fmt:message key="tariffs.per_month"/></option>
		</select>
	</div>
	<div class="input-group">
		<span class="input-group-text"><fmt:message key="tariff_add.description"/></span>
		<textarea class="form-control" name="description"
			id="edit_tariff_description" placeholder="<fmt:message key="tariff_add.max_character_1"/> 255 <fmt:message key="tariff_add.max_character_2"/>"
			maxlength="255" required>${requestScope.tariffForm.description}</textarea>
	</div>
	<hr class="style1">
	<div class="text-end my-3">
		<button type="submit" class="btn btn-warning" id="update_add_buttom"><fmt:message key="tariff_add.add"/></button>
	</div>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>