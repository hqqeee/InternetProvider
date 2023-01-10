<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:choose>
	<c:when test="${sortingField=='name'}">
		<c:set var="sortingFieldLabel" scope="request" value="Name" />
		<c:set var="anotherSortingFieldName" scope="request" value="Rate" />
	</c:when>
	<c:otherwise>
		<c:set var="sortingFieldLabel" scope="request" value="Price" />
		<c:set var="anotherSortingFieldName" scope="request" value="Name" />
	</c:otherwise>
</c:choose>

<div class="container py-3">
	<div class="container bg-dark p-2 text-dark  px-4 py-4">
		<form action="controller?action=viewTariff" id="select_service"
			method="post">
			<input type="hidden" name="sortingField"
				value="${requestScope.sortingField}"> <input type="hidden"
				name="sortingOrder" value="${requestScope.sortingOrder}"> <input
				type="hidden" name="rowNumber" value="${requestScope.rowNumber}">
			<input type="hidden" name="page" value="${requestScope.page}" /> <input
				type="hidden" name="serviceId" id="service_id"
				value="${requestScope.serviceId}">
			<div class="row justify-content-around form-group">
				<div
					class="col card bg-dark text-secondary ${requestScope.serviceId=='1'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/call-contact-phone-svgrepo-com.svg" alt="..."
						width="64" style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white"><fmt:message key="tariffs.telephone_service_header"/></h5>
						<p class="card-text">Some quick example text to build on the
							card title and make up the bulk of the card's content.</p>

						<a href="#" class="stretched-link"
							onclick="select_service(1);document.getElementById('select_service').submit(); "></a>
					</div>
				</div>

				<div
					class="col card bg-dark text-secondary ${requestScope.serviceId=='2'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/connection-svgrepo-com.svg" alt="..." width="64"
						style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white"><fmt:message key="tariffs.internet_service_header"/></h5>
						<p class="card-text">Some quick example text to build on the
							card title and make up the bulk of the card's content.</p>

						<a href="#" class="stretched-link"
							onclick="select_service(2);document.getElementById('select_service').submit(); "></a>
					</div>
				</div>
				<div
					class="col card bg-dark text-secondary ${requestScope.serviceId=='3'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/satelite-svgrepo-com.svg" alt="..." width="64"
						style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white"><fmt:message key="tariffs.cableTV_service_header"/></h5>
						<p class="card-text">Some quick example text to build on the
							card title and make up the bulk of the card's content.</p>

						<a href="#" class="stretched-link"
							onclick="select_service(3);document.getElementById('select_service').submit(); "></a>
					</div>
				</div>
				<div
					class="col card bg-dark text-secondary ${requestScope.serviceId=='4'?'border-warning':''} mx-3">
					<img class="align-self-center mt-2"
						src="images/smart-svgrepo-com.svg" alt="..." width="64"
						style="filter: invert(1);">
					<div class="card-body">
						<h5 class="card-title text-center text-white"><fmt:message key="tariffs.IP-TV_service_header"/></h5>
						<p class="card-text">Some quick example text to build on the
							card title and make up the bulk of the card's content.</p>
					</div>
					<a href="#" class="stretched-link"
						onclick="select_service(4);document.getElementById('select_service').submit(); "></a>
				</div>
			</div>
		</form>
	</div>

	<div class="d-flex justify-content-between align-items-center">
		<form class="my-3 mx-1 d-flex" method="post">
			<input type="hidden" name="sortingField"
				value="${requestScope.sortingField}"> <input type="hidden"
				name="sortingOrder" value="${requestScope.sortingOrder}"> <input
				type="hidden" name="page" value="${requestScope.page}" /> <input
				type="hidden" name="serviceId" value="${requestScope.serviceId}" />
			<label class="fs-5 text-white">Show</label> <select
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
			<form action="controller?action=viewTariff" id="sorting_change"
				method="post">
				<input type="hidden" name="sortingField" id="sorting_field"
					value="${sortingField}"> <input type="hidden"
					name="sortingOrder" id="sorting_order" value="${sortingOrder}">
				<input type="hidden" name="serviceId"
					value="${requestScope.serviceId}" /> <input type="hidden"
					name="rowNumber" value="${requestScope.rowNumber}"> <input
					type="hidden" name="page" value="${requestScope.page}" /> <label
					class="fs-5 text-white me-2">Sort by</label>
				<div class="btn-group">
					<button type="button"
						class="btn btn-warning dropdown-toggle dropdown-toggle-split"
						data-bs-toggle="dropdown" aria-expanded="false">
						<span class="visually-hidden">Toggle Dropdown</span>
					</button>
					<ul class="dropdown-menu dropdown-menu-dark">
						<li><a class="dropdown-item"
							onclick="change_sorting_field(this.innerText);document.getElementById('sorting_change').submit();"
							href="#">${anotherSortingFieldName }</a></li>
					</ul>
					<button type="button" class="btn btn-outline-warning"
						onclick="change_sorting_order();document.getElementById('sorting_change').submit();">
						${sortingFieldLabel}<img
							class="align-self-center filter-yellow mx-1 mb-1 ${requestScope.sortingOrder=='ASC'?'rotate-270':'rotate-90'}"
							src="images/arrow.svg" alt="..." width="16">
					</button>
				</div>
			</form>
		</div>
	</div>
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
				<p class="mx-3 dw-bold">Rate: $${tariff.rate}/${tariff.paymentPeriod eq 28?'month':tariff.paymentPeriod eq 14?'two weeks': tariff.paymentPeriod eq 7? 'week':'day'}</p>
			</div>
			<div class="d-flex justify-content-start m-3 text-start">${tariff.description }</div>
			<c:if test="${loggedUser.roleId==1}">
				<div class="text-end mb-3 me-3">

					<button type="button" class="btn btn-warning me-2"
						data-bs-toggle="modal" data-bs-target="#editTariff"
						onclick="edit_tariff('${tariff.name}','${tariff.rate}','${tariff.serviceId}','${tariff.description}','${tariff.paymentPeriod}','${tariff.id}')">Edit</button>
					<button type="button" class="btn btn-danger" data-bs-toggle="modal"
						data-bs-target="#submitTariffRemove"
						onclick="confirm_tariff_remove('${tariff.name}', '${tariff.id}')">Remove</button>
				</div>
			</c:if>
			<c:if test="${loggedUser.roleId==2}">
				<div class="text-center mb-3">

					<button type="button" class="btn btn-warning btn-lg "
						data-bs-toggle="modal" data-bs-target="#submitTariffSelection"
						onclick="confirm_tariff_selection('${tariff.rate}', '${tariff.name}', '${tariff.id}')">GET
						NOW</button>
				</div>
			</c:if>
		</div>

	</c:forEach>

