<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ include file="/WEB-INF/jsp/subscriber/profile_header.jspf"%>

<div class="d-flex justify-content-between align-items-center">
	<p class="h2 m-3 fw-bold text-white"><fmt:message key="active_tariffs.header"/></p>
</div>
<hr class="style1">
<c:forEach var="tariff" items="${requestScope.tariffsToDisplay}">
	<ctf:tariff tariff="${tariff.key}" daysUtilNext="${tariff.value}">
		<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
			<div class="text-end mb-3 me-3">

				<button type="button" class="btn btn-danger" data-bs-toggle="modal"
					data-bs-target="#submitTariffRemove"
					onclick="confirm_tariff_remove('${tariff.key.name}', '${tariff.key.id}')"><fmt:message key="active_tariffs.disable"/>
				</button>
			</div>
		</c:if>
	</ctf:tariff>
</c:forEach>
<div class="modal fade" id="submitTariffRemove" tabindex="-1"
	role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content text-bg-dark">
			<div class="modal-body">
				<p class="fs-2"><fmt:message key="active_tariffs.confirm_disable"/></p>
				<hr class="style1">
				<p class="fw-bold">
					<fmt:message key="active_tariffs.about_to_disable"/> <span id="tariff_name"></span>
				</p>
				<div class="text-end">
					<form action="controller?action=disableTariff" method="post">
						<input type="hidden" name="tariffId" id="tariff_id" />
						<button type="button" class="btn btn-secondary me-2"
							data-bs-dismiss="modal"><fmt:message key="active_tariffs.cancel"/></button>
						<button type="submit" class="btn btn-warning"><fmt:message key="active_tariffs.confirm"/></button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>