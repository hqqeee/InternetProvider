<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ include file="/WEB-INF/jsp/subscriber/profile_header.jspf"%>

<div class="d-flex justify-content-between align-items-center">
	<p class="h2 m-3 fw-bold text-white">Your active tariffs</p>
</div>
<hr class="style1">
<!-- TARIFFS -->
	<c:forEach var="tariff" items="${requestScope.tariffsToDisplay}">
		<div class="card bg-dark text-white border-light mb-5 mt-3">
			<div class="d-flex justify-content-between align-items-center">
				<p class="h2 m-3 fw-bold">${tariff.key.name}</p>
				<img class="align-self-center mx-3"
					src="${tariff.key.service eq 'TELEPHONE'?'images/call-contact-phone-svgrepo-com.png'
									:tariff.key.service eq 'INTERNET'?'images/connection-svgrepo-com.png'
				 					:tariff.key.service eq 'CABLE_TV'?'images/satelite-svgrepo-com.png':'images/smart-svgrepo-com.png'} "
					alt="..." width="32" style="filter: invert(1);">
			</div>
			<div class="text-start mb-3 ms-3 text-muted">
			  ${tariff.value} day(s) until next payment.
			</div>
			<hr class="style1">
			<div class="d-flex justify-content-between align-items-center">
				<div class="text-muted mx-3">Description</div>
				<p class="mx-3 dw-bold">Price: $${tariff.key.rate}/${tariff.key.paymentPeriod eq 28?'month':tariff.key.paymentPeriod eq 14?'two weeks': tariff.key.paymentPeriod eq 7? 'week':'day'}</p>
			</div>
			<div class="d-flex justify-content-start m-3 text-start">${tariff.key.description }</div>
			
			<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
				<div class="text-end mb-3 me-3">

					<button type="button" class="btn btn-danger"
						data-bs-toggle="modal" data-bs-target="#submitTariffRemove" onclick="confirm_tariff_remove('${tariff.key.name}', '${tariff.key.id}')">Disable
						</button>
				</div>
			</c:if>
		</div>

	</c:forEach>
	<div class="modal fade" id="submitTariffRemove" tabindex="-1"
		role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2">Are you sure you want to cancel the tariff?</p>
					<hr class="style1">
					<p class="fw-bold">
						You are about to stop using tariff <span id="tariff_name"></span>
					</p>
					<div class="text-end">
						<form action="controller?action=disableTariff" method="post">
						<input type="hidden" name="tariffId" id="tariff_id"/>
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-warning">Confirm</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>