</div>
<div class="d-flex justify-content-between align-items-center">
	<form action="controller?action=viewTariff" method="post">
		<input type="hidden" name="page" id="page_number" /> <input
			type="hidden" name="sortingField"
			value="${requestScope.sortingField}"> <input type="hidden"
			name="sortingOrder" value="${requestScope.sortingOrder}"> <input
			type="hidden" name="serviceId" value="${requestScope.serviceId}" />
		<input type="hidden" name="rowNumber"
			value="${requestScope.rowNumber}">
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
		<c:when test="${loggedUser.roleId == 1}">
		<form action="controller?action=openAddTariff" method="post">
			<button type="submit"
				class="btn btn-outline-warning mx-3 mb-3">Add
				new tariff</button>
		</form>
		</c:when>
		<c:otherwise>
			<form action="controller?action=downloadTariff" method="post">
				<input type="hidden" name="serviceId"
					value="${requestScope.serviceId}" />
				<button type="submit" class="btn btn-outline-warning mx-3 mb-3">Download
					PDF</button>
			</form>
		</c:otherwise>
	</c:choose>
</div>

<c:if test="${loggedUser.roleId==2}">
	<div class="modal fade" id="submitTariffSelection" tabindex="-1"
		role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2">Please confirm you selection.</p>
					<hr class="style1">
					<p class="fw-bold">
						You are about to connect tariff <span id="tariff_name"></span>
					</p>
					<p class="text-muted">
						Please note. You will now be charged $<span id="tariff_rate"></span>
						per month.
					</p>
					<div class="text-end">
						<form action="controller?action=connectTariff" method="post">
							<input type="hidden" name="tariffId" id="tariff_id" />
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal">Let me think</button>
							<button type="submit" class="btn btn-warning">Confirm</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>

<c:if test="${loggedUser.roleId==1}">
	<div class="modal fade" id="submitTariffRemove" tabindex="-1"
		role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2">Please confirm you action.</p>
					<hr class="style1">
					<p class="fw-bold">You are about to remove tariff</p>
					<p id="tariff_name" class="fw-bold fs-5"></p>

					<div class="text-end">
						<form action="controller?action=removeTariff" method="post">
							<input type="hidden" name="tariffId" id="tariff_id" />
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-warning">Confirm</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${loggedUser.roleId==1}">
	<div class="modal fade" id="editTariff" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<p class="fs-2" id="enter_invite">Please provide data to update.</p>
					<hr class="style1">
					<c:if test="${not empty requestScope.tariffValidateErrors}">
						<c:forEach var="error"
							items="${requestScope.tariffValidateErrors}">
							<div class="alert alert-danger" role="alert">
									${error}</div>
						</c:forEach>
					</c:if>
					<form action="controller?action=editTariff" method="post"
						id="edit_tariff_form">
						<div class="input-group">
							<label for="name" class="input-group-text">Name: </label><input
								type="text" class="form-control" id="edit_tariff_name"
								name="name" maxlength="32" placeholder="Max 32 characters."
								value="${requestScope.tariffForm.name}" required>
						</div>
						<select class="form-select my-3" id="edit_tariff_service_id"
							name="serviceIdNew">
							<option value="1" ${requestScope.tariffForm.serviceId == 1?'selected':''}><fmt:message key="tariffs.telephone_service_header"/></option>
							<option value="2" ${requestScope.tariffForm.serviceId == 2?'selected':''}><fmt:message key="tariffs.internet_service_header"/></option>
							<option value="3" ${requestScope.tariffForm.serviceId == 3?'selected':''}><fmt:message key="tariffs.cableTV_service_header"/></option>
							<option value="4" ${requestScope.tariffForm.serviceId == 4?'selected':''}><fmt:message key="tariffs.IP-TV_service_header"/></option>
						</select>


						<div class="input-group my-3">
							<label for="rate" class="input-group-text">Rate</label> <span
								class="input-group-text">$</span> <input id="edit_tariff_rate"
								type="number" name="rate" class="form-control" step="0.01"
								value="0" min="0" value="${requestScope.tariffForm.rate}" required />
								<input type="hidden" name="paymentPeriod" id="payment_period">
								 <span class="input-group-text" id="payment_period_span"></span>
						</div>
						<div class="input-group">
							<span class="input-group-text">Description</span>
							<textarea class="form-control" name="description"
								id="edit_tariff_description" placeholder="Max 255 characters."
								maxlength="255" required>${requestScope.tariffForm.description}"</textarea>
						</div>
						<hr class="style1">
						<div class="text-end my-3">
							<input type="hidden" name="tariffId" id="edit_tariff_id" />
							<button type="button" class="btn btn-secondary me-2"
								data-bs-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-warning"
								id="update_add_buttom">Update</button>
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