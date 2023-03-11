<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>




<c:set var="page" scope="request" value="${currentPage}" />
<c:set var="searchField" scope="request" value="${currentSearchField}" />
<c:set var="rowNumber" scope="request" value="${currentRowNumber}" />


<h1 class="display-5 fw-bold text-white align-start"><fmt:message key="admin_menu.admin_menu"/></h1>
<div class="container pt-3">
	<div
		class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
		<div
			class="col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">

			<form action="controller" class="my-3 mx-1 d-flex"
				method="get">
				<input type="hidden" name="action" value="adminMenu"/>
				<input
					type="hidden" name="searchField" value="${searchField}" /> <label
					class="fs-5 text-white"><fmt:message key="view.show"/></label> <select
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

		</div>
		<div class="text-end">
			<form class="d-flex text-end" role="search"
				action="controller" method="get">
				<input type="hidden" name="action" value="adminMenu"/>
				 <input
					type="hidden" name="rowNumber" value="${rowNumber}" /> <input
					class="form-control me-2" type="search" placeholder=<fmt:message key = "admin_menu.login"/>
					aria-label="Search" name="searchField" value="${searchField}">
				<button class="btn btn-outline-warning" type="submit"><fmt:message key="view.search"/></button>
			</form>
		</div>
	</div>
</div>

<table class="table align-middle mb-0 p-4 bg-dark">
	<thead class="table-secondary">
		<tr>
			<th><fmt:message key = "admin_menu.name"/></th>
			<th><fmt:message key = "admin_menu.login"/></th>
			<th><fmt:message key = "admin_menu.address"/></th>
			<th><fmt:message key = "admin_manu.status"/></th>
			<th><fmt:message key = "admin_menu.balance"/></th>
			<th></th>
		</tr>
	</thead>
	<tbody class="text-white">
		<c:forEach var="user" items="${requestScope.usersToDisplay}">
			<tr>
				<td>
					<p class="fw-bold mb-1">${user.firstName} ${user.lastName}</p>
					<p class="text-muted mb-0">${user.email}</p>
				</td>
				<td><p class="fw-bold mb-1">${user.login}</p></td>
				<td>
					<p class="fw-normal mb-1 fw-bold">${user.city}</p>
					<p class="text-muted mb-0">${user.address}</p>
				</td>
				<td><c:choose>
						<c:when test="${user.blocked}">
							<span class=" badge text-bg-danger"><fmt:message key="admin_menu.blocked"/></span>
						</c:when>
						<c:otherwise>
							<span class=" badge text-bg-success"><fmt:message key="admin_menu.unblocked"/></span>
						</c:otherwise>
					</c:choose></td>
				<td>$${user.balance}</td>

				<td>

					<form action="controller"
						method="get" id="view_profile_form">
						<input type="hidden" name="action" value="viewSubscriberProfile"/>
						<input type="hidden" name="userId" id="view_user_profile_id">
					</form>
					<div class="btn-group">
						<button type="button" class="btn btn-warning"
							onclick="
							document.getElementById('view_user_profile_id').value='${user.id}';
							document.getElementById('view_profile_form').submit()"><fmt:message key="admin_menu.profile"/></button>

						<button type="button"
							class="btn btn-outline-warning dropdown-toggle dropdown-toggle-split"
							data-bs-toggle="dropdown" aria-expanded="false">
							<span class="visually-hidden"><fmt:message key="admin_menu.profile"/></span>
						</button>
						<ul class="dropdown-menu dropdown-menu-dark">
							<li><button type="button" class="dropdown-item"
									data-bs-toggle="modal" data-bs-target="#submitBlockModal"
									onClick="submit_modal(`${user.firstName}`,`${user.lastName}`,`${user.login}`,`${user.id}`)"><fmt:message key="admin_menu.remove"/></button></li>
							<li><form action="controller?action=changeUserStatus"
									method="post">
									<input type="hidden" name="page" value="${page}" /> <input
										type="hidden" name="searchField" value="${searchField}" /> <input
										type="hidden" name="rowNumber" value="${rowNumber}" /> <input
										type="hidden" name="userId" value="${user.id}" /> <input
										type="hidden" name="userBlocked" value="${user.blocked}" />
									<c:choose>
										<c:when test="${user.blocked}">
											<button type="submit" class="dropdown-item"><fmt:message key="admin_menu.unblock"/></button>
										</c:when>
										<c:otherwise>
											<button type="submit" class="dropdown-item"><fmt:message key="admin_menu.block"/></button>
										</c:otherwise>
									</c:choose>
								</form></li>
							<li><button type="button" class="dropdown-item"
									data-bs-toggle="modal" data-bs-target="#changeUserBalanceModal"
									onClick="change_balance_modal(`${user.login}`,`${user.balance}`,`${user.id}`)"><fmt:message key="admin_menu.change_balance"/></button></li>
						</ul>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="container pt-3">
	<div class="d-flex justify-content-between align-items-center">

		<form action="controller" method="get">
		<input type="hidden" name="action" value="adminMenu"/>
			<input type="hidden" name="searchField" value="${searchField}" />
			<input type="hidden" name="rowNumber" value="${rowNumber}" />
			<input type="hidden" name="page" id="pageNumber" />
			<div class="form-group btn-group col-lg-auto me-lg-auto mx-2 justify-content-center">
				<c:forEach begin="1" end="${numberOfPages}" var="i">
					<c:choose>
						<c:when test="${page eq i}">
							<button type="button" class="btn btn-warning">${i}</button>
						</c:when>
						<c:otherwise>
							<button value="${i}" type="submit"
								class="btn btn-outline-warning" onclick=submit_page(this.value)>${i}</button>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</form>


		<div class="text-end">
			<form action="controller" method="get">
			<input type="hidden" name="action" value="openUserRegistration"/>
				<button type="submit" class="btn btn-outline-warning"><fmt:message key="admin_menu.register_user"/></button>
			</form>
		</div>
	</div>
