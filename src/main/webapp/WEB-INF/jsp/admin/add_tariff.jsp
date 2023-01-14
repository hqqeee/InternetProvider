<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<p class="fs-2" id="enter_invite">Please provide data to update.</p>
<hr class="style1">
<form action="controller?action=addTariff" method="post"
	id="edit_tariff_form">
	<div class="input-group">
		<label for="name" class="input-group-text">Name: </label><input
			type="text" class="form-control" id="edit_tariff_name" name="name"
			maxlength="32" placeholder="Max 32 characters."
			value="${requestScope.tariffForm.name}" required>
	</div>
	<select class="form-select my-3" id="edit_tariff_service_id"
		name="serviceSelected">
		<option value="TELEPHONE"
			${requestScope.tariffForm.service == TELEPHONE?'selected':''}>Telephone</option>
		<option value="INTERNET"
			${requestScope.tariffForm.service == INTERNET?'selected':''}>Internet</option>
		<option value="CABLE_TV"
			${requestScope.tariffForm.service == CABLE_TV?'selected':''}>Cable
			TV</option>
		<option value="IP_TV"
			${requestScope.tariffForm.service == IP_TV?'selected':''}>IP-TV</option>
	</select>


	<div class="input-group my-3">
		<label for="rate" class="input-group-text">Rate</label> <span
			class="input-group-text">$</span> <input id="edit_tariff_rate"
			type="number" name="rate" class="form-control" step="0.01" value="0"
			min="0" value="${requestScope.tariffForm.rate}" required /> <select
			class="form-select" name="paymentPeriod">
			<option value="1" ${requestScope.tariffForm.paymentPeriod == 1?'selected':''}>Per day</option>
			<option value="7" ${requestScope.tariffForm.paymentPeriod == 7?'selected':''}>Per week</option>
			<option value="14" ${requestScope.tariffForm.paymentPeriod == 14?'selected':''}>Every two weeks</option>
			<option value="28" ${requestScope.tariffForm.paymentPeriod == 28?'selected':''}>Per month</option>
		</select>
	</div>
	<div class="input-group">
		<span class="input-group-text">Description</span>
		<textarea class="form-control" name="description"
			id="edit_tariff_description" placeholder="Max 255 characters."
			maxlength="255" required>${requestScope.tariffForm.description}</textarea>
	</div>
	<hr class="style1">
	<div class="text-end my-3">
		<input type="hidden" name="tariffId" id="edit_tariff_id" />
		<button type="button" class="btn btn-secondary me-2"
			data-bs-dismiss="modal">Cancel</button>
		<button type="submit" class="btn btn-warning" id="update_add_buttom">Add</button>
	</div>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>