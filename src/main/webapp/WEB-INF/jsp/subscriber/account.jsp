<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ include file="/WEB-INF/jsp/subscriber/profile_header.jspf"%>
<div class="text-white  text-start">
	<div class="d-flex justify-content-between align-items-center">
		<p class="h2 m-3 fw-bold">Your balance:
			$${requestScope.userBalance}</p>
		<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
			<button class="btn btn-warning" data-bs-toggle="modal"
				data-bs-target="#replenishModal">Replenish</button>
		</c:if>
	</div>
	<hr class="style1">

	<p class="text-muted fs-4 text-center">Payment History</p>
	<table class="table table-dark table-striped">
		<thead>
			<tr>
				<th scope="col">Date</th>
				<th scope="col">Description</th>
				<th scope="col">Amount</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="transaction"
				items="${requestScope.transactionsToDisplay}">
				<tr>
					<td><lct:dateFormatTag
							locale="${empty cookie['lang'].value?'en':cookie['lang'].value}"
							date="${transaction.timestamp}"></lct:dateFormatTag></td>
					<td>${transaction.description}</td>
					<td><c:choose>
							<c:when test="${transaction.amount < 0}">
								<span class="text-danger">${transaction.amount} $</span>
							</c:when>
							<c:when test="${transaction.amount > 0}">
								<span class="text-success">+${transaction.amount} $</span>
							</c:when>
							<c:otherwise>
								<span>${transaction.amount} $</span>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<c:if test="${not empty noTransactionFound}">
		<div class="alert alert-warning my-2 mx-3 text-center" role="alert">
			${noTransactionFound}</div>
	</c:if>
	<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
		<form action="controller" method="get">
	</c:if>
	<input type="hidden" name="action" value="viewAccount" />
	<c:if test="${sessionScope.loggedUser.role eq 'ADMIN'}">
		<form action="controller" method="get">

			<input type="hidden" name="action" value="viewSubscriberAccount" /> <input
				name="userId" value="${requestScope.userId}" type="hidden" />
	</c:if>
	<input type="hidden" name="page" id="pageNumber" />
	<div
		class="form-group btn-group col-lg-auto me-lg-auto mx-2 justify-content-center">
		<c:forEach begin="1" end="${numberOfPages}" var="i">

			<c:choose>
				<c:when test="${page eq i}">
					<button type="button" class="btn btn-warning">${i}</button>
				</c:when>
				<c:otherwise>
					<button value="${i}" type="submit" class="btn btn-outline-warning"
						onclick=submit_page(this.value)>${i}</button>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	</form>
</div>
<c:if test="${sessionScope.loggedUser.role eq 'SUBSCRIBER'}">
	<!-- 			Replenish modal -->
	<div class="modal fade" id="replenishModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<form action="controller?action=replenish" method="post">
						<div class="d-flex justify-content-between align-items-center">
							<div class="text-muted fs-5">Amount</div>
							<div class="mx-5"></div>
							<div class="input-group">
								<span class="input-group-text">$</span> <input type="number"
									name="amount" class="form-control" step="0.01" value="0"
									min="0" required />
							</div>
						</div>

						<hr class="style1">
						<div class="form-floating text-dark my-4">
							<input type="text" class="form-control" id="floatingCardNumber"
								placeholder="1234 5678 9012 3456" name="cardNumber" required>
							<label for="floatingCardNumber">Card number</label>
						</div>

						<div
							class="d-flex justify-content-between align-items-center mb-2">
							<div class="form-floating text-dark">
								<input type="text" class="form-control" id="floatingPassword"
									placeholder="12/13" name="expireDate" maxlength="5" required>
								<label for="floatingPassword">Expire Date</label>
							</div>
							<div class="form-floating text-dark">
								<input type="password" class="form-control"
									id="floatingPassword" placeholder="Password" name="password"
									minlength="3" maxlength="3" required> <label
									for="floatingPassword">CVC</label>
							</div>
						</div>

						<hr class="style1">
						<div class="d-flex justify-content-end">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<div class="mx-2"></div>
							<button class="btn btn-warning">Replenish</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>