</div>


<!-- Submit Block Modal -->
<div class="modal fade" id="submitBlockModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content text-bg-dark">
			<div class="modal-body">
				<h5 class="modal-title fw-bold m-4"><fmt:message key="admin_menu.remove_user_confirm_msg"/></h5>
				<ul
					class="list-group list-group-horizontal flex-fill mt-3 mb-1 text-white">
					<li class="list-group-item"><fmt:message key="admin_menu.name"/>:</li>
					<li class="list-group-item flex-fill"
						id="submit_modal_user_full_name"></li>
				</ul>
				<ul class="list-group list-group-horizontal-sm  mt-3 mb-1">
					<li class="list-group-item"><fmt:message key="admin_menu.login"/>:</li>
					<li class="list-group-item flex-fill" id="submit_modal_user_login"></li>
				</ul>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal"><fmt:message key="admin_menu.cancel"/></button>
				<form action="controller?action=removeUser" method="post">
					<input type="hidden" name="page" value="${page}" />
					 <input
						type="hidden" name="searchField" value="${searchField}" /> <input
						type="hidden" name="rowNumber" value="${rowNumber}" /> <input
						type="hidden" name="userId" id="remove_user_id" />
					<button type="submit" class="btn btn-warning"><fmt:message key="admin_menu.submit"/></button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Change Balance Modal -->
<div class="modal fade" id="changeUserBalanceModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content text-bg-dark">
			<div class="modal-body">
				<h5 class="modal-title fw-bold m-4"><fmt:message key="admin_menu.change_balance_prompt"/></h5>
				<ul class="list-group list-group-horizontal-sm  mt-3 mb-1">
					<li class="list-group-item"><fmt:message key="admin_menu.login"/>:</li>
					<li class="list-group-item flex-fill"
						id="change_balance_modal_user_login"></li>
				</ul>
				<form action="controller?action=changeUserBalance" method="post">
					<input type="hidden" name="page" value="${page}" /> <input
						type="hidden" name="searchField" value="${searchField}" /> <input
						type="hidden" name="rowNumber" value="${rowNumber}" /> <input
						type="hidden" name="userId" id="change_balance_user_id" />
					<div class="input-group my-3">
						<span class="input-group-text">$</span> <span
							class="input-group-text" id="current_user_balance"></span> <input
							type="number" name="amount" class="form-control" step="0.01"
							value="0" min="0" required />
					</div>
					<div class="input-group">
						<span class="input-group-text"><fmt:message key="admin_menu.transaction_description"/></span>
						<textarea class="form-control" name="description"
							placeholder="<fmt:message key="tariff_add.max_character_1"/> 100 <fmt:message key="tariff_add.max_character_2"/>" maxlength="100" required></textarea>
					</div>
					<hr class="style1">
					<br>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal"><fmt:message key="account.cancel"/></button>
					<button type=submit class="btn btn-warning"
						name="balanceChangeType" value="withdraw"><fmt:message key="admin_menu.withdraw"/></button>
					<button type="submit" class="btn btn-warning"
						name="balanceChangeType" value="topUp"><fmt:message key="admin_menu.top_up"/></button>
				</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/admin_menu.js"></script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
