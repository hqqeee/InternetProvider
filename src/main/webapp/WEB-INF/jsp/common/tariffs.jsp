<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:choose>
	<c:when test="${sortingField=='name'}">
		<c:set var="sortingFieldLabel" scope="request" ><fmt:message key="view.name_field" /></c:set>
		<c:set var="anotherSortingFieldName" scope="request"><fmt:message key="view.rate_field" /></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="sortingFieldLabel" scope="request"><fmt:message key="view.rate_field" /></c:set>
		<c:set var="anotherSortingFieldName" scope="request"><fmt:message key="view.name_field" /></c:set>
	</c:otherwise>
</c:choose>
<div class="container py-3">
	<div class="container bg-dark p-2 text-dark  px-4 py-4">
		<form action="controller" id="select_service" method="get">
			<input type="hidden" name="action" value="viewTariffs" /> <input
				type="hidden" name="sortingField"
				value="${requestScope.sortingField}"> <input type="hidden"
				name="sortingOrder" value="${requestScope.sortingOrder}"> <input
				type="hidden" name="rowNumber" value="${requestScope.rowNumber}">
			<input type="hidden" name="service" id="service_name"
				value="${requestScope.service}">
			<div class="row justify-content-around form-group">
				<div
					class="col card bg-dark text-secondary ${requestScope.service eq 'TELEPHONE'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/call-contact-phone-svgrepo-com.png" alt="..."
						width="64" style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white">
							<fmt:message key="tariffs.telephone_service_header" />
						</h5>
						<p class="card-text"><fmt:message key="tariffs.telephone_service_description" /></p>

						<a href="#" class="stretched-link"
							onclick="select_service('TELEPHONE');document.getElementById('select_service').submit(); "></a>
					</div>
				</div>

				<div
					class="col card bg-dark text-secondary ${requestScope.service eq 'INTERNET'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/connection-svgrepo-com.png" alt="..." width="64"
						style="filter: invert(1);" />
					<div class="card-body">
						<h5 class="card-title text-center text-white">
							<fmt:message key="tariffs.internet_service_header" />
						</h5>
						<p class="card-text"><fmt:message key="tariffs.internet_service_description" /></p>

						<a href="#" class="stretched-link"
							onclick="select_service('INTERNET');document.getElementById('select_service').submit(); "></a>
					</div>
				</div>
				<div
					class="col card bg-dark text-secondary ${requestScope.service eq 'CABLE_TV'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/satelite-svgrepo-com.png" alt="..." width="64"
						style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white">
							<fmt:message key="tariffs.cableTV_service_header" />
						</h5>
						<p class="card-text"><fmt:message key="tariffs.cableTV_service_description" /></p>

						<a href="#" class="stretched-link"
							onclick="select_service('CABLE_TV');document.getElementById('select_service').submit();"></a>
					</div>
				</div>
				<div
					class="col card bg-dark text-secondary ${requestScope.service eq 'IP_TV'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/smart-svgrepo-com.png" alt="..." width="64"
						style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white">
							<fmt:message key="tariffs.IP-TV_service_header" />
						</h5>
						<p class="card-text"><fmt:message key="tariffs.IP-TV_service_description" /></p>
					</div>
					<a href="#" class="stretched-link"
						onclick="select_service('IP_TV');document.getElementById('select_service').submit();"></a>
				</div>
			</div>
		</form>
	</div>

	<div class="d-flex justify-content-between align-items-center">
		<form class="my-3 mx-1 d-flex" method="get">

			<input type="hidden" name="action" value="viewTariffs" /> <input
				type="hidden" name="sortingField"
				value="${requestScope.sortingField}"> <input type="hidden"
				name="sortingOrder" value="${requestScope.sortingOrder}">
			<input type="hidden" name="service" value="${requestScope.service}" />
			<label class="fs-5 text-white"><fmt:message key="view.show" /></label> <select
				class="form-select bg-dark text-white mx-2"
				onchange="this.form.submit()" name="rowNumber">
				<c:forEach begin="5" end="20" var="i" step="5">
					<c:choose>
						<c:when test="${rowNumber eq i}">
							<option selected>${i}</option>
						</c:when>
						<c:otherwise>
							<option value="${i}">${i}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</form>
		<div>
			<form action="controller" id="sorting_change" method="get">

				<input type="hidden" name="action" value="viewTariffs" /> <input
					type="hidden" name="sortingField" id="sorting_field"
					value="${sortingField}"> <input type="hidden"
					name="sortingOrder" id="sorting_order" value="${sortingOrder}">
				<input type="hidden" name="service" value="${requestScope.service}" />
				<input type="hidden" name="rowNumber"
					value="${requestScope.rowNumber}">
				<%-- 					<input type="hidden" name="page" value="${requestScope.page}" /> --%>
				<label class="fs-5 text-white me-2"><fmt:message key="view.sort_by" /></label>
				<div class="btn-group">
					<button type="button"
						class="btn btn-warning dropdown-toggle dropdown-toggle-split"
						data-bs-toggle="dropdown" aria-expanded="false">
						<span class="visually-hidden">Toggle Dropdown</span>
					</button>
					<ul class="dropdown-menu dropdown-menu-dark">
						<li><a class="dropdown-item"
							onclick="change_sorting_field(this.innerText);document.getElementById('sorting_change').submit();"
							href="#">${anotherSortingFieldName}</a></li>
					</ul>
					<button type="button" class="btn btn-outline-warning"
						onclick="change_sorting_order();document.getElementById('sorting_change').submit();">
						${sortingFieldLabel}<img
							class="align-self-center filter-yellow mx-1 mb-1 ${requestScope.sortingOrder=='ASC'?'rotate-270':'rotate-90'}"
							src="images/arrow.png" alt="..." width="16">
					</button>
				</div>
			</form>
		</div>
	</div>
	<!-- TARIFFS -->
	<c:forEach var="tariff" items="${requestScope.tariffsToDisplay}">


		<ctf:tariff tariff="${tariff}">
			<c:if test="${sessionScope.loggedUser.role eq 'ADMIN'}">
				<div class="text-end mb-3 me-3">

					<button type="button" class="btn btn-warning me-2"
						data-bs-toggle="modal" data-bs-target="#editTariff"
						onclick="edit_tariff(`${tariff.name}`,`${tariff.rate}`,`${tariff.service}`,`${tariff.description}`,`${tariff.paymentPeriod}`,`${tariff.id}`)"><fmt:message key="tariffs.edit"/></button>
					<button type="button" class="btn btn-danger" data-bs-toggle="modal"
						data-bs-target="#submitTariffRemove"
						onclick="confirm_tariff_remove(`${tariff.name}`, `${tariff.id}`)"><fmt:message key="tariffs.remove"/></button>
				</div>
			</c:if>
			<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
				<div class="text-center mb-3">

					<button type="button" class="btn btn-warning btn-lg "
						data-bs-toggle="modal" data-bs-target="#submitTariffSelection"
						onclick="confirm_tariff_selection(`${tariff.rate}`, `${tariff.name}`, `${tariff.id}`,`${tariff.paymentPeriod}`)"><fmt:message key="tariffs.get_now" /></button>
				</div>
			</c:if>
		</ctf:tariff>

	</c:forEach>

