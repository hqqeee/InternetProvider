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
				<p class="h2 m-3 fw-bold">${tariff.name}</p>
				<img class="align-self-center mx-3"
					src="${tariff.serviceId==1?'images/call-contact-phone-svgrepo-com.svg':tariff.serviceId==2?'images/connection-svgrepo-com.svg':tariff.serviceId==3?'images/satelite-svgrepo-com.svg':'images/smart-svgrepo-com.svg'}
					
					"
					alt="..." width="32" style="filter: invert(1);">
			</div>
			<hr class="style1">
			<div class="d-flex justify-content-between align-items-center">
				<div class="text-muted mx-3">Description</div>
				<p class="mx-3 dw-bold">Price: $${tariff.price}/month</p>
			</div>
			<div class="d-flex justify-content-start m-3 text-start">${tariff.description }</div>
			<c:if test="${loggedUser.roleId==2}">
				<div class="text-end mb-3 me-3">

					<button type="button" class="btn btn-danger"
						data-bs-toggle="modal" data-bs-target="#submitTariffRemove" onclick="confirm_tariff_remove('${tariff.name}', '${tariff.id}')">Disable
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