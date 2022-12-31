<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:choose>
	<c:when test="${sortingField=='name'}">
		<c:set var="sortingFieldLabel" scope="request" value="Name" />
		<c:set var="anotherSortingFieldName" scope="request" value="Price" />
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
						<h5 class="card-title text-center text-white">Telephone</h5>
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
						<h5 class="card-title text-center text-white">Internet</h5>
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
						<h5 class="card-title text-center text-white">Cable TV</h5>
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
						<h5 class="card-title text-center text-white">IP-TV</h5>
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
				<p class="mx-3 dw-bold">Price: $${tariff.price}/month</p>
			</div>
			<div class="d-flex justify-content-start m-3 text-start">${tariff.description }</div>
			<c:if test="${loggedUser.roleId==2}">
				<div class="text-center mb-3">

					<button type="button" class="btn btn-warning btn-lg "
						data-bs-toggle="modal" data-bs-target="#submitTariffSelection" onclick="confirm_tariff_selection('${tariff.price}', '${tariff.name}', '${tariff.id}')">GET
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
			<button type="submit" class="btn btn-outline-warning mx-3 mb-3">Add
				new tariff</button>
		</c:when>
		<c:otherwise>
		<form action = "controller?action=downloadTariff" method="post">
			<input type="hidden" name="serviceId" value="${requestScope.serviceId}"/>
			<button type="submit" class="btn btn-outline-warning mx-3 mb-3">Download
				PDF</button></form>
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
						Please note. You will now be charged $<span id="tariff_price"></span> per month.
					</p>
					<div class="text-end">
						<form action="controller?action=connectTariff" method="post">
						<input type="hidden" name="tariffId" id="tariff_id"/>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tariff_menu.js"></script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>