</div>
<div class="d-flex justify-content-between align-items-center">
	<form action="controller" method="get">

		<input type="hidden" name="action" value="viewTariffs" /> <input
			type="hidden" name="page" id="page_number" /> <input type="hidden"
			name="sortingField" value="${requestScope.sortingField}"> <input
			type="hidden" name="sortingOrder"
			value="${requestScope.sortingOrder}"> <input type="hidden"
			name="service" value="${requestScope.service}" /> <input
			type="hidden" name="rowNumber" value="${requestScope.rowNumber}">
		<div
			class="form-group btn-group col-lg-auto me-lg-auto mx-2 justify-content-center">
			<c:forEach begin="1" end="${numberOfPages}" var="i">
				<c:choose>
					<c:when test="${page eq i}">
						<button type="button" class="btn btn-warning">${i}</button>
					</c:when>
					<c:otherwise>
						<button value="${i}" type="submit" class="btn btn-outline-warning"
							onclick="submit_page(this.value)">${i}</button>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</form>
	<c:choose>
		<c:when test="${sessionScope.loggedUser.role eq 'ADMIN'}">
			<form action="controller" method="get">
			
			<input type="hidden" name="action" value="openAddTariff"/>
				<button type="submit" class="btn btn-outline-warning mx-3 mb-3"><fmt:message key="tariffs.add_new_tariff"/></button>
			</form>
		</c:when>
		<c:otherwise>
			<form action="controller" method="get">
				<input type="hidden" name="action" value="downloadTariffs"/>
				<input type="hidden" name="service" value="${requestScope.service}" />
				<button type="submit" class="btn btn-outline-warning mx-3 mb-3"><fmt:message key="tariffs.download_pdf"/></button>
			</form>
		</c:otherwise>
	</c:choose>
</div>

<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
	<div class="modal fade" id="submitTariffSelection" tabindex="-1"
		role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2"><fmt:message key="tariffs.confirm_selection"/></p>
					<hr class="style1">
					<p class="fw-bold">
						<fmt:message key="tariffs.about_to_connect"/> <span id="tariff_name"></span>
					</p>
					<p class="text-muted">
						<fmt:message key="tariffs.charge_note_1"/>$<span id="tariff_rate"></span>
						<fmt:message key="tariffs.charge_note_2"/> <span id="tariff_days"></span> <fmt:message key="tariffs.charge_note_3"/>
					</p>
					<div class="text-end">
						<form action="controller?action=connectTariff" method="post">
							<input type="hidden" name="tariffId" id="tariff_id" />
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal"><fmt:message key="tariffs.let_me_think"/></button>
							<button type="submit" class="btn btn-warning"><fmt:message key="tariffs.confirm"/></button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>

<c:if test="${sessionScope.loggedUser.role eq 'ADMIN'}">
	<div class="modal fade" id="submitTariffRemove" tabindex="-1"
		role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2"><fmt:message key="tariff_remove.prompt"/></p>
					<hr class="style1">
					<p class="fw-bold"><fmt:message key="tariff_remove.about_to_remove"/></p>
					<p id="tariff_name" class="fw-bold fs-5"></p>

					<div class="text-end">
						<form action="controller?action=removeTariff" method="post">
							<input type="hidden" name="tariffId" id="tariff_id" />
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal"><fmt:message key="tariff_remove.cancel"/></button>
							<button type="submit" class="btn btn-warning"><fmt:message key="tariff_remove.confirm"/></button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${sessionScope.loggedUser.role eq 'ADMIN'}">
	<div class="modal fade" id="editTariff" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2" id="enter_invite"><fmt:message key="tariff_update.prompt"/></p>
					<hr class="style1">
					<c:if test="${not empty requestScope.tariffValidateErrors}">
						<c:forEach var="error"
							items="${requestScope.tariffValidateErrors}">
							<div class="alert alert-danger" role="alert">${error}</div>
						</c:forEach>
					</c:if>
					<form action="controller?action=editTariff" method="post"
						id="edit_tariff_form">
						<div class="input-group">
							<label for="name" class="input-group-text"><fmt:message key="tariff_add.name"/></label><input
								type="text" class="form-control" id="edit_tariff_name"
								name="name" maxlength="32" placeholder="Max 32 characters."
								value="${requestScope.tariffForm.name}" required>
						</div>
						<select class="form-select my-3" id="edit_tariff_service"
							name="serviceSelected">
							<option value="TELEPHONE"
								${requestScope.tariffForm.service == 'TELEPHONE'?'selected':''}><fmt:message
									key="tariffs.telephone_service_header" /></option>
							<option value="INTERNET"
								${requestScope.tariffForm.service == 'INTERNET'?'selected':''}><fmt:message
									key="tariffs.internet_service_header" /></option>
							<option value="CABLE_TV"
								${requestScope.tariffForm.service == 'CABLE_TV'?'selected':''}><fmt:message
									key="tariffs.cableTV_service_header" /></option>
							<option value="IP_TV"
								${requestScope.tariffForm.service == 'IP_TV'?'selected':''}><fmt:message
									key="tariffs.IP-TV_service_header" /></option>
						</select>


						<div class="input-group my-3">
							<label for="rate" class="input-group-text"><fmt:message key="tariff_add.rate"/></label> <span
								class="input-group-text">$</span> <input id="edit_tariff_rate"
								type="number" name="rate" class="form-control" step="0.01"
								value="0" min="0" value="${requestScope.tariffForm.rate}"
								required /> <input type="hidden" name="paymentPeriod"
								id="payment_period"> <span class="input-group-text"
								id="payment_period_span"></span><span class="input-group-text"><fmt:message key="tariff_add.days" /></span>
						</div>
						<div class="input-group">
							<span class="input-group-text"><fmt:message
									key="tariff_add.description" /></span>
							<textarea class="form-control" name="description"
								id="edit_tariff_description" placeholder="Max 255 characters."
								maxlength="255" required>${requestScope.tariffForm.description}"</textarea>
						</div>
						<hr class="style1">
						<div class="text-end my-3">
							<input type="hidden" name="tariffId" id="edit_tariff_id" />
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal"><fmt:message key="tariff_update.cancel"/></button>
							<input type="hidden" name="service"
								value="${requestScope.service}" /> <input type="hidden"
								name="rowNumber" value="${requestScope.rowNumber}"> <input
								type="hidden" name="sortingField"
								value="${requestScope.sortingField}"> <input
								type="hidden" name="sortingOrder"
								value="${requestScope.sortingOrder}"> <input
								type="hidden" name="page" value="${requestScope.page}" />
							<button type="submit" class="btn btn-warning"
								id="update_add_buttom"><fmt:message key="tariff_update.update"/></button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${not empty requestScope.tariffValidateErrors}">
	<script type="text/javascript">
		var myModal = new bootstrap.Modal(
				document.getElementById('editTariff'), {})
		myModal.toggle()
	</script>
</c:if>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tariff_menu.js"></